package com.nobody.dto;


import javax.validation.constraints.NotEmpty;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TelegramBotCredentialsDto extends BaseDto {

    public interface Create {
    }

    public interface Update {
    }

    @NotEmpty(message = "access key is not valid", groups = {Create.class, Update.class})
    private String accessKey;

    @NotEmpty(message = "token is not valid", groups = {Create.class, Update.class})
    private String token;

    @NotEmpty(message = "chatId is not valid", groups = {Create.class})
    private String chatId;
}
