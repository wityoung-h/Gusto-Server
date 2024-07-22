package com.umc.gusto.domain.user.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PublishingInfoRequest {
    private boolean publishReview;
    @JsonProperty("publishCategory")
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
