package com.umc.gusto.domain.group.controller;

import com.umc.gusto.domain.group.model.request.GroupRequestDto;
import com.umc.gusto.domain.group.model.response.GroupResponseDto;
import com.umc.gusto.domain.group.service.GroupService;
import com.umc.gusto.domain.user.entity.User;
import com.umc.gusto.global.auth.model.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/groups")
public class GroupController {
    private final GroupService groupService;

    /**
     * 그룹 생성
     * [POST] /groups
     */
    @PostMapping
    public ResponseEntity<GroupResponseDto.PostGroupResponseDto> createGroup(@AuthenticationPrincipal AuthUser authUser, @RequestBody GroupRequestDto.CreateGroupDTO createGroupDTO){
        User owner = authUser.getUser();
        groupService.createGroup(owner, createGroupDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 그룹 1건 조회
     * [GET] /groups/{groupId}
     */
    @GetMapping("/{groupId}")
    public ResponseEntity<GroupResponseDto.GetGroupResponseDto> getGroup(@PathVariable Long groupId){
        GroupResponseDto.GetGroupResponseDto getGroup = groupService.getGroup(groupId);
        return ResponseEntity.ok().body(getGroup);
    }

    /**
     * 그룹 수정
     * [PATCH] /groups/{groupId}
     */
    @PatchMapping("/{groupId}")
    public ResponseEntity<GroupResponseDto.UpdateGroupResponseDto> updateGroup(@AuthenticationPrincipal AuthUser authUser, @PathVariable Long groupId, @RequestBody GroupRequestDto.UpdateGroupDTO updateGroupDTO){
        User owner = authUser.getUser();
        GroupResponseDto.UpdateGroupResponseDto updatedGroup = groupService.updateGroup(owner, groupId, updateGroupDTO);
        return ResponseEntity.ok().body(updatedGroup);
    }

    /**
     * 그룹 삭제
     * [PATCH] /groups/{groupId}/delete
     */
    @PatchMapping("/{groupId}/delete")
    public ResponseEntity<String> deleteGroup(@AuthenticationPrincipal AuthUser authUser, @PathVariable Long groupId){
        User owner = authUser.getUser();
        groupService.deleteGroup(owner, groupId);
        return ResponseEntity.ok("DELETE SUCCESS");
    }

}
