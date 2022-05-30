package com.nobody.dto;

public enum CategoryEarningsType {

    _25_A_DAY("Subscriptions"),
    _ON_DEMAND("On Demand"),
    _ENHANCED("Enhanced"),
    _SINGLE_IMAGE_AND_OTHER("Singles");

    private final String BotApi;

    CategoryEarningsType(String botApi) {
        BotApi = botApi;
    }

    public static String getKeyForBotApi(String shutterApiKey) {
        return CategoryEarningsType.valueOf(CategoryEarningsType.class, "_" + shutterApiKey.toUpperCase()).getBotApi();
    }

    private String getBotApi() {
        return BotApi;
    }
}
