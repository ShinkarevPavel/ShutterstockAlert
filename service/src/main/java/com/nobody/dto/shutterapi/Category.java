package com.nobody.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = CategoryEnumDeserializer.class)
public enum Category {
    @JsonProperty("25_a_day")
    SUBSCRIPTION,
    @JsonProperty("on_demand")
    ON_DEMAND,
    @JsonProperty("enhanced")
    ENHANCED,
    @JsonProperty("single_image_and_other")
    SINGLES,
    @JsonProperty("cart_sales")
    CART_SALES,
    @JsonProperty("clip_packs")
    CLIP_PACKS,
    @JsonProperty("footage_enhanced")
    FOOTAGE_ENHANCED,
    @JsonProperty("contributor_image_referrals")
    CONTRIBUTOR_IMAGE_REFERRALS,
    @JsonProperty("contributor_video_referrals")
    CONTRIBUTOR_VIDEO_REFERRALS,
    @JsonProperty("customer_referrals")
    CUSTOMER_REFERRALS;


    private String downloads;
    private String earnings;

    Category() {
    }

    Category(String downloads, String earnings) {
        this.downloads = downloads;
        this.earnings = earnings;
    }
}
