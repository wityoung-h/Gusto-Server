package com.umc.gusto.domain.group.service;

import com.umc.gusto.domain.group.entity.Group;
import com.umc.gusto.domain.group.entity.GroupMember;
import com.umc.gusto.domain.group.model.request.PostGroupRequest;
import com.umc.gusto.domain.group.model.request.UpdateGroupRequest;
import com.umc.gusto.domain.group.model.response.GetGroupMemberResponse;
import com.umc.gusto.domain.group.model.response.GetGroupResponse;
import com.umc.gusto.domain.group.model.response.GetGroupsResponse;
import com.umc.gusto.domain.group.model.response.UpdateGroupResponse;
import com.umc.gusto.domain.group.repository.GroupListRepository;
import com.umc.gusto.domain.group.repository.GroupMemberRepository;
import com.umc.gusto.domain.group.repository.GroupRepository;
import com.umc.gusto.domain.route.repository.RouteRepository;
import com.umc.gusto.domain.user.entity.User;
import com.umc.gusto.global.common.BaseEntity;
import com.umc.gusto.global.exception.Code;
import com.umc.gusto.global.exception.GeneralException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class GroupServiceImpl implements GroupService{
    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final GroupListRepository groupListRepository;
    private final RouteRepository routeRepository;

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
        groupRepository.save(group);
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
}
