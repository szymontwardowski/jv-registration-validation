
package core.basesyntax.service; // albo dokładny pakiet, gdzie chcesz mieć wyjątek

public class UserValidationException extends RuntimeException {
    public UserValidationException(String message) {
        super(message);
    }
}
