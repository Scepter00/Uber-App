package africa.semicolon.uberdeluxe.exception;

public class UserNotFoundException extends BusinessLogicException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
