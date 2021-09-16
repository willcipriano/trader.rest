package trader.rest.combat.service;

import org.springframework.beans.factory.annotation.Autowired;
import trader.rest.combat.dao.CharacterDao;
import trader.rest.combat.entity.CharacterSheet;
import trader.rest.combat.entity.Character;
import trader.rest.combat.repository.CharacterDaoRepository;

import java.util.UUID;

public class CharacterService {

    CharacterDaoRepository repository;
    CharacterSheetService characterSheetService;

    @Autowired
    void CharacterService(CharacterDaoRepository characterDaoRepository, CharacterSheetService characterSheetService) {
        this.repository = characterDaoRepository;
        this.characterSheetService = characterSheetService;
    }


    Character getCharacterFromUuid(UUID uuid) {
        CharacterDao characterDao = repository.findByUuid(uuid);
        Character.CharacterBuilder character = Character.builder();

        CharacterSheet characterSheet = characterSheetService.fromDao(characterDao);
        character.sheet(characterSheet);

        return character.build();
    }

}
