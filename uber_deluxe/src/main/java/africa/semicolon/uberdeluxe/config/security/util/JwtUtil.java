package africa.semicolon.uberdeluxe.config.security.util;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JwtUtil {
    private final String jwtSecret;
}
