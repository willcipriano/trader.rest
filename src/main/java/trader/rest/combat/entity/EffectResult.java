package trader.rest.combat.entity;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Data
@Builder
public class EffectResult {
    @NotNull
    String effectName;

    @NotNull
    Map<StatTypeEnum, Integer> selfStatModifier;

    @NotNull
    Map<StatTypeEnum, Integer> targetStatModifier;

    @NotNull
    int effectTurnsRemaining;

    int atkHitModifier;
    int atkDmgModifier;
    int defHitModifier;
    int defDmgModifier;

    public void recordTargetStatChange(StatTypeEnum statType, Integer change) {
        if (targetStatModifier == null) {
            targetStatModifier = new HashMap<>();
        }

        if (targetStatModifier.containsKey(statType)) {
            targetStatModifier.replace(statType, targetStatModifier.get(statType) + change);
        } else {
            targetStatModifier.put(statType, change); }
    }

    public void recordSelfStatChange(StatTypeEnum statType, Integer change) {
        if (selfStatModifier == null) {
            selfStatModifier = new HashMap<>();
        }

        if (selfStatModifier.containsKey(statType)) {
            selfStatModifier.replace(statType, selfStatModifier.get(statType) + change);
        } else {
            selfStatModifier.put(statType, change); }
    }
}
