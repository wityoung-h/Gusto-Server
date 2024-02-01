package com.umc.gusto.domain.group.model.response;

import com.umc.gusto.domain.group.entity.GroupMember;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class GroupMemberResponseDto {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetGroupMemberResponseDto{
        Long groupMemberId;
        String nickname;
        String profileImg;
    }
}
