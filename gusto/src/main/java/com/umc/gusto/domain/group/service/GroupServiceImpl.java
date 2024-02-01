package com.umc.gusto.domain.group.service;

import com.umc.gusto.domain.group.entity.Group;
import com.umc.gusto.domain.group.model.request.GroupRequestDto;
import com.umc.gusto.domain.group.model.response.GroupResponseDto;
import com.umc.gusto.domain.group.repository.GroupRepository;
import com.umc.gusto.domain.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService{
    private final GroupRepository groupRepository;

    @Transactional
    public GroupResponseDto.PostGroupResponseDto createGroup(User owner, GroupRequestDto.createGroupDTO createGroupDTO){
        Group group = Group.builder()
                .groupName(createGroupDTO.getGroupName())
                .groupScript(createGroupDTO.getGroupScript())
                .owner(owner)
                .notice("멤버들에게 새로운 공지사항을 공유해보세요!")
                .build();
        Group savedGroup = groupRepository.save(group);
        return new GroupResponseDto.PostGroupResponseDto(savedGroup.getGroupId(), savedGroup.getGroupName(), savedGroup.getGroupScript());
    }
}
