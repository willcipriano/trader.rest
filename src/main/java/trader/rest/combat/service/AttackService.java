package trader.rest.combat.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trader.rest.combat.entity.*;
import trader.rest.combat.entity.Character;
import trader.rest.combat.exception.AbiltyBonusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class AttackService {
    private static EffectService effectService;

    @Autowired
    public AttackService(EffectService effectService) {
        AttackService.effectService = effectService;
    }

    private static CombatResult calculateAttackInstance(AttackRequest attackRequest, Character belligerent, Character defender) {
        CombatResult result = CombatResult.builder()
                .belligerent(belligerent)
                .defender(defender)
                .build();

        List<EffectResult> effectResults = effectService.calculateAndApplyOnAttackEffects(belligerent, defender);

        List<Double> atkDmgMultiList = attackRequest.ability.getAbilityModifierDmgAtk().entrySet().stream().map(e-> {
            try {
                if (belligerent.getAbilityBonusModifier(e.getKey()) < 0) {
                    return belligerent.getAbilityBonusModifier(e.getKey()) / e.getValue();
                } else {
                return belligerent.getAbilityBonusModifier(e.getKey()) * e.getValue();}
            } catch (AbiltyBonusException abiltyBonusException) {
                log.warn("Failed to calculate ability modifier for {}.", belligerent.getName(), abiltyBonusException);
                return (double) 0;
            }
        }).collect(Collectors.toList());
        Double atkDmg = atkDmgMultiList.stream().reduce((double) 0, Double::sum);
        atkDmg += attackRequest.getAbility().getBaseDmg();

        List<Double> atkHitMultiList = attackRequest.ability.getAbilityModifierHitAtk().entrySet().stream().map(e-> {
            try {
                if (belligerent.getAbilityBonusModifier(e.getKey()) < 0) {
                    return belligerent.getAbilityBonusModifier(e.getKey()) / e.getValue();
                } else {
                    return belligerent.getAbilityBonusModifier(e.getKey()) * e.getValue();}
            } catch (AbiltyBonusException abiltyBonusException) {
                log.warn("Failed to calculate ability modifier for {}.", belligerent.getName(), abiltyBonusException);
                return (double) 0;
            }
        }).collect(Collectors.toList());
        Double atkHit = atkHitMultiList.stream().reduce((double) 0, Double::sum);
        atkHit += attackRequest.ability.getBaseHit();

        List<Double> defDmgMultiList = attackRequest.ability.getAbilityModifierDmgDef().entrySet().stream().map(e-> {
            try {
                if (defender.getAbilityBonusModifier(e.getKey()) < 0) {
                    return defender.getAbilityBonusModifier(e.getKey()) / e.getValue();
                } else {
                    return defender.getAbilityBonusModifier(e.getKey()) * e.getValue();}
            } catch (AbiltyBonusException abiltyBonusException) {
                log.warn("Failed to calculate ability modifier for {}.", defender.getName(), abiltyBonusException);
                return (double) 0;
            }
        }).collect(Collectors.toList());
        Double defDmg = defDmgMultiList.stream().reduce((double) 0, Double::sum);

        List<Double> defHitMultiList = attackRequest.ability.getAbilityModifierHitDef().entrySet().stream().map(e-> {
            try {
                if (defender.getAbilityBonusModifier(e.getKey()) < 0) {
                    return defender.getAbilityBonusModifier(e.getKey()) / e.getValue();
                } else {
                    return defender.getAbilityBonusModifier(e.getKey()) * e.getValue();}
            } catch (AbiltyBonusException abiltyBonusException) {
                log.warn("Failed to calculate ability modifier for {}.", defender.getName(), abiltyBonusException);
                return (double) 0;
            }
        }).collect(Collectors.toList());
        Double defHit = defHitMultiList.stream().reduce((double) 0, Double::sum);
        defHit += defender.getArmorStats().getArmorClass();

        List<Double> postEffectCalculatedValues = effectService.calculatePostEffectAttackValues(atkDmg, atkHit, defDmg, defHit, effectResults);

        atkDmg = postEffectCalculatedValues.get(0);
        atkHit = postEffectCalculatedValues.get(1);
        defDmg = postEffectCalculatedValues.get(2);
        defHit = postEffectCalculatedValues.get(3);
        result.setEffectResults(effectResults);

        log.debug("Combat between {} and {} starts: AtkDmg {}, AtkHit {}, DefDmg {}, DefHit {}.", belligerent.getName(), defender.getName(), atkDmg, atkHit, defDmg, defHit);

        if (atkHit > defHit) {
            if (atkDmg > defDmg) {
                int change = (int) (atkDmg - defDmg);
                log.debug("Combat between {} and {} calculated, {} damage results.", belligerent.getName(), defender.getName(), change);
                result.setNewStatChange(HostilityModeEnum.DEFENDER, StatTypeEnum.HP, change);
                return result.signOffAndSend();
            }
        }

        List<CombatFailureEnum> failureReasons = new ArrayList<>();

        if (atkHit < defHit) {
            failureReasons.add(CombatFailureEnum.HIT);
            log.debug("The defenders speed and armor was greater than the attackers aim.");
        }

        if (atkDmg < defDmg) {
            failureReasons.add(CombatFailureEnum.DMG);
            log.debug("The defenders defence was greater than the attackers damage.");
        }

        if (atkHit.equals(defHit)) {
            failureReasons.add(CombatFailureEnum.EQUAL_HIT);
            log.debug("So evenly matched in terms of hit that they are unable to hit each other.");
        }

        if (atkDmg.equals(defDmg)) {
            failureReasons.add(CombatFailureEnum.EQUAL_DMG);
            log.debug("So evenly matched in terms of damage and damage reduction that they are unable to damage each other.");
        }

        log.debug("Combat between {} and {} calculated, no damage results.", belligerent.getName(), defender.getName());

        result.setCombatFailureReasons(failureReasons);

        return result;
    }


    public static AttackResult render(AttackRequest attackRequest) throws AbiltyBonusException {
        log.debug("Starting attack simulation. Belligerents: '{}' Defenders: '{}'", attackRequest.belligerents.stream().map(Character::getName).collect(Collectors.toList()), attackRequest.defenders.stream().map(Character::getName).collect(Collectors.toList()));
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

                if (!belligerent.isValidAttacker()) {
                    log.debug("{} is not a valid attacker.", belligerent.getName());
                    combatResultList.add(CombatResult.builder()
                            .quickReport(belligerent, attackRequest.defenders.get(curDefender), CombatFailureEnum.INELIGIBLE_ATTACKER));
                    break;
                }

                Character defender = attackRequest.defenders.get(curDefender);

                if (defender.isValidDefender()) {
                    combatResultList.add(calculateAttackInstance(attackRequest, belligerent, defender));
                } else {
                    log.debug("{} is not a valid defender.", defender.getName());
                    attackRequest.defenders.remove(defender);
                    defenderSize = attackRequest.defenders.size();
                    combatResultList.add(CombatResult.builder()
                            .quickReport(belligerent, defender, CombatFailureEnum.INELIGIBLE_DEFENDER));
                    continue;
                }

                hitsTotal += 1;
            }
        }

        return AttackResult.builder()
                .combatResults(combatResultList)
                .build();
    }





}
