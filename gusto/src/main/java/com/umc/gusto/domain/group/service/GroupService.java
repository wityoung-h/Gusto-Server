package com.umc.gusto.domain.group.service;

import com.umc.gusto.domain.group.model.request.GroupRequestDto;
import com.umc.gusto.domain.group.model.response.GroupResponseDto;
import com.umc.gusto.domain.user.entity.User;

import java.util.List;

public interface GroupService {
    // 그룹 생성
    GroupResponseDto.PostGroupResponseDto createGroup(User owner, GroupRequestDto.CreateGroupDTO createGroupDTO);

    // 그룹 1건 조회
    GroupResponseDto.GetGroupResponseDto getGroup(Long groupId);

    // 그룹 수정
    GroupResponseDto.UpdateGroupResponseDto updateGroup(User owner, Long groupId, GroupRequestDto.UpdateGroupDTO updateGroupDTO);

    // 그룹 삭제
    void deleteGroup(User owner, Long groupId);
}
