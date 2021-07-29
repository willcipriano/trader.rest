package trader.rest.combat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.EXPECTATION_FAILED, reason = "Character AC calculation failed.")
public class CharacterDexAcBonusCalculationException extends CharacterInitException {
}