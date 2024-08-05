package com.umc.gusto.domain.user.model.request;

import lombok.Getter;

@Getter
public class PublishingInfoRequest {
    private boolean publishReview;
    private boolean publishPin;
    private boolean publishRoute;

    public boolean getPublishReview() {
        return this.publishReview;
    }

    public boolean getPublishPin() {
        return this.publishPin;
    }

    public boolean getPublishRoute() { return this.publishRoute; }
}
