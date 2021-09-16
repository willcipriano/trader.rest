package trader.rest.combat.service;

import org.springframework.stereotype.Service;
import trader.rest.combat.dao.CharacterDao;
import trader.rest.combat.dao.CharacterSheetDao;
import trader.rest.combat.entity.CharacterSheet;

@Service
public class CharacterSheetService {

    public CharacterSheet fromDao(CharacterDao characterDao) {
        CharacterSheetDao characterSheetDao = characterDao.getCharacterSheetDao();
        CharacterSheet.CharacterSheetBuilder characterSheetBuilder = CharacterSheet.builder();

        characterSheetBuilder.maxCharisma(characterSheetDao.getMaxCharisma());
        characterSheetBuilder.maxConstitution(characterSheetDao.getMaxConstitution());
        characterSheetBuilder.maxDexterity(characterSheetDao.getMaxDexterity());
        characterSheetBuilder.maxIntelligence(characterSheetDao.getMaxIntelligence());
        characterSheetBuilder.maxStrength(characterSheetDao.getMaxStrength());
        characterSheetBuilder.maxWisdom(characterSheetDao.getMaxWisdom());
        characterSheetBuilder.name(characterSheetDao.getName());
        characterSheetBuilder.level(characterSheetDao.getLevel());

        return characterSheetBuilder.build();
    }


}
