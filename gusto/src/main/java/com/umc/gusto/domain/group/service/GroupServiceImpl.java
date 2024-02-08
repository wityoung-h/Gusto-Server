package com.umc.gusto.domain.group.service;

import com.umc.gusto.domain.group.entity.Group;
import com.umc.gusto.domain.group.entity.GroupList;
import com.umc.gusto.domain.group.entity.GroupMember;
import com.umc.gusto.domain.group.model.request.GroupListRequest;
import com.umc.gusto.domain.group.model.request.PostGroupRequest;
import com.umc.gusto.domain.group.model.request.UpdateGroupRequest;
import com.umc.gusto.domain.group.model.response.GetGroupMemberResponse;
import com.umc.gusto.domain.group.model.response.GetGroupResponse;
import com.umc.gusto.domain.group.model.response.GroupListResponse;
import com.umc.gusto.domain.group.model.response.UpdateGroupResponse;
import com.umc.gusto.domain.group.repository.GroupListRepository;
import com.umc.gusto.domain.group.repository.GroupMemberRepository;
import com.umc.gusto.domain.group.repository.GroupRepository;
import com.umc.gusto.domain.review.repository.ReviewRepository;
import com.umc.gusto.domain.store.repository.StoreRepository;
import com.umc.gusto.domain.user.entity.User;
import com.umc.gusto.global.common.BaseEntity;
import com.umc.gusto.global.exception.Code;
import com.umc.gusto.global.exception.GeneralException;
import com.umc.gusto.global.exception.customException.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class GroupServiceImpl implements GroupService{
    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;


    private final GroupListRepository groupListRepository;
    private final StoreRepository storeRepository;
    private final ReviewRepository reviewRepository;


    public void createGroup(User owner, PostGroupRequest postGroupRequest){
        Group group = Group.builder()
                .groupName(postGroupRequest.getGroupName())
                .groupScript(postGroupRequest.getGroupScript())
                .owner(owner)
                .notice("멤버들에게 새로운 공지사항을 공유해보세요!")
                .build();
        Group savedGroup = groupRepository.save(group);
        GroupMember ownerMember = GroupMember.builder()
                .group(savedGroup)
                .user(owner)
                .build();
        groupMemberRepository.save(ownerMember);
    }

    @Transactional(readOnly = true)
    public GetGroupResponse getGroup(Long groupId){
        Group group = groupRepository.findGroupByGroupIdAndStatus(groupId, BaseEntity.Status.ACTIVE)
                .orElseThrow(()->new GeneralException(Code.FIND_FAIL_GROUP));
        Long ownerMemberId = groupMemberRepository.findGroupMemberIdByGroupAndUser(group, group.getOwner());
        List<GroupMember> groupMembers = groupMemberRepository.findGroupMembersByGroup(group);
        List<GetGroupMemberResponse> groupMembersDto = groupMembers.stream()
                .map(member -> new GetGroupMemberResponse(
                        member.getGroupMemberId(),
                        member.getUser().getNickname(),
                        member.getUser().getProfileImage()
                ))
                .collect(Collectors.toList());
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
        groupRepository.save(group);
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
            String reviewImg = reviewRepository.findTopReviewImageByStoreId(gl.getStore().getStoreId()).orElse(null);
            return GroupListResponse.builder()
                    .groupListId(gl.getGroupListId())
                    .storeName(gl.getStore().getStoreName())
                    .profileImg(reviewImg)
                    .address(gl.getStore().getAddress())
                    .build();
        }).collect(Collectors.toList());


    }
}
