package com.umc.gusto.domain.group.controller;

import com.umc.gusto.domain.group.model.request.GroupRequestDto;
import com.umc.gusto.domain.group.model.response.GroupResponseDto;
import com.umc.gusto.domain.group.service.GroupService;
import com.umc.gusto.domain.user.entity.User;
import com.umc.gusto.global.auth.model.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/groups")
public class GroupController {
    private final GroupService groupService;

    @ResponseBody
    @PostMapping
    public ResponseEntity<GroupResponseDto.PostGroupResponseDto> createGroup(@AuthenticationPrincipal AuthUser authUser, @RequestBody GroupRequestDto.createGroupDTO createGroupDTO){
        User owner = authUser.getUser();
        GroupResponseDto.PostGroupResponseDto newGroup = groupService.createGroup(owner, createGroupDTO);
        return ResponseEntity.ok().body(newGroup);
    }
}
