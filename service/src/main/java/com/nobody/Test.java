package com.nobody;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.ToString;

public class Test {

  private static String json =
      "{\n"
          + "\"days\": [\n"
          + "{\n"
          + "\"date\": \"2022-09-01\",\n"
          + "\"earnings\": 26.72,\n"
          + "\"downloads\": 59,\n"
          + "\"25_a_day\": {\n"
          + "\"earnings\": 12.25,\n"
          + "\"downloads\": 54\n"
          + "},\n"
          + "\"on_demand\": {\n"
          + "\"earnings\": 5.02,\n"
          + "\"downloads\": 2\n"
          + "},\n"
          + "\"single_image_and_other\": {\n"
          + "\"earnings\": 9.45,\n"
          + "\"downloads\": 3\n"
          + "}\n"
          + "},\n"
          + "{\n"
          + "\"date\": \"2022-09-02\",\n"
          + "\"earnings\": 18.21,\n"
          + "\"downloads\": 41,\n"
          + "\"25_a_day\": {\n"
          + "\"earnings\": 8.96,\n"
          + "\"downloads\": 38\n"
          + "},\n"
          + "\"on_demand\": {\n"
          + "\"earnings\": 6.1,\n"
          + "\"downloads\": 2\n"
          + "},\n"
          + "\"single_image_and_other\": {\n"
          + "\"earnings\": 3.15,\n"
          + "\"downloads\": 1\n"
          + "}\n"
          + "}],\n"
          + "\"earnings\": 137.70999999999998,\n"
          + "\"downloads\": 336,\n"
          + "\"categories\": {\n"
          + "\"25_a_day\": {\n"
          + "\"earnings\": 63.620000000000005,\n"
          + "\"downloads\": 315\n"
          + "},\n"
          + "\"enhanced\": {\n"
          + "\"earnings\": 0,\n"
          + "\"downloads\": 0\n"
          + "},\n"
          + "\"on_demand\": {\n"
          + "\"earnings\": 38.37,\n"
          + "\"downloads\": 12\n"
          + "},\n"
          + "\"single_image_and_other\": {\n"
          + "\"earnings\": 35.72,\n"
          + "\"downloads\": 9\n"
          + "},\n"
          + "\"cart_sales\": {\n"
          + "\"earnings\": 0,\n"
          + "\"downloads\": 0\n"
          + "},\n"
          + "\"clip_packs\": {\n"
          + "\"earnings\": 0,\n"
          + "\"downloads\": 0\n"
          + "},\n"
          + "\"footage_enhanced\": {\n"
          + "\"earnings\": 0,\n"
          + "\"downloads\": 0\n"
          + "},\n"
          + "\"contributor_image_referrals\": {\n"
          + "\"earnings\": 0,\n"
          + "\"downloads\": 0\n"
          + "},\n"
          + "\"contributor_video_referrals\": {\n"
          + "\"earnings\": 0,\n"
          + "\"downloads\": 0\n"
          + "},\n"
          + "\"customer_referrals\": {\n"
          + "\"earnings\": 0,\n"
          + "\"downloads\": 0\n"
          + "}\n"
          + "}\n"
          + "}";

  private static String json1 =
      "{\n"
          + "\"date\": \"2022-09-01\",\n"
          + "\"earnings\": 26.72,\n"
          + "\"downloads\": 59,\n"
          + "\"25_a_day\": {\n"
          + "\"earnings\": 12.25,\n"
          + "\"downloads\": 54\n"
          + "},\n"
          + "\"on_demand\": {\n"
          + "\"earnings\": 5.02,\n"
          + "\"downloads\": 2\n"
          + "},\n"
          + "\"single_image_and_other\": {\n"
          + "\"earnings\": 9.45,\n"
          + "\"downloads\": 3\n"
          + "}}";

  public static void main(String[] args) throws JsonProcessingException {

    ObjectMapper objectMapper = new ObjectMapper();

    Daily daily = objectMapper.readValue(json1, Daily.class);
    System.out.println(daily);
    System.out.println("------------");
    System.out.println(daily.getSubsc().getDownloads() + " " + daily.getSubsc().getEarnings() + "  Subscription");
  }

  @ToString
  @Getter
  public static class Daily {
    @JsonProperty("date")
    private String date;

    @JsonProperty("earnings")
    private String earnings;

    @JsonProperty("downloads")
    private String downloads;

    @JsonProperty("25_a_day")
    private CategoryT subsc;

    @JsonProperty("on_demand")
    private CategoryT onDemand;

    @JsonProperty("single_image_and_other")
    private CategoryT single;
  }

  @ToString
  @Getter
  public static class CategoryT {

    @JsonProperty("earnings")
    private String earnings;

    @JsonProperty("downloads")
    private String downloads;
  }
}
