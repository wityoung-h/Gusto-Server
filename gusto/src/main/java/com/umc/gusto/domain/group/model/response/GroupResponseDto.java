package com.umc.gusto.domain.group.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
