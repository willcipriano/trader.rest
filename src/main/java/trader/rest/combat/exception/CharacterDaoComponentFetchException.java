package trader.rest.combat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import trader.rest.combat.entity.CharacterDaoComponentEnum;

import java.util.UUID;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "CharacterDao fetch failed.")
public class CharacterDaoComponentFetchException extends Exception{
    public CharacterDaoComponentFetchException(CharacterDaoComponentEnum failedComponent, UUID characterUUID) {
        super(String.format("Unable to fetch component %s for character sheet %s.", failedComponent.name, characterUUID));
    }
}
