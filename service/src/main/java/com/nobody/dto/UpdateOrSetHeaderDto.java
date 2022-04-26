package com.nobody.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateOrSetHeaderDto extends BaseDto {
    private String accessKey;
    private String headerParameter;
    private String headerValue;
}
