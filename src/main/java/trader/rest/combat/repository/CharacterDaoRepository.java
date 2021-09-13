package trader.rest.combat.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import trader.rest.combat.dao.CharacterDao;
import trader.rest.combat.dao.CharacterInventoryDao;
import trader.rest.combat.dao.CharacterSheetDao;
import trader.rest.combat.dao.CharacterStatusDao;
import trader.rest.combat.entity.CharacterDaoComponentEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class CharacterDaoRepository {

    CharacterStatusDaoRepository characterStatusDaoRepository;

    CharacterSheetDaoRepository characterSheetDaoRepository;

    CharacterInventoryDaoRepository characterInventoryDaoRepository;

    private static final List<CharacterDaoComponentEnum> allFields = List.of(CharacterDaoComponentEnum.STATUS, CharacterDaoComponentEnum.INVENTORY);

    @Autowired
    public CharacterDaoRepository(CharacterStatusDaoRepository characterStatusDaoRepository, CharacterSheetDaoRepository characterSheetDaoRepository, CharacterInventoryDaoRepository characterInventoryDaoRepository) {
        this.characterStatusDaoRepository = characterStatusDaoRepository;
        this.characterSheetDaoRepository = characterSheetDaoRepository;
        this.characterInventoryDaoRepository = characterInventoryDaoRepository;
    }

    CharacterDao findByUuid(UUID uuid) {
        return findFieldsByUuid(uuid, allFields);
    }

    CharacterDao findFieldsByUuid(UUID uuid, List<CharacterDaoComponentEnum> fields) {
        List<CharacterDaoComponentEnum> pulledFields = new ArrayList<>();
        CharacterDao.CharacterDaoBuilder characterDaoBuilder = CharacterDao.builder();
        characterDaoBuilder.uuid(uuid);
        characterDaoBuilder.containsFields(pulledFields);

        CharacterSheetDao characterSheetDao = characterSheetDaoRepository.findByUuid(uuid);
        characterDaoBuilder.characterSheetDao(characterSheetDao);

        if (characterSheetDao == null) {
            return characterDaoBuilder.build();
        }

        pulledFields.add(CharacterDaoComponentEnum.SHEET);


        for (CharacterDaoComponentEnum characterDaoComponentEnum: fields) {
            switch (characterDaoComponentEnum) {
                case STATUS:
                    CharacterStatusDao characterStatusDao = characterStatusDaoRepository.findByCharacterSheetUuid(uuid);
                    characterDaoBuilder.characterStatusDao(characterStatusDao);
                    pulledFields.add(CharacterDaoComponentEnum.STATUS);
                    break;

                case INVENTORY:
                    CharacterInventoryDao characterInventoryDao = characterInventoryDaoRepository.findByCharacterSheetUuid(uuid);
                    characterDaoBuilder.characterInventoryDao(characterInventoryDao);
                    pulledFields.add(CharacterDaoComponentEnum.INVENTORY);
                    break;
            }
        }

        return characterDaoBuilder.build();
    }
}
