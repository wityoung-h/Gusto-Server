package com.umc.gusto.domain.group.service;

import com.umc.gusto.domain.group.entity.Group;
import com.umc.gusto.domain.group.entity.GroupList;
import com.umc.gusto.domain.group.entity.GroupMember;
import com.umc.gusto.domain.group.model.request.GroupListRequest;
import com.umc.gusto.domain.group.entity.InvitationCode;
import com.umc.gusto.domain.group.model.request.JoinGroupRequest;
import com.umc.gusto.domain.group.model.request.PostGroupRequest;
import com.umc.gusto.domain.group.model.request.TransferOwnershipRequest;
import com.umc.gusto.domain.group.model.request.UpdateGroupRequest;
import com.umc.gusto.domain.group.model.response.GetGroupMemberResponse;
import com.umc.gusto.domain.group.model.response.GetGroupResponse;
import com.umc.gusto.domain.group.model.response.GroupListResponse;
import com.umc.gusto.domain.group.model.response.GetInvitationCodeResponse;
import com.umc.gusto.domain.group.model.response.TransferOwnershipResponse;
import com.umc.gusto.domain.group.model.response.GetGroupsResponse;
import com.umc.gusto.domain.group.model.response.UpdateGroupResponse;
import com.umc.gusto.domain.group.repository.GroupListRepository;
import com.umc.gusto.domain.group.repository.GroupMemberRepository;
import com.umc.gusto.domain.group.repository.GroupRepository;
import com.umc.gusto.domain.review.repository.ReviewRepository;
import com.umc.gusto.domain.store.repository.StoreRepository;
import com.umc.gusto.domain.group.repository.InvitationCodeRepository;
import com.umc.gusto.domain.route.repository.RouteRepository;
import com.umc.gusto.domain.user.entity.User;
import com.umc.gusto.domain.user.repository.UserRepository;
import com.umc.gusto.global.common.BaseEntity;
import com.umc.gusto.global.exception.Code;
import com.umc.gusto.global.exception.GeneralException;
import com.umc.gusto.global.exception.customException.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.RandomStringUtils;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class GroupServiceImpl implements GroupService{
    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final UserRepository userRepository;
    private final InvitationCodeRepository invitationCodeRepository;
    private final GroupListRepository groupListRepository;
    private final RouteRepository routeRepository;
  
    private static final int INVITE_CODE_LENGTH = 12;

    private final StoreRepository storeRepository;
    private final ReviewRepository reviewRepository;


    public void createGroup(User owner, PostGroupRequest postGroupRequest){
        // 그룹
        Group group = Group.builder()
                .groupName(postGroupRequest.getGroupName())
                .groupScript(postGroupRequest.getGroupScript())
                .owner(owner)
                .notice("멤버들에게 새로운 공지사항을 공유해보세요!")
                .build();
        Group savedGroup = groupRepository.save(group);

        // 그룹 멤버
        GroupMember ownerMember = GroupMember.builder()
                .group(savedGroup)
                .user(owner)
                .build();
        groupMemberRepository.save(ownerMember);

        // 초대 코드
        String code = RandomStringUtils.randomAlphanumeric(INVITE_CODE_LENGTH);
        InvitationCode invitationCode = InvitationCode.builder()
                .group(group)
                .code(code)
                .build();
        invitationCodeRepository.save(invitationCode);
    }

    @Transactional(readOnly = true)
    public GetGroupResponse getGroup(Long groupId){
        Group group = groupRepository.findGroupByGroupIdAndStatus(groupId, BaseEntity.Status.ACTIVE)
                .orElseThrow(()->new GeneralException(Code.FIND_FAIL_GROUP));
        Long ownerMemberId = groupMemberRepository.findGroupMemberIdByGroupAndUser(group, group.getOwner());
        List<GetGroupMemberResponse> groupMembersDto = getGroupMembers(groupId);
        return GetGroupResponse.builder()
                .groupId(group.getGroupId())
                .groupName(group.getGroupName())
                .groupScript(group.getGroupScript())
                .owner(ownerMemberId)
                .notice(group.getNotice())
                .groupMembers(groupMembersDto)
                .build();
    }

    public UpdateGroupResponse updateGroup(User owner, Long groupId, UpdateGroupRequest updateGroupRequest){
        Group group = groupRepository.findGroupByGroupIdAndStatus(groupId, BaseEntity.Status.ACTIVE)
                .orElseThrow(()->new GeneralException(Code.FIND_FAIL_GROUP));
        Long ownerMemberId = groupMemberRepository.findGroupMemberIdByGroupAndUser(group, group.getOwner());

        // 그룹 이름 수정 (owner만 수정 가능)
        if(updateGroupRequest.getGroupName() != null){
            if(group.getOwner().getUserId().equals(owner.getUserId())){
                group.updateGroupName(updateGroupRequest.getGroupName());
            } else{
                throw new GeneralException(Code.UNAUTHORIZED_MODIFY_GROUP_NAME);
            }
        }

        //그룹 공지 수정
        if(updateGroupRequest.getNotice() != null){
            group.updateNotice(updateGroupRequest.getNotice());
        }

        groupRepository.save(group);
        return UpdateGroupResponse.builder()
                .groupId(group.getGroupId())
                .groupName(group.getGroupName())
                .groupScript(group.getGroupScript())
                .owner(ownerMemberId)
                .notice(group.getNotice())
                .build();
    }

    public void deleteGroup(User owner, Long groupId){
        Group group = groupRepository.findGroupByGroupIdAndStatus(groupId, BaseEntity.Status.ACTIVE)
                .orElseThrow(()->new GeneralException(Code.FIND_FAIL_GROUP));

        // 그룹 소유자가 아닌 경우 권한 예외 처리
        if(!group.getOwner().getUserId().equals(owner.getUserId())){
            throw new GeneralException(Code.UNAUTHORIZED_DELETE_GROUP);
        }

        // 그룹 삭제
        group.updateStatus(BaseEntity.Status.INACTIVE);
    }

    public void joinGroup(User user, Long groupId, JoinGroupRequest joinGroupRequest){
        Group group = groupRepository.findGroupByGroupIdAndStatus(groupId, BaseEntity.Status.ACTIVE)
                .orElseThrow(()->new GeneralException(Code.FIND_FAIL_GROUP));
        String invitationCode = invitationCodeRepository.findCodeByGroup(group);

        // 초대 코드 확인
        if(joinGroupRequest.getCode().equals(invitationCode)){
            // 그룹 참여
            User joinUser = userRepository.findById(user.getUserId())
                    .orElseThrow(()->new GeneralException(Code.DONT_EXIST_USER));

            GroupMember groupMember = GroupMember.builder()
                    .group(group)
                    .user(joinUser)
                    .build();

            groupMemberRepository.save(groupMember);
        }else{
            throw new GeneralException(Code.INVALID_INVITATION_CODE);
        }
    }

    public void leaveGroup(User user, Long groupId){
        Group group = groupRepository.findGroupByGroupIdAndStatus(groupId, BaseEntity.Status.ACTIVE)
                .orElseThrow(()->new GeneralException(Code.FIND_FAIL_GROUP));
        GroupMember groupMember = groupMemberRepository.findGroupMemberByGroupAndUser(group, user)
                        .orElseThrow(()->new GeneralException(Code.USER_NOT_IN_GROUP));

        groupMemberRepository.delete(groupMember);
    }
  
    @Transactional(readOnly = true)
    public GetInvitationCodeResponse getInvitationCode(Long groupId) {
        Group group = groupRepository.findGroupByGroupIdAndStatus(groupId, BaseEntity.Status.ACTIVE)
                .orElseThrow(() -> new GeneralException(Code.FIND_FAIL_GROUP));
        InvitationCode invitationCode = invitationCodeRepository.findInvitationCodeByGroup(group)
                .orElseThrow(() -> new GeneralException(Code.INVITATION_CODE_NOT_FOUND));

        return GetInvitationCodeResponse.builder()
                .invitationCodeId(invitationCode.getInvitationCodeId())
                .groupId(invitationCode.getGroup().getGroupId())
                .code(invitationCode.getCode())
                .build();
    }

    @Transactional(readOnly = true)
    public List<GetGroupsResponse> getUserGroups(User user) {
        List<Long> groupIds = groupMemberRepository.findGroupIdsByUser(user);
        List<Group> groups = groupRepository.findGroupsByGroupIdInAndStatus(groupIds, BaseEntity.Status.ACTIVE);
        return groups.stream()
                .map(group -> {
                    int numMembers = groupMemberRepository.countGroupMembersByGroup(group);
                    int numRestaurants = groupListRepository.countGroupListsByGroup(group);
                    int numRoutes = routeRepository.countRoutesByGroupAndStatus(group, BaseEntity.Status.ACTIVE);

                    return GetGroupsResponse.builder()
                            .groupId(group.getGroupId())
                            .groupName(group.getGroupName())
                            .isOwner(user.getUserId().equals(group.getOwner().getUserId()))
                            .numMembers(numMembers)
                            .numRestaurants(numRestaurants)
                            .numRoutes(numRoutes)
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GetGroupMemberResponse> getGroupMembers(Long groupId){
        Group group = groupRepository.findGroupByGroupIdAndStatus(groupId, BaseEntity.Status.ACTIVE)
                .orElseThrow(()->new GeneralException(Code.FIND_FAIL_GROUP));
        List<GroupMember> groupMembers = groupMemberRepository.findGroupMembersByGroup(group);
        return groupMembers.stream()
                .map(groupMember -> GetGroupMemberResponse.builder()
                        .groupMemberId(groupMember.getGroupMemberId())
                        .nickname(groupMember.getUser().getNickname())
                        .profileImg(groupMember.getUser().getProfileImage())
                        .build())
                .collect(Collectors.toList());
    }

    public TransferOwnershipResponse transferOwnership(User owner, Long groupId, TransferOwnershipRequest transferOwnershipRequest){
        Group group = groupRepository.findGroupByGroupIdAndStatus(groupId, BaseEntity.Status.ACTIVE)
                .orElseThrow(()->new GeneralException(Code.FIND_FAIL_GROUP));

        // 그룹 소유자 권한 확인
        if(!group.getOwner().getUserId().equals(owner.getUserId())){
            throw new GeneralException(Code.NO_TRANSFER_PERMISSION);
        }

        // 새로운 그룹장 찾기
        GroupMember newOwnerMember = groupMemberRepository.findGroupMemberByGroupAndGroupMemberId(group, transferOwnershipRequest.getNewOwner())
                .orElseThrow(()->new GeneralException(Code.USER_NOT_IN_GROUP));

        User newOwner = newOwnerMember.getUser();

        // 새로운 그룹장으로 권한 이전
        group.updateOwner(newOwner);
        groupRepository.save(group);

        return TransferOwnershipResponse.builder()
                .newOwner(newOwnerMember.getGroupMemberId())
                .build();
    }

    @Override
    public void createGroupList(Long groupId,GroupListRequest request,User user) {
        // 그룹 존재 여부 확인
        Group group = groupRepository.findGroupByGroupIdAndStatus(groupId, BaseEntity.Status.ACTIVE)
                .orElseThrow(() -> new GeneralException(Code.FIND_FAIL_GROUP));
        // 그룹 구성원인지 확인
        if(!groupMemberRepository.existsGroupMemberByGroupAndUser(group,user))
            throw new GeneralException(Code.USER_NO_PERMISSION_FOR_GROUP);

        GroupList groupList =GroupList.builder()
                .group(group)
                .store(storeRepository.findById(request.getStoreId()).orElseThrow(() -> new NotFoundException(Code.STORE_NOT_FOUND)))
                .user(user)
                .build();

        // 그룹리스트 저장
        groupListRepository.save(groupList);

    }

    @Override
    public void deleteGroupList(List<Long> groupListId,User user) {
        for (Long gl : groupListId) {
            GroupList groupList = groupListRepository.findGroupListByGroupListId(gl).orElseThrow(() -> new GeneralException(Code.GROUPLIST_NOT_FROUND));
            // 그룹 구성원인지 확인
            if(!groupMemberRepository.existsGroupMemberByGroupAndUser(groupList.getGroup(),user))
                throw new GeneralException(Code.USER_NO_PERMISSION_FOR_GROUP);
            // 그룹리스트 삭제
            groupListRepository.delete(groupList);
        }

    }

    @Override
    public List<GroupListResponse> getAllGroupList(Long groupId) {
        // 그룹 존재여부 확인
        Group group = groupRepository.findGroupByGroupIdAndStatus(groupId, BaseEntity.Status.ACTIVE)
                .orElseThrow(() -> new GeneralException(Code.FIND_FAIL_GROUP));
        // 그룹 내 모든 찜한 상점 조회 = 그룹리스트 조회
        List<GroupList> groupLists = groupListRepository.findGroupListByGroup(group);

        // 그룹 리스트에 해당하는 각 상점 정보 조회
        return groupLists.stream().map(gl -> {
            String reviewImg = reviewRepository.findTopReviewImageByStoreId(gl.getStore().getStoreId()).get(0);
            return GroupListResponse.builder()
                    .groupListId(gl.getGroupListId())
                    .storeName(gl.getStore().getStoreName())
                    .profileImg(reviewImg)
                    .address(gl.getStore().getAddress())
                    .build();
        }).collect(Collectors.toList());


    }
}
