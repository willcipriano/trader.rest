package trader.rest.combat.entity;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
public class AttackRequest {
    @NotNull
    @NotEmpty
    public List<Character> belligerents;

    @NotNull
    @NotEmpty
    public List<Character> defenders;

    @NotNull
    public AttackAbility ability;
}
