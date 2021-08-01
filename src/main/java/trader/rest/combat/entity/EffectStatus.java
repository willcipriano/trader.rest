package trader.rest.combat.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class EffectStatus {
    private int turnsRemaining;
    private Map<Character, Map<StatTypeEnum, Integer>> selfStatModifierApplied;
    private Map<Character, Map<StatTypeEnum, Integer>> targetStatModifierApplied;

    public boolean isValid() {
        return turnsRemaining > 0;
    }

    public void increment() {
        turnsRemaining -= 1;
    }
}
