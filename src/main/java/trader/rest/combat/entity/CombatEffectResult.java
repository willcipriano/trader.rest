package trader.rest.combat.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CombatEffectResult {
    String effectName;
    int atkHitModifier;
    int atkDmgModifier;
    int defHitModifier;
    int defDmgModifier;

    public static class CombatEffectResultBuilder {
        public CombatEffectResult quickEffect(String effectName, int atkHit, int atkDmg, int defHit, int defDmg) {
            this.atkHitModifier = atkHit;
            this.atkDmgModifier = atkDmg;
            this.defHitModifier = defHit;
            this.defDmgModifier = defDmg;
            this.effectName = effectName;
            return this.build();
        }
    }
}
