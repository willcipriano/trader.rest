package trader.rest.combat.dao;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CharacterDao {
    UUID uuid;
    boolean fetchSuccess;
    CharacterSheetDao characterSheetDao;
    CharacterStatusDao characterStatusDao;
    CharacterInventoryDao characterInventoryDao;
}
