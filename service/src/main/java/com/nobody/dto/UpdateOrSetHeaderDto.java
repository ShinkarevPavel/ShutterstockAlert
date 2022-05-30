package com.nobody.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateOrSetHeaderDto extends BaseDto {

    public interface Create {
    }

    public interface Update {
    }

    @NotNull
    private String accessKey;

    @NotNull(groups = {Create.class, Update.class})
    private String headerParameter;

    @NotNull(groups = {Create.class, Update.class})
    private String headerValue;

}
