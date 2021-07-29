package trader.rest.combat.entity;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

@Data
@Getter
@Setter
@EqualsAndHashCode
public class Armor {
    @NotNull
    ArmorItem head;

    @NotNull
    ArmorItem body;

    @NotNull
    ArmorItem hands;

    @NotNull
    ArmorItem feet;

    public List<ArmorItem> getArmorItemList() {
        return Arrays.asList(this.head, this.body, this.hands, this.feet);
    }
}
