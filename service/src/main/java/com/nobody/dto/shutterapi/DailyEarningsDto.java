package com.nobody.dto.shutterapi;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nobody.dto.BaseDto;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
public class DailyEarningsDto extends BaseDto {
    private String date;
    private String earnings;
    private String downloads;

    @JsonProperty("25_a_day")
    private Category subscription;

    @JsonProperty("on_demand")
    private Category onDemand;

    @JsonProperty("enhanced")
    private Category enhanced;

    @JsonProperty("single_image_and_other")
    private Category singlesAndOther;

    @JsonProperty("cart_sales")
    @JsonIgnore
    private Category cart_sales;

    @JsonProperty("clip_packs")
    @JsonIgnore
    private Category clip_packs;

    @JsonProperty("footage_enhanced")
    @JsonIgnore
    private Category footage_enhanced;

    @JsonProperty("contributor_image_referrals")
    @JsonIgnore
    private Category contributor_image_referrals;

    @JsonProperty("contributor_video_referrals")
    @JsonIgnore
    private Category contributor_video_referrals;

    @JsonProperty("customer_referrals")
    @JsonIgnore
    private Category customer_referrals;
}
