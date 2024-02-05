package com.umc.gusto.domain.group.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetGroupResponse {
    Long groupId;
    String groupName;
    String groupScript;
    Long owner;
    String notice;
    List<GetGroupMemberResponse> groupMembers;
}
