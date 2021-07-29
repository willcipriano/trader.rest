package trader.rest.combat.entity;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class ArmorItem {
    @NotNull
    ArmorTypeEnum type;

    @NotNull
    ArmorSlotEnum slot;

    @Min(0)
    @Max(20)
    int armorBonus;
}
