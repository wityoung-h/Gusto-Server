package com.umc.gusto.domain.group.service;

import com.umc.gusto.domain.group.model.request.GroupRequestDto;
import com.umc.gusto.domain.group.model.response.GroupResponseDto;
import com.umc.gusto.domain.user.entity.User;

public interface GroupService {
    GroupResponseDto.PostGroupResponseDto createGroup(User owner, GroupRequestDto.CreateGroupDTO createGroupDTO);
    GroupResponseDto.GetGroupResponseDto getGroup(Long groupId);
    GroupResponseDto.UpdateGroupResponseDto updateGroup(User owner, Long groupId, GroupRequestDto.UpdateGroupDTO updateGroupDTO);
}
