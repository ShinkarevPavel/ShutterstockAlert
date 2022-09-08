package com.nobody.dto.shutterapi;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MonthCategories {

  @JsonProperty("25_a_day")
  private Category subscription;

  @JsonProperty("on_demand")
  private Category on_demand;

  @JsonProperty("enhanced")
  private Category enhanced;

  @JsonProperty("single_image_and_other")
  private Category single_image_and_other;

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
