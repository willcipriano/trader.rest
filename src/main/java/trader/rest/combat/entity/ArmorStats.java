package trader.rest.combat.entity;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
public class ArmorStats {

    @NotNull
    int armorClass;

    @NotNull
    int armorBonus;

    public static class ArmorStatsBuilder {
        private int armorTotal;
        private int armorClass;

        private int calculateArmorBonus(List<ArmorItem> armorItems) {
                return armorItems.stream().mapToInt(a -> a.armorBonus).sum();
        }

        public ArmorStatsBuilder define(List<ArmorItem> armorItemList, int dexBonus) {
            this.armorTotal = calculateArmorBonus(armorItemList);
            this.armorClass = this.armorTotal + dexBonus;
            return this;
        }

    }
}
