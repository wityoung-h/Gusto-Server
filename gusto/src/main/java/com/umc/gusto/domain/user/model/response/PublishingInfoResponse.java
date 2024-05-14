package com.umc.gusto.domain.user.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class PublishingInfoResponse {
    private boolean publishReview;
    private boolean publishPin;
    private boolean publishRoute;
}
