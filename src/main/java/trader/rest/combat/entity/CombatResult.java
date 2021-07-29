package trader.rest.combat.entity;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
public class CombatResult {

    @NotNull
    Character belligerent;

    @NotNull
    Character defender;

    @NotNull
    int hpChange;

    List<CombatFailureEnum> combatFailureReasons;
}
