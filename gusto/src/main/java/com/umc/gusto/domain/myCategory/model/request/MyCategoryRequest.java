package com.umc.gusto.domain.myCategory.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.umc.gusto.global.common.BaseEntity;
import com.umc.gusto.global.common.PublishStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

public class MyCategoryRequest {

    @Getter
    public static class createMyCategory {
        @Size(max=10, message = "카테고리 이름은 10자를 초과할 수 없습니다.")
        String myCategoryName;
        @Size(max=20, message = "카테고리 설명은 20자를 초과할 수 없습니다.")
        String myCategoryScript;
        Integer myCategoryIcon;
        PublishStatus publishCategory;
        BaseEntity.Status status = BaseEntity.Status.ACTIVE;

    }

    @Getter
    public static class updateMyCategory {
        String myCategoryName;
        String myCategoryScript;
        Integer myCategoryIcon;
        PublishStatus publishCategory = PublishStatus.PUBLIC;

    }

    @Getter
    public static class deleteMyCategory {
        BaseEntity.Status status;
    }
}
