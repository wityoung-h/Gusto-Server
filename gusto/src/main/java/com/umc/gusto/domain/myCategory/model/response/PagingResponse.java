package com.umc.gusto.domain.myCategory.model.response;

import com.umc.gusto.global.common.PublishStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class PagingResponse {
    private boolean hasNext;
    private PublishStatus userPublishCategory;
    private PublishStatus publishCategory;
    private List<?> result;
}
