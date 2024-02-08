package com.umc.gusto.domain.group.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupListResponse {
    private String storeName;
    private String profileImg;
    private String address;
    private Long groupListId;

}
