package africa.semicolon.uberdeluxe.data.dto.request;


import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class Recipient {
    private String name;
    private String email;
}
