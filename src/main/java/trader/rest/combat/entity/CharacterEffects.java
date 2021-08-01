package trader.rest.combat.entity;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
@Builder
public class CharacterEffects {
    @NotNull
    private Map<AttackEffect, EffectStatus> attackEffects;

    public void addAttackEffect(AttackEffect effect, EffectStatus status) {
        this.attackEffects.put(effect, status);
    }
}
