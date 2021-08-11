package trader.rest.combat.entity;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

@Data
@Builder
public class ArmorStats {

    @NotNull
    int armorClass;

    @NotNull
    int armorBonus;

    public static class ArmorStatsBuilder {
        private int armorBonus;
        private int armorClass;

        private int calculateArmorBonus(Collection<ArmorItem> armorItems) {
                return armorItems.stream().mapToInt(a -> a.armorBonus).sum();
        }

        public ArmorStatsBuilder define(Collection<ArmorItem> armorItemList, int dexBonus) {
            this.armorBonus = calculateArmorBonus(armorItemList);
            this.armorClass = this.armorBonus + dexBonus;
            return this;
        }

    }
}
