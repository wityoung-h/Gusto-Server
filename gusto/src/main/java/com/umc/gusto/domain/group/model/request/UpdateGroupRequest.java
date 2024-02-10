package com.umc.gusto.domain.group.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateGroupRequest {
    @NotBlank
    @Size(max=10, message = "그룹 이름은 10자를 초과할 수 없습니다.")
    String groupName;
    @Size(max=50, message = "그룹 공지는 50자를 초과할 수 없습니다.")
    String notice;
}
