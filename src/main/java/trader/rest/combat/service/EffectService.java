package trader.rest.combat.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import trader.rest.combat.entity.*;
import trader.rest.combat.entity.Character;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EffectService {


    private CombatEffectResult renderAttackStartEffect(AttackEffect effect, EffectStatus status, Character belligerent) {
        CombatEffectResult result;
        if (status.isValid()) {
            result = CombatEffectResult.builder()
                    .atkDmgModifier(effect.getAtkDmgModifier())
                    .atkHitModifier(effect.getAtkHitModifier())
                    .defDmgModifier(effect.getDefDmgModifier())
                    .defHitModifier(effect.getAtkHitModifier())
                    .effectName(effect.getName())
                    .build();
            status.increment();
        } else {
            belligerent.expireAttackEffect(effect);
            return null;
        }
        return result;
    }


    private CombatEffectResult renderAttackEndEffect(AttackEffect effect, EffectStatus status, Character belligerent) {
        return CombatEffectResult.builder().build();
    }

    public CombatEffectResult renderAttackingEffect(AttackEffect effect, EffectStatus status, Character belligerent) {
        switch (effect.getTiming()) {

            case ATTACK_START:
                return renderAttackStartEffect(effect, status, belligerent);

            case ATTACK_END:
                return renderAttackEndEffect(effect, status, belligerent);
        }

        return renderAttackStartEffect(effect, status, belligerent);
    }

    public List<CombatEffectResult> renderAttackStartEffects(Character belligerent) {
        List<CombatEffectResult> results = new ArrayList<>();
        Map<AttackEffect, EffectStatus> statusMap = belligerent.getAttackEffects();

        if (statusMap.size() == 0) {
            return results;
        }

        if (belligerent.isValidAttacker()) {

        for (AttackEffect attackEffect : belligerent.getAttackEffects().keySet()) {
            if (attackEffect.getTiming().equals(EffectTimingEnum.ATTACK_START)) {
                results.add(renderAttackingEffect(attackEffect, statusMap.get(attackEffect), belligerent));
            }
        }
        }

        return results;

    }



}
