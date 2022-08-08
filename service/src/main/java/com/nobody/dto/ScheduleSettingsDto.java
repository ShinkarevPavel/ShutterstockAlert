package com.nobody.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ScheduleSettingsDto {

    public interface Change {
    }

    public interface Create {
    }

    public interface Update {
    }

    public interface Delete {
    }

    @NotNull(groups = {Create.class, Update.class, Delete.class})
    private String accessKey;

    @NotNull(groups = {Create.class, Update.class, Delete.class})
    private String name;

    @Null(groups = Change.class)
    private String value;

    @NotNull(groups = {Create.class, Update.class, Delete.class})
    @Null(groups = Change.class)
    private Boolean isCurrent;
}
