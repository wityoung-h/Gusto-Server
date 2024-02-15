package com.umc.gusto.domain.group.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetGroupsResponse {
    Long groupId;
    String groupName;
    Boolean isOwner;
    Integer numMembers;
    Integer numRestaurants;
    Integer numRoutes;
}
