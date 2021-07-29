package trader.rest.combat.service;

import org.junit.jupiter.api.Test;
import trader.rest.combat.entity.*;
import trader.rest.combat.entity.Character;
import trader.rest.combat.service.AttackService;
import trader.rest.combat.exception.CharacterInitException;
import trader.rest.combat.exception.ValidationException;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

public class AttackServiceTest {

    AttackAbility createTestAbility(double strAtkBonus, double dexAtkBonus, double conDefBonus, double dexDefBonus, int baseDmg, int baseHit, int maxTargets) {
        return AttackAbility.builder()
                .abilityModifierDmgAtk(Map.of(StatTypeEnum.STR, strAtkBonus))
                .abilityModifierHitAtk(Map.of(StatTypeEnum.DEX, dexAtkBonus))
                .abilityModifierDmgDef(Map.of(StatTypeEnum.CON, conDefBonus))
                .abilityModifierHitDef(Map.of(StatTypeEnum.DEX, dexDefBonus))
                .baseDmg(baseDmg)
                .baseHit(baseHit)
                .maxTargets(maxTargets)
                .build();
    }

    ArmorItem createTestArmor(ArmorTypeEnum type, ArmorSlotEnum slot, int bonus) {
        return ArmorItem.builder()
                .type(type)
                .slot(slot)
                .armorBonus(bonus)
                .build();
    }

    Character createTestCharacter(int level, int statLevel, String name) throws CharacterInitException, ValidationException {
        Armor armor = new Armor();
        armor.setHead(createTestArmor(ArmorTypeEnum.LIGHT, ArmorSlotEnum.HEAD, 1));
        armor.setBody(createTestArmor(ArmorTypeEnum.LIGHT, ArmorSlotEnum.BODY, 2));
        armor.setHands(createTestArmor(ArmorTypeEnum.LIGHT, ArmorSlotEnum.HANDS, 1));
        armor.setFeet(createTestArmor(ArmorTypeEnum.LIGHT, ArmorSlotEnum.HANDS, 1));

        return Character.builder()
                .level(level)
                .strength(statLevel)
                .intelligence(statLevel)
                .wisdom(statLevel)
                .dexterity(statLevel)
                .constitution(statLevel)
                .charisma(statLevel)
                .name(name)
                .armor(armor)
                .validate()
                .calculateDerivedStats()
                .build();
    }

    @Test
    public void testAttackCalculation() throws CharacterInitException, ValidationException{
        Character attacker = createTestCharacter(2, 2, "attacker");
        Character defender = createTestCharacter(1, 1, "defender");
        AttackAbility ability = createTestAbility(1,1,1,1,1,1,1);

        AttackRequest attackRequest = AttackRequest.builder()
                .belligerents(Collections.singletonList(attacker))
                .defenders(Collections.singletonList(defender))
                .ability(ability)
                .build();

        AttackResult result = AttackService.render(attackRequest);

        assertEquals(1, result.getCombatResults().size());
        assertEquals((double) 1, result.getCombatResults().get(0).getHpChange());
    }

    @Test
    public void testMultiTargetAttackCalculation() throws CharacterInitException, ValidationException{
        Character attacker = createTestCharacter(2, 2, "attacker");
        Character defender1 = createTestCharacter(1, 1, "defender1");
        Character defender2 = createTestCharacter(1, 1, "defender2");
        Character defender3 = createTestCharacter(1, 1, "defender3");
        AttackAbility ability = createTestAbility(1,1,1,1,1,1,2);

        AttackRequest attackRequest = AttackRequest.builder()
                .belligerents(Collections.singletonList(attacker))
                .defenders(Arrays.asList(defender1, defender2, defender3))
                .ability(ability)
                .build();

        AttackResult result = AttackService.render(attackRequest);

        assertEquals(2, result.getCombatResults().size());
        assertEquals((double) 1, result.getCombatResults().get(0).getHpChange());
        assertEquals((double) 1, result.getCombatResults().get(1).getHpChange());
    }


}
