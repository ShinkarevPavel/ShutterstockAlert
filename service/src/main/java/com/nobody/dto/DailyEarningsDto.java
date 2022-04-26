package com.nobody.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DailyEarningsDto extends BaseDto{
    private String date;
    private String totalDownloads;
    private String totalEarnings;
    private String subscriptions;
    private String onDemand;
    private String enhanced;
    private String singles;
}
