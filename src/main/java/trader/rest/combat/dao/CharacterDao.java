package trader.rest.combat.dao;

import lombok.Builder;
import lombok.Data;
import trader.rest.combat.entity.CharacterDaoComponentEnum;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class CharacterDao implements CharacterComponent {
    UUID uuid;
    List<CharacterDaoComponentEnum> containsFields;
    CharacterSheetDao characterSheetDao;
    CharacterStatusDao characterStatusDao;
    CharacterInventoryDao characterInventoryDao;

    public boolean isFetchSuccess() {
        return containsFields.contains(CharacterDaoComponentEnum.SHEET);
    }
}
