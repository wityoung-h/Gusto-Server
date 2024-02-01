package com.umc.gusto.domain.group.model.request;

import com.umc.gusto.global.common.BaseEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

public class GroupRequestDto {
    @Getter
    public static class CreateGroupDTO{
        @NotBlank
        @Size(min=1, max=10)
        String groupName;
        @NotBlank
        @Size(min=1, max=20)
        String groupScript;
        BaseEntity.Status status = BaseEntity.Status.ACTIVE;
    }
}
