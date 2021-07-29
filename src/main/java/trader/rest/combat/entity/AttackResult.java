package trader.rest.combat.entity;


import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
public class AttackResult {

    @NotNull
    @NotEmpty
    List<CombatResult> combatResults;

}
