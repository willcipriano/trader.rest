package trader.rest.combat.entity;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

@Data
@Builder
public class CharacterEffects implements Serializable {
    @NotNull
    private Map<Effect, EffectStatus> effects;

    public static class CharacterEffectsBuilder {
        public CharacterEffects buildEmpty() {
            this.effects = Collections.emptyMap();
            return this.build();
        }
    }
}
