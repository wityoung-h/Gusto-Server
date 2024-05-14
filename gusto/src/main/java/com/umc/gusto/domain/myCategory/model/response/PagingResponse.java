package com.umc.gusto.domain.myCategory.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class PagingResponse {
    private boolean hasNext;
    private List<?> result;
}
