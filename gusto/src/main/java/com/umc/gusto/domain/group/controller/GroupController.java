package com.umc.gusto.domain.group.controller;

import com.umc.gusto.domain.group.model.request.GroupListRequest;
import com.umc.gusto.domain.group.model.request.PostGroupRequest;
import com.umc.gusto.domain.group.model.request.UpdateGroupRequest;
import com.umc.gusto.domain.group.model.response.GetGroupResponse;
import com.umc.gusto.domain.group.model.response.GroupListResponse;
import com.umc.gusto.domain.group.model.response.UpdateGroupResponse;
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
    public ResponseEntity<?> createGroup(@AuthenticationPrincipal AuthUser authUser, @RequestBody PostGroupRequest postGroupRequest){
        User owner = authUser.getUser();
        groupService.createGroup(owner, postGroupRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 그룹 1건 조회
     * [GET] /groups/{groupId}
     */
    @GetMapping("/{groupId}")
    public ResponseEntity<GetGroupResponse> getGroup(@PathVariable Long groupId){
        GetGroupResponse getGroup = groupService.getGroup(groupId);
        return ResponseEntity.status(HttpStatus.OK).body(getGroup);
    }

    /**
     * 그룹 수정
     * [PATCH] /groups/{groupId}
     */
    @PatchMapping("/{groupId}")
    public ResponseEntity<UpdateGroupResponse> updateGroup(@AuthenticationPrincipal AuthUser authUser, @PathVariable Long groupId, @RequestBody UpdateGroupRequest updateGroupRequest){
        User owner = authUser.getUser();
        UpdateGroupResponse updatedGroup = groupService.updateGroup(owner, groupId, updateGroupRequest);
        return ResponseEntity.status(HttpStatus.OK).body(updatedGroup);
    }

    /**
     * 그룹 삭제
     * [DELETE] /groups/{groupId}
     */
    @DeleteMapping("/{groupId}")
    public ResponseEntity<?> deleteGroup(@AuthenticationPrincipal AuthUser authUser, @PathVariable Long groupId){
        User owner = authUser.getUser();
        groupService.deleteGroup(owner, groupId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 그룹리스트 추가
     * [POST] /group/{groupId}/groupList
     */
    @PostMapping("/{groupId}/groupList")
    public ResponseEntity<?> createGroupList(
            @AuthenticationPrincipal AuthUser authUser,@PathVariable Long groupId, @RequestBody GroupListRequest request){
        User user = authUser.getUser();
        groupService.createGroupList(groupId,request,user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 그룹 리스트 삭제
     * [DELETE] /groups/groupListId
     */
    @DeleteMapping("")
    public ResponseEntity<?> deleteGroupList(
            @AuthenticationPrincipal AuthUser authUser, @RequestParam(name = "groupListId") List<Long> groupListId){
        User user = authUser.getUser();
        groupService.deleteGroupList(groupListId,user);
        return ResponseEntity.ok().build();
    }

    /**
     * 그룹 리스트 조회 == 그룹 내 찜한 식당 정보
     * [GET] /groups/{groupId}/groupLists
     */
    @GetMapping("/{groupId}/groupLists")
    public ResponseEntity<List<GroupListResponse>> getGroupList(@PathVariable Long groupId){
        return ResponseEntity.ok().body(groupService.getAllGroupList(groupId));
    }
}
