package trader.rest.combat.repository;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import trader.rest.combat.dao.*;
import trader.rest.combat.entity.CharacterDaoComponentEnum;
import trader.rest.combat.exception.CharacterDaoComponentFetchException;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


@SpringBootTest
class CharacterDaoRepositoryTest {

    CharacterSheetDao createCharacterSheetDao(UUID uuid, String name, int level, int maxStat) {
        CharacterSheetDao characterSheetDao = new CharacterSheetDao();
        characterSheetDao.setLevel(level);
        characterSheetDao.setMaxCharisma(maxStat);
        characterSheetDao.setMaxDexterity(maxStat);
        characterSheetDao.setMaxIntelligence(maxStat);
        characterSheetDao.setMaxStrength(maxStat);
        characterSheetDao.setMaxWisdom(maxStat);
        characterSheetDao.setName(name);
        characterSheetDao.setUuid(uuid);
        return characterSheetDao;
    }

    CharacterStatusDao createCharacterStatusDao(UUID uuid, int curStat, int hitPoints) {
        CharacterStatusDao characterStatusDao = new CharacterStatusDao();
        characterStatusDao.setCharacterSheetUuid(uuid);
        characterStatusDao.setCurrentCharisma(curStat);
        characterStatusDao.setCurrentDexterity(curStat);
        characterStatusDao.setCurrentIntelligence(curStat);
        characterStatusDao.setCurrentStrength(curStat);
        characterStatusDao.setCurrentWisdom(curStat);
        characterStatusDao.setCurrentHitPoints(hitPoints);
        return characterStatusDao;
    }

    CharacterInventoryDao createCharacterInventoryDao(UUID uuid) {
        CharacterInventoryDao characterInventoryDao = new CharacterInventoryDao();
        characterInventoryDao.setCharacterSheetUuid(uuid);

        ArmorDao armorDao = new ArmorDao();
        armorDao.setItems(Collections.emptyMap());

        characterInventoryDao.setArmorDao(armorDao);
        return characterInventoryDao;
    }

    @Test
    void testFindByUuid() {
        CharacterSheetDaoRepository characterSheetDaoRepository = Mockito.mock(CharacterSheetDaoRepository.class);
        UUID characterUuid = UUID.randomUUID();

        Mockito.when(characterSheetDaoRepository.findByUuid(characterUuid))
                .thenReturn(createCharacterSheetDao(characterUuid, "Test Char", 1, 2));

        CharacterStatusDaoRepository characterStatusDaoRepository = Mockito.mock(CharacterStatusDaoRepository.class);

        Mockito.when(characterStatusDaoRepository.findByCharacterSheetUuid(characterUuid)).thenReturn(createCharacterStatusDao(characterUuid, 1, 1));

        CharacterInventoryDaoRepository characterInventoryDaoRepository = Mockito.mock(CharacterInventoryDaoRepository.class);

        Mockito.when(characterInventoryDaoRepository.findByCharacterSheetUuid(characterUuid)).thenReturn(createCharacterInventoryDao(characterUuid));

        CharacterDaoRepository characterDaoRepository = new CharacterDaoRepository(characterStatusDaoRepository, characterSheetDaoRepository, characterInventoryDaoRepository);

        CharacterDao characterDao = characterDaoRepository.findByUuid(characterUuid);

        assertEquals(characterUuid, characterDao.getUuid());
        assertTrue(characterDao.isFetchSuccess());
    }

    @Test
    void testDontFindByUuid() {
        CharacterSheetDaoRepository characterSheetDaoRepository = Mockito.mock(CharacterSheetDaoRepository.class);
        UUID characterUuid = UUID.randomUUID();

        Mockito.when(characterSheetDaoRepository.findByUuid(characterUuid))
                .thenReturn(null);

        CharacterStatusDaoRepository characterStatusDaoRepository = Mockito.mock(CharacterStatusDaoRepository.class);
        CharacterInventoryDaoRepository characterInventoryDaoRepository = Mockito.mock(CharacterInventoryDaoRepository.class);

        CharacterDaoRepository characterDaoRepository = new CharacterDaoRepository(characterStatusDaoRepository, characterSheetDaoRepository, characterInventoryDaoRepository);

        CharacterDao characterDao = characterDaoRepository.findByUuid(characterUuid);

        assertEquals(characterUuid, characterDao.getUuid());
        assertFalse(characterDao.isFetchSuccess());
    }

    @Test
    void testFindFieldsByUuid() throws CharacterDaoComponentFetchException {
        CharacterSheetDaoRepository characterSheetDaoRepository = Mockito.mock(CharacterSheetDaoRepository.class);
        UUID characterUuid = UUID.randomUUID();

        Mockito.when(characterSheetDaoRepository.findByUuid(characterUuid))
                .thenReturn(createCharacterSheetDao(characterUuid, "Test Char", 1, 2));

        CharacterStatusDaoRepository characterStatusDaoRepository = Mockito.mock(CharacterStatusDaoRepository.class);

        Mockito.when(characterStatusDaoRepository.findByCharacterSheetUuid(characterUuid)).thenReturn(createCharacterStatusDao(characterUuid, 1, 1));

        CharacterInventoryDaoRepository characterInventoryDaoRepository = Mockito.mock(CharacterInventoryDaoRepository.class);

        Mockito.when(characterInventoryDaoRepository.findByCharacterSheetUuid(characterUuid)).thenReturn(createCharacterInventoryDao(characterUuid));

        CharacterDaoRepository characterDaoRepository = new CharacterDaoRepository(characterStatusDaoRepository, characterSheetDaoRepository, characterInventoryDaoRepository);

        CharacterDao characterDaoStatus = characterDaoRepository.findFieldsByUuid(characterUuid, List.of(CharacterDaoComponentEnum.STATUS));
        CharacterDao characterDaoInventory = characterDaoRepository.findFieldsByUuid(characterUuid, List.of(CharacterDaoComponentEnum.INVENTORY));

        assertNotNull(characterDaoStatus.getCharacterStatusDao());
        assertNotNull(characterDaoInventory.getCharacterInventoryDao());

        assertNull(characterDaoStatus.getCharacterInventoryDao());
        assertNull(characterDaoInventory.getCharacterStatusDao());

        assertTrue(characterDaoInventory.getContainsFields().contains(CharacterDaoComponentEnum.INVENTORY));
        assertTrue(characterDaoStatus.getContainsFields().contains(CharacterDaoComponentEnum.STATUS));
    }

}
