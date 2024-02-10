package com.umc.gusto.domain.group.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class JoinGroupRequest {
    @NotBlank
    @Size(min=12, max= 12, message = "초대 코드는 12자 입니다.")
    String code;
}