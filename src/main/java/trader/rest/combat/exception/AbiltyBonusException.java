package trader.rest.combat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.EXPECTATION_FAILED, reason = "Character abilty bonus calculation failed.")
public class AbiltyBonusException extends Exception{
}
