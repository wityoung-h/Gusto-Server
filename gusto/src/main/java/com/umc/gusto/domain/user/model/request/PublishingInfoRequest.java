package com.umc.gusto.domain.user.model.request;

public class PublishingInfoRequest {
    private boolean publishReview;
    private boolean publishPin;

    public boolean getPublishReview() {
        return this.publishReview;
    }

    public boolean getPublishPin() {
        return this.publishPin;
    }
}
