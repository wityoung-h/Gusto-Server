package com.umc.gusto.domain.group.model.request;

import com.umc.gusto.domain.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class TransferOwnershipRequest {
    @NotBlank
    Long newOwner;  // memberId
}
