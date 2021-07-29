package trader.rest.combat.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import trader.rest.combat.entity.*;
import trader.rest.combat.entity.Character;
import trader.rest.combat.exception.AbiltyBonusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class AttackService {

    private static CombatResult calculateAttackInstance(AttackRequest attackRequest, Character belligerent, Character defender)  {

        List<Double> atkDmgMultiList = attackRequest.ability.getAbilityModifierDmgAtk().entrySet().stream().map(e-> {
            try {
                return belligerent.getAbilityBonusModifier(e.getKey()) * e.getValue();
            } catch (AbiltyBonusException abiltyBonusException) {
                log.warn("Failed to calculate ability modifier for {}.", belligerent.getName(), abiltyBonusException);
                return (double) 0;
            }
        }).collect(Collectors.toList());
        Double atkDmg = atkDmgMultiList.stream().reduce((double) 0, Double::sum);

        List<Double> atkHitMultiList = attackRequest.ability.getAbilityModifierHitAtk().entrySet().stream().map(e-> {
            try {
                return belligerent.getAbilityBonusModifier(e.getKey()) * e.getValue();
            } catch (AbiltyBonusException abiltyBonusException) {
                log.warn("Failed to calculate ability modifier for {}.", belligerent.getName(), abiltyBonusException);
                return (double) 0;
            }
        }).collect(Collectors.toList());
        Double atkHit = atkHitMultiList.stream().reduce((double) 0, Double::sum);

        List<Double> defDmgMultiList = attackRequest.ability.getAbilityModifierDmgDef().entrySet().stream().map(e-> {
            try {
                return defender.getAbilityBonusModifier(e.getKey()) * e.getValue();
            } catch (AbiltyBonusException abiltyBonusException) {
                log.warn("Failed to calculate ability modifier for {}.", belligerent.getName(), abiltyBonusException);
                return (double) 0;
            }
        }).collect(Collectors.toList());
        Double defDmg = defDmgMultiList.stream().reduce((double) 0, Double::sum);

        List<Double> defHitMultiList = attackRequest.ability.getAbilityModifierHitDef().entrySet().stream().map(e-> {
            try {
                return defender.getAbilityBonusModifier(e.getKey()) * e.getValue();
            } catch (AbiltyBonusException abiltyBonusException) {
                log.warn("Failed to calculate ability modifier for {}.", belligerent.getName(), abiltyBonusException);
                return (double) 0;
            }
        }).collect(Collectors.toList());
        Double defHit = defHitMultiList.stream().reduce((double) 0, Double::sum);

        if (atkHit > defHit) {
            if (atkDmg > defDmg) {
                return CombatResult.builder()
                        .belligerent(belligerent)
                        .defender(defender)
                        .hpChange((int) (atkDmg - defDmg))
                        .combatFailureReasons(Collections.singletonList(CombatFailureEnum.SUCCESS))
                        .build();
            }
        }

        List<CombatFailureEnum> failureReasons = new ArrayList<>();

        if (atkHit < defHit) {
            failureReasons.add(CombatFailureEnum.HIT);
        }

        if (atkDmg < defDmg) {
            failureReasons.add(CombatFailureEnum.DMG);
        }

        if (atkHit.equals(defHit)) {
            failureReasons.add(CombatFailureEnum.EQUAL_HIT);
        }

        if (atkDmg.equals(defDmg)) {
            failureReasons.add(CombatFailureEnum.EQUAL_DMG);
        }

        return CombatResult.builder()
                .belligerent(belligerent)
                .defender(defender)
                .combatFailureReasons(failureReasons)
                .hpChange(0)
                .build();
    }


    public static AttackResult render(AttackRequest attackRequest) {
        List<CombatResult> combatResultList = new ArrayList<>();
        int curDefender = 0;
        int defenderSize = attackRequest.defenders.size();

        for (Character belligerent: attackRequest.belligerents) {
            int maxTargets = attackRequest.ability.getMaxTargets();
            int hitsTotal = 0;
            int startingDefender = curDefender + 1;

            while (hitsTotal < maxTargets) {
                curDefender += 1;

                if (curDefender > defenderSize - 1) {
                    curDefender = 0;
                }

                if (curDefender == startingDefender && hitsTotal != 0) {
                    continue;
                }

                combatResultList.add(calculateAttackInstance(attackRequest, belligerent, attackRequest.defenders.get(curDefender)));
                hitsTotal += 1;
            }
        }

        return AttackResult.builder()
                .combatResults(combatResultList)
                .build();
    }





}
