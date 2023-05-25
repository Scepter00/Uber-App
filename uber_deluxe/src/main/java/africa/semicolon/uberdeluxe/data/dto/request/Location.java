package africa.semicolon.uberdeluxe.data.dto.request;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Location {
    private String houseNumber;
    private String street;
    private String city;

    private String state;
}
