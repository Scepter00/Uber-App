package africa.semicolon.uberdeluxe.config.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Getter
@Setter
public class MailConfig {
    private String apiKey;
    private String mailUrl;
}
