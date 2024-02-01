package com.umc.gusto.domain.group.model.response;

import com.umc.gusto.domain.group.entity.GroupMember;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class GroupResponseDto {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostGroupResponseDto{
        Long groupId;
        String groupName;
        String groupScript;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetGroupResponseDto{
        Long groupId;
        String groupName;
        String groupScript;
        String owner;
        String notice;
        List<GroupMemberResponseDto.GetGroupMemberResponseDto> groupMembers;
    }
}
