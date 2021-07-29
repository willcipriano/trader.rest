package trader.rest.combat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Attack calculation failed.")
public class AttackInstanceEvaluationException extends Exception {
    public AttackInstanceEvaluationException(Exception error) {
        super(error);
    }
}
