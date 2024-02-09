package com.umc.gusto.domain.group.controller;

import com.umc.gusto.domain.group.model.request.JoinGroupRequest;
import com.umc.gusto.domain.group.model.request.PostGroupRequest;
import com.umc.gusto.domain.group.model.request.TransferOwnershipRequest;
import com.umc.gusto.domain.group.model.request.UpdateGroupRequest;
import com.umc.gusto.domain.group.model.response.GetGroupMemberResponse;
import com.umc.gusto.domain.group.model.response.GetGroupResponse;
import com.umc.gusto.domain.group.model.response.GetInvitationCodeResponse;
import com.umc.gusto.domain.group.model.response.TransferOwnershipResponse;
import com.umc.gusto.domain.group.model.response.GetGroupsResponse;
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
     * 초대 코드 조회
     * [GET] /groups/{groupId}/invitationCode
     */
    @GetMapping("/{groupId}/invitationCode")
    public ResponseEntity<GetInvitationCodeResponse> getInvitationCode(@PathVariable Long groupId){
        GetInvitationCodeResponse getInvitationCode = groupService.getInvitationCode(groupId);
        return ResponseEntity.status(HttpStatus.OK).body(getInvitationCode);
      
    /**
     * 그룹 소유권 이전
     * [PATCH] /groups/{groupId}/transfer-ownership
     */
    @PatchMapping("/{groupId}/transfer-ownership")
    public ResponseEntity<TransferOwnershipResponse> transferOwnership(@AuthenticationPrincipal AuthUser authUser, @PathVariable Long groupId, @RequestBody TransferOwnershipRequest transferOwnershipRequest){
        User owner = authUser.getUser();
        TransferOwnershipResponse transferOwnership = groupService.transferOwnership(owner, groupId, transferOwnershipRequest);
        return ResponseEntity.status(HttpStatus.OK).body(transferOwnership);
      
    /**
     * 그룹 참여
     * [POST] /groups/{groupId}/join
     */
    @PostMapping("/{groupId}/join")
    public ResponseEntity<?> joinGroup(@AuthenticationPrincipal AuthUser authUser, @PathVariable Long groupId, @RequestBody JoinGroupRequest joinGroupRequest){
        User user = authUser.getUser();
        groupService.joinGroup(user, groupId, joinGroupRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 그룹 탈퇴
     * [DELETE] /groups/{groupId}/leave
     */
    @DeleteMapping("/{groupId}/leave")
    public ResponseEntity<?> leaveGroup(@AuthenticationPrincipal AuthUser authUser, @PathVariable Long groupId){
        User user = authUser.getUser();
        groupService.leaveGroup(user, groupId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
  
    /**
     * 그룹 목록 조회
     * [GET] /groups
     */
    @GetMapping
    public ResponseEntity<List<GetGroupsResponse>> getGroups(@AuthenticationPrincipal AuthUser authUser){
        User user = authUser.getUser();
        List<GetGroupsResponse> getGroups = groupService.getUserGroups(user);
        return ResponseEntity.status(HttpStatus.OK).body(getGroups);
    }

    /**
     * 그룹 구성원 조회
     * [GET] /groups/{groupId}/members
     */
    @GetMapping("/{groupId}/members")
    public ResponseEntity<List<GetGroupMemberResponse>> getGroupMembers (@PathVariable Long groupId){
        List<GetGroupMemberResponse> getGroupMembers = groupService.getGroupMembers(groupId);
        return ResponseEntity.status(HttpStatus.OK).body(getGroupMembers);
    }
}
