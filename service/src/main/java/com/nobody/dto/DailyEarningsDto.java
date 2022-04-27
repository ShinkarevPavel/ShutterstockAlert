package com.nobody.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class DailyEarningsDto extends BaseDto{
    private String date;
    private String totalDownloads;
    private String totalEarnings;
    private String subscriptions;
    private String onDemand;
    private String enhanced;
    private String singles;
}
