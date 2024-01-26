package com.umc.gusto.domain.myCategory.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.umc.gusto.global.common.BaseEntity;
import com.umc.gusto.global.common.PublishStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

public class MyCategoryRequest {

    @Getter
    public static class createMyCategoryDTO {
        @NotBlank
        String myCategoryName;

        String myCategoryScript;

        @NotNull
        Integer myCategoryIcon;

        @NotNull
        PublishStatus publishCategory;

        BaseEntity.Status status = BaseEntity.Status.ACTIVE;

    }

    @Getter
    public static class updateMyCategoryDTO {
        String myCategoryName;
        String myCategoryScript;
        Integer myCategoryIcon;
        PublishStatus publishCategory;

    }

    @Getter
    public static class deleteMyCategoryDTO {
        BaseEntity.Status status;
    }
}
