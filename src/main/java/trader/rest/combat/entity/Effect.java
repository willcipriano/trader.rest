package trader.rest.combat.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class Effect {
    String name;
    Map<StatTypeEnum, Integer> selfStatModifier;
    Map<StatTypeEnum, Integer> targetStatModifier;
    EffectTimingEnum timing;
    int effectTurns;
}
