package com.nobody.dto.shutterapi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Category {
    private String name;
    private String earnings;
    private String downloads;
    private String type;

}
