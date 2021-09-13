package trader.rest.combat.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import trader.rest.combat.dao.CharacterDao;
import trader.rest.combat.dao.CharacterInventoryDao;
import trader.rest.combat.dao.CharacterSheetDao;
import trader.rest.combat.dao.CharacterStatusDao;

import java.util.UUID;

@Repository
public class CharacterDaoRepository {

    CharacterStatusDaoRepository characterStatusDaoRepository;

    CharacterSheetDaoRepository characterSheetDaoRepository;

    CharacterInventoryDaoRepository characterInventoryDaoRepository;

    @Autowired
    public CharacterDaoRepository(CharacterStatusDaoRepository characterStatusDaoRepository, CharacterSheetDaoRepository characterSheetDaoRepository, CharacterInventoryDaoRepository characterInventoryDaoRepository) {
        this.characterStatusDaoRepository = characterStatusDaoRepository;
        this.characterSheetDaoRepository = characterSheetDaoRepository;
        this.characterInventoryDaoRepository = characterInventoryDaoRepository;
    }

    CharacterDao findByUuid(UUID uuid) {
        CharacterDao.CharacterDaoBuilder characterDaoBuilder = CharacterDao.builder();
        characterDaoBuilder.uuid(uuid);

        CharacterSheetDao characterSheetDao = characterSheetDaoRepository.findByUuid(uuid);

        if (characterSheetDao == null) {
            characterDaoBuilder.fetchSuccess(false);
            return characterDaoBuilder.build();
        } else {
            characterDaoBuilder.characterSheetDao(characterSheetDao);
        }

        CharacterStatusDao characterStatusDao = characterStatusDaoRepository.findByCharacterSheetUuid(uuid);
        characterDaoBuilder.characterStatusDao(characterStatusDao);




        characterDaoBuilder.fetchSuccess(true);

        return characterDaoBuilder.build();
    }
}
