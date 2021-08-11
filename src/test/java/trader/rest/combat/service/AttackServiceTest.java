package trader.rest.combat.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import trader.rest.combat.entity.*;
import trader.rest.combat.entity.Character;
import trader.rest.combat.exception.AbiltyBonusException;
import trader.rest.combat.exception.CharacterInitException;
import trader.rest.combat.exception.ValidationException;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AttackServiceTest {

    @Autowired
    AttackService attackService;

    private final static CharacterEffects noFx = CharacterEffects.builder().build();

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

    Character createTestCharacter(int level, int statLevel, String name, CharacterEffects effects) throws CharacterInitException, ValidationException {
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
                .effects(effects)
                .calculateDerivedStats()
                .validate()
                .build();
    }

    @Test
    public void testAttackCalculation() throws CharacterInitException, ValidationException, AbiltyBonusException {
        Character attacker = createTestCharacter(1, 1, "attacker", noFx);
        Character defender = createTestCharacter(1, 1, "defender", noFx);
        AttackAbility ability = createTestAbility(1,1,1,1,10,5,1);

        AttackRequest attackRequest = AttackRequest.builder()
                .belligerents(Collections.singletonList(attacker))
                .defenders(Collections.singletonList(defender))
                .ability(ability)
                .build();

        AttackResult result = AttackService.render(attackRequest);

        assertEquals(1, result.getCombatResults().size());
        assertEquals((double) 10, result.getCombatResults().get(0).getDefHPChange());
    }

    @Test
    public void testMultiTargetAttackCalculation() throws CharacterInitException, ValidationException, AbiltyBonusException{
        Character attacker = createTestCharacter(1, 1, "attacker", noFx);
        Character defender1 = createTestCharacter(1, 1, "defender1", noFx);
        Character defender2 = createTestCharacter(1, 1, "defender2", noFx);
        Character defender3 = createTestCharacter(1, 1, "defender3", noFx);
        AttackAbility ability = createTestAbility(1,1,1,1,10,5,2);

        AttackRequest attackRequest = AttackRequest.builder()
                .belligerents(Collections.singletonList(attacker))
                .defenders(Arrays.asList(defender1, defender2, defender3))
                .ability(ability)
                .build();

        AttackResult result = AttackService.render(attackRequest);

        assertEquals(2, result.getCombatResults().size());
        assertEquals((double) 10, result.getCombatResults().get(0).getDefHPChange());
        assertEquals((double) 10, result.getCombatResults().get(1).getDefHPChange());
    }

    @Test
    public void testOnAttackEffects() throws CharacterInitException, ValidationException, AbiltyBonusException{
        Effect attackEffect = Effect.builder()
                .effectTurns(5)
                .name("Poison Coated")
                .selfStatModifier(Map.of(StatTypeEnum.HP, 1))
                .targetStatModifier(Collections.emptyMap())
                .build();

        EffectStatus attackEffectStatus = EffectStatus.builder()
                .turnsRemaining(5)
                .build();

        CharacterEffects effects = CharacterEffects.builder()
                .effects(Map.of(attackEffect, attackEffectStatus))
                .build();

        Character attacker = createTestCharacter(1, 1, "attacker", effects);
        Character defender = createTestCharacter(1, 1, "defender", noFx);
        AttackAbility ability = createTestAbility(1,1,1,1,10,5,1);

        AttackRequest attackRequest = AttackRequest.builder()
                .belligerents(Collections.singletonList(attacker))
                .defenders(Arrays.asList(defender))
                .ability(ability)
                .build();

        AttackResult result = AttackService.render(attackRequest);

        EffectResult effectResult = result.getCombatResults().get(0).getEffectResults().get(0);

        assertEquals("Poison Coated", effectResult.getEffectName());
        assertEquals(1, effectResult.getSelfStatModifier().get(StatTypeEnum.HP));
        assertEquals(5, effectResult.getEffectTurnsRemaining());

        AttackResult result2 = AttackService.render(attackRequest);
        EffectResult effectResult2 = result2.getCombatResults().get(0).getEffectResults().get(0);

        assertEquals(4, effectResult2.getEffectTurnsRemaining());
    }


}
