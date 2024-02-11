package com.umc.gusto.domain.group.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class GroupListResponse {
    private String storeName;
    private String storeProfileImg;
    private String userProfileImg;
    private String address;
    private Long groupListId;

}
