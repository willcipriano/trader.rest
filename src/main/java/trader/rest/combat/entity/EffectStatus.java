package trader.rest.combat.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EffectStatus {
    private int turnsRemaining;

    public boolean isValid() {
        return turnsRemaining > 0;
    }

    public void increment() {
        turnsRemaining -= 1;
    }
}
