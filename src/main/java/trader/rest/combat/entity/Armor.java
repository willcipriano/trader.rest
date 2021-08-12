package trader.rest.combat.entity;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Data
@Getter
@Setter
@EqualsAndHashCode
public class Armor implements Serializable {
    @NotNull
    Map<ArmorSlotEnum, ArmorItem> items;

    private void setSlot(ArmorItem item, ArmorSlotEnum slot) {
        if (this.items == null) {
            this.items = new HashMap<>();
        }

        if (item.slot.equals(slot)) {
        this.items.put(slot, item);
        }
    }

    public Collection<ArmorItem> getArmorItemList() {
        return this.items.values();
    }

    public void setHead(ArmorItem item) {
        setSlot(item, ArmorSlotEnum.HEAD);
    }

    public void setFeet(ArmorItem item) {
        setSlot(item, ArmorSlotEnum.FEET);
    }

    public void setBody(ArmorItem item) {
        setSlot(item, ArmorSlotEnum.BODY);
    }

    public void setHands(ArmorItem item) {
        setSlot(item, ArmorSlotEnum.HANDS);
    }
}
