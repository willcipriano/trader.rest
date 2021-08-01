package trader.rest.combat.exception;

import trader.rest.combat.entity.Character;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Combat result applied twice.")
public class DoubleCombatResultApplicationException extends Exception {
    public DoubleCombatResultApplicationException(Character b, Character d) {
        super(String.format("Combat changes between %s and %s set as applied previously.", b.getName(), d.getName()));
    }
}
