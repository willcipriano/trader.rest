package trader.rest.combat.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import trader.rest.combat.entity.*;
import trader.rest.combat.entity.Character;
import trader.rest.combat.exception.CharacterInitException;
import trader.rest.combat.exception.ValidationException;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class EffectServiceTest {

    @Autowired
    EffectService effectService;

    private final static CharacterEffects noFx = CharacterEffects.builder().build();

    CharacterEffects createCharacterEffect() {
        return CharacterEffects.builder()
                .effects(Map.of(
                        Effect.builder()
                                .effectTurns(3)
                                .selfStatModifier(
                                        Map.of(StatTypeEnum.HP, -1))
                                .targetStatModifier(Collections.emptyMap())
                                .name("Test")
                                .build()
                        , EffectStatus.builder()
                                .turnsRemaining(2).build())).build();
    }

    ArmorItem createTestArmor(ArmorTypeEnum type, ArmorSlotEnum slot, int bonus) {
        return ArmorItem.builder()
                .type(type)
                .slot(slot)
                .armorBonus(bonus)
                .build();
    }

    trader.rest.combat.entity.Character createTestCharacter(int level, int statLevel, String name, CharacterEffects effects) throws CharacterInitException, ValidationException {
        Armor armor = new Armor();
        armor.setHead(createTestArmor(ArmorTypeEnum.LIGHT, ArmorSlotEnum.HEAD, 1));
        armor.setBody(createTestArmor(ArmorTypeEnum.LIGHT, ArmorSlotEnum.BODY, 2));
        armor.setHands(createTestArmor(ArmorTypeEnum.LIGHT, ArmorSlotEnum.HANDS, 1));
        armor.setFeet(createTestArmor(ArmorTypeEnum.LIGHT, ArmorSlotEnum.HANDS, 1));

        return Character.builder()
                .build();
    }

    @Test
    public void testCalculateAndApplyOnAttackEffects() throws CharacterInitException, ValidationException {
        CharacterEffects testEffect = createCharacterEffect();

        Character attacker = createTestCharacter(1, 1, "attacker", testEffect);
        Character defender = createTestCharacter(1, 1, "defender", noFx);

        int currentAttackerHp = attacker.getCurrentHp();

        effectService.calculateAndApplyOnAttackEffects(attacker, defender);

        assertEquals(currentAttackerHp - 1, attacker.getCurrentHp());
    }
}
