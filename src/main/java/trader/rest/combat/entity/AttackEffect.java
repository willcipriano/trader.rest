package trader.rest.combat.entity;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;

@Data
@Builder
public class AttackEffect {
    EffectTimingEnum timing;
    String name;
    HashMap<StatTypeEnum, Double> statModifier;
    int effectTurns;

    int atkHitModifier;
    int atkDmgModifier;
    int defHitModifier;
    int defDmgModifier;
}
