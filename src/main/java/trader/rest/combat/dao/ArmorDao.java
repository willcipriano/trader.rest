package trader.rest.combat.dao;

import lombok.Data;
import trader.rest.combat.entity.ArmorItem;
import trader.rest.combat.entity.ArmorSlotEnum;

import java.util.Map;


@Data
public class ArmorDao  {
    Map<ArmorSlotEnum, ArmorItem> items;
}
