package com.umc.gusto.domain.group.service;

import com.umc.gusto.domain.group.model.request.PostGroupRequest;
import com.umc.gusto.domain.group.model.request.TransferOwnershipRequest;
import com.umc.gusto.domain.group.model.request.UpdateGroupRequest;
import com.umc.gusto.domain.group.model.response.GetGroupResponse;
import com.umc.gusto.domain.group.model.response.TransferOwnershipResponse;
import com.umc.gusto.domain.group.model.response.UpdateGroupResponse;
import com.umc.gusto.domain.user.entity.User;

public interface GroupService {
    // 그룹 생성
    void createGroup(User owner, PostGroupRequest postGroupRequest);

    // 그룹 1건 조회
    GetGroupResponse getGroup(Long groupId);

    // 그룹 수정
    UpdateGroupResponse updateGroup(User owner, Long groupId, UpdateGroupRequest updateGroupRequest);

    // 그룹 삭제
    void deleteGroup(User owner, Long groupId);

    // 그룹 소유권 이전
    TransferOwnershipResponse transferOwnership(User owner, Long groupId, TransferOwnershipRequest transferOwnershipRequest);
}
