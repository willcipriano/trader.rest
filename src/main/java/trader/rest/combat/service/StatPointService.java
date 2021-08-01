package trader.rest.combat.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import trader.rest.combat.entity.CombatResult;
import trader.rest.combat.entity.Character;
import trader.rest.combat.exception.DoubleCombatResultApplicationException;

@Service
@Slf4j
public class StatPointService {

    private void renderHpChange(Character character, int change) {
        if (change != 0) {
            if (change > 0) {
                character.applyDamage(change);
            } else {
                character.applyHealing(change);
            }
        }
    }

    private void renderCombatResult(CombatResult combatResult, Character belligerent, Character defender) throws DoubleCombatResultApplicationException {
        renderHpChange(belligerent, combatResult.getAtkHPChange());
        renderHpChange(defender, combatResult.getDefHPChange());
        combatResult.setChangesApplied();
    }

    public CombatResult processCombatResultChanges(CombatResult combatResult, Character belligerent, Character defender) throws DoubleCombatResultApplicationException {
        renderCombatResult(combatResult, belligerent, defender);
        return combatResult;
    }


}
