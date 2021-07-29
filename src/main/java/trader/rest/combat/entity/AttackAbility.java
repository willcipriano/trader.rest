package trader.rest.combat.entity;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
@Builder
public class AttackAbility {
    @NotNull
    String name;

    @NotNull
    String adjective;

    @NotNull
    @Min(-999)
    @Max(999)
    int baseDmg;

    @NotNull
    @Min(-999)
    @Max(999)
    int baseHit;

    @NotNull
    @Min(0)
    @Max(999)
    int maxTargets;

    @NotNull
    Map<StatTypeEnum, Double> abilityModifierDmgAtk;

    @NotNull
    Map<StatTypeEnum, Double> abilityModifierHitAtk;

    @NotNull
    Map<StatTypeEnum, Double> abilityModifierDmgDef;

    @NotNull
    Map<StatTypeEnum, Double> abilityModifierHitDef;
}
