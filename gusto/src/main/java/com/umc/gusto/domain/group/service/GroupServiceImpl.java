package com.umc.gusto.domain.group.service;

import com.umc.gusto.domain.group.entity.Group;
import com.umc.gusto.domain.group.entity.GroupMember;
import com.umc.gusto.domain.group.model.request.GroupRequestDto;
import com.umc.gusto.domain.group.model.response.GroupMemberResponseDto;
import com.umc.gusto.domain.group.model.response.GroupResponseDto;
import com.umc.gusto.domain.group.repository.GroupListRepository;
import com.umc.gusto.domain.group.repository.GroupMemberRepository;
import com.umc.gusto.domain.group.repository.GroupRepository;
import com.umc.gusto.domain.user.entity.User;
import com.umc.gusto.global.common.BaseEntity;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService{
    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;

    @Transactional
    public GroupResponseDto.PostGroupResponseDto createGroup(User owner, GroupRequestDto.CreateGroupDTO createGroupDTO){
        Group group = Group.builder()
                .groupName(createGroupDTO.getGroupName())
                .groupScript(createGroupDTO.getGroupScript())
                .owner(owner)
                .notice("멤버들에게 새로운 공지사항을 공유해보세요!")
                .build();
        Group savedGroup = groupRepository.save(group);
        GroupMember ownerMember = GroupMember.builder()
                .group(savedGroup)
                .user(owner)
                .build();
        groupMemberRepository.save(ownerMember);
        return new GroupResponseDto.PostGroupResponseDto(savedGroup.getGroupId(), savedGroup.getGroupName(), savedGroup.getGroupScript());
    }

    @Transactional(readOnly = true)
    public GroupResponseDto.GetGroupResponseDto getGroup(Long groupId){
        Group group = groupRepository.findGroupByGroupId(groupId)
                .orElseThrow(()->new RuntimeException("Group not found"));
        Long ownerMemberId = groupMemberRepository.findGroupMemberIdByGroupAndUser(group, group.getOwner());
        List<GroupMember> groupMembers = groupMemberRepository.findGroupMembersByGroup(group);
        List<GroupMemberResponseDto.GetGroupMemberResponseDto> groupMembersDto = groupMembers.stream()
                .map(member -> new GroupMemberResponseDto.GetGroupMemberResponseDto(
                        member.getGroupMemberId(),
                        member.getUser().getNickname(),
                        member.getUser().getProfileImage()
                ))
                .collect(Collectors.toList());
        return GroupResponseDto.GetGroupResponseDto.builder()
                .groupId(group.getGroupId())
                .groupName(group.getGroupName())
                .groupScript(group.getGroupScript())
                .owner(ownerMemberId)
                .notice(group.getNotice())
                .groupMembers(groupMembersDto)
                .build();
    }

    @Transactional
    public GroupResponseDto.UpdateGroupResponseDto updateGroup(User owner, Long groupId, GroupRequestDto.UpdateGroupDTO updateGroupDTO){
        Group group = groupRepository.findGroupByGroupId(groupId)
                .orElseThrow(()->new RuntimeException("Group not found"));
        Long ownerMemberId = groupMemberRepository.findGroupMemberIdByGroupAndUser(group, group.getOwner());

        // 그룹 이름 수정 (owner만 수정 가능)
        if(owner.equals(group.getOwner()) && updateGroupDTO.getGroupName() != null){
            group.updateGroupName(updateGroupDTO.getGroupName());
        }

        //그룹 공지 수정
        if(updateGroupDTO.getNotice() != null){
            group.updateNotice(updateGroupDTO.getNotice());
        }

        groupRepository.save(group);
        return GroupResponseDto.UpdateGroupResponseDto.builder()
                .groupId(group.getGroupId())
                .groupName(group.getGroupName())
                .groupScript(group.getGroupScript())
                .owner(ownerMemberId)
                .notice(group.getNotice())
                .build();
    }

    @Transactional
    public void deleteGroup(User owner, Long groupId){
        Group group = groupRepository.findGroupByGroupId(groupId)
                .orElseThrow(()->new RuntimeException("Group not found"));
        if(group.getOwner().getUserid().equals(owner.getUserid())){
            // 그룹의 가게, 루트 모두 삭제

            // 그룹 삭제
            group.updateStatus(BaseEntity.Status.INACTIVE);
            groupRepository.save(group);
        }
    }
}
