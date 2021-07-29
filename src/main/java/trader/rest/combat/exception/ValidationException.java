package trader.rest.combat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import java.util.Set;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Character is invalid.")
public class ValidationException extends Exception {
    public ValidationException(Set<ConstraintViolation<Object>> violations) {
        for (ConstraintViolation<Object> constraintViolation: violations) {
            System.err.println(constraintViolation);
        }
    }
}