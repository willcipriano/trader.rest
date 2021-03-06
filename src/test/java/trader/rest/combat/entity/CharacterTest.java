package trader.rest.combat.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import trader.rest.combat.exception.CharacterInitException;
import trader.rest.combat.exception.ValidationException;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


import java.util.*;


@SpringBootTest
class CharacterTest {

    //test options
    double timeLimit = .05;

    ArmorItem createTestArmor(ArmorTypeEnum type, ArmorSlotEnum slot, int bonus) {
        return ArmorItem.builder()
                .type(type)
                .slot(slot)
                .armorBonus(bonus)
                .build();
    }

    Armor createTestArmor(ArmorTypeEnum type) {
        List<ArmorItem> armorItems = new ArrayList<>();
        int bonus;

        switch (type) {
            case LIGHT:
                bonus = 2;
                break;

            case MEDIUM:
                bonus = 3;
                break;

            case HEAVY:
                bonus = 4;
                break;

            default:
                bonus = 1;
        }

        for (ArmorSlotEnum slot : ArmorSlotEnum.values()) {
            armorItems.add(createTestArmor(type, slot, bonus));
        }

        Armor armor = new Armor();

        for (ArmorItem armorItem : armorItems) {


            switch (armorItem.slot) {

                case BODY:
                    armor.setBody(armorItem);
                    break;

                case FEET:
                    armor.setFeet(armorItem);
                    break;

                case HANDS:
                    armor.setHands(armorItem);
                    break;

                case HEAD:
                    armor.setHead(armorItem);
                    break;

                default:
                    break;
            }

        }
        return armor;


    }

    Character createTestCharacter(int level, int statLevel, String name, ArmorTypeEnum armorType) throws CharacterInitException, ValidationException {
        CharacterSheet characterSheet = new CharacterSheet();
        characterSheet.level = level;
        characterSheet.maxCharisma = statLevel;
        characterSheet.maxConstitution = statLevel;
        characterSheet.maxDexterity = statLevel;
        characterSheet.maxIntelligence = statLevel;
        characterSheet.maxStrength = statLevel;
        characterSheet.maxWisdom = statLevel;
        characterSheet.name = name;

        Armor armor = createTestArmor(armorType);

        CharacterInventory characterInventory = new CharacterInventory();
        characterInventory.armor = armor;

        CharacterStatus characterStatus = new CharacterStatus(statLevel, statLevel, statLevel, statLevel, statLevel, statLevel, statLevel);




        return Character.builder()
                .name(name)
                .sheet(characterSheet)
                .inventory(characterInventory)
                .status(characterStatus)
                .calculateDerivedStats()
                .build();
    }

    @Test
    void characterSmokeTest() throws ValidationException, CharacterInitException {
        //character stats
        List<Integer> statLevels = Arrays.asList(3, 5, 15, 30);
        List<Integer> charLevels = Arrays.asList(1, 5, 9, 11);
        String name = "Steven";

        for (ArmorTypeEnum armorType : ArmorTypeEnum.values()) {
            for (Integer statLevel : statLevels) {
                for (Integer level : charLevels) {
                    // Character creation time is critical to overall
                    // combat simulation performance.
                    long startTime = System.nanoTime();
                    Character testChar = createTestCharacter(level, statLevel, name, armorType);
                    long endTime = System.nanoTime();

                    assertTrue((double) (endTime - startTime) / 1000000000 < timeLimit,
                            "Ran out of time for character rehydration, took: " + (double) (endTime - startTime) / 1000000000  + " seconds, have " + timeLimit + " seconds.");

                    // Validation occurs on the CharacterBuilder that should
                    // guarantee a valid object, however this call is recursive
                    // and ensures that the child object validations also pass.
                    Utils.validate(testChar);

                    // basic smoke testing
                    assertEquals(level, testChar.sheet.level);
                    assertEquals(statLevel, testChar.status.currentStrength);
                    assertEquals(statLevel, testChar.status.currentIntelligence);
                    assertEquals(statLevel, testChar.status.currentWisdom);
                    assertEquals(statLevel, testChar.status.currentDexterity);
                    assertEquals(statLevel, testChar.status.currentConstitution);
                    assertEquals(statLevel, testChar.status.currentCharisma);
                    assertEquals(name, testChar.name);
                    assertEquals(armorType, testChar.inventory.armor.items.get(ArmorSlotEnum.BODY).type);
                    assertEquals(armorType, testChar.inventory.armor.items.get(ArmorSlotEnum.HEAD).type);
                    assertEquals(armorType, testChar.inventory.armor.items.get(ArmorSlotEnum.HANDS).type);
                    assertEquals(armorType, testChar.inventory.armor.items.get(ArmorSlotEnum.FEET).type);

                    // Armor Class calculation testing
                    if (armorType == ArmorTypeEnum.LIGHT) {
                        assertEquals((8 + testChar.abilityModifier.dexterity), testChar.armorStats.armorClass);
                    }
                    if (armorType == ArmorTypeEnum.MEDIUM) {
                        assertEquals((12 + testChar.abilityModifier.dexterity), testChar.armorStats.armorClass);
                    }
                    if (armorType == ArmorTypeEnum.HEAVY) {
                        assertEquals((16 + testChar.abilityModifier.dexterity), testChar.armorStats.armorClass);
                    }

                    // Ability Modifier calculation testing
                    if (statLevel == 3) {
                        assertEquals(-5, testChar.abilityModifier.charisma);
                        assertEquals(-5, testChar.abilityModifier.constitution);
                        assertEquals(-5, testChar.abilityModifier.dexterity);
                        assertEquals(-5, testChar.abilityModifier.intelligence);
                        assertEquals(-5, testChar.abilityModifier.strength);
                        assertEquals(-5, testChar.abilityModifier.wisdom);
                    }

                    if (statLevel == 5) {
                        assertEquals(-4, testChar.abilityModifier.charisma);
                        assertEquals(-4, testChar.abilityModifier.constitution);
                        assertEquals(-4, testChar.abilityModifier.dexterity);
                        assertEquals(-4, testChar.abilityModifier.intelligence);
                        assertEquals(-4, testChar.abilityModifier.strength);
                        assertEquals(-4, testChar.abilityModifier.wisdom);
                    }

                    if (statLevel == 15) {
                        assertEquals(2, testChar.abilityModifier.charisma);
                        assertEquals(2, testChar.abilityModifier.constitution);
                        assertEquals(2, testChar.abilityModifier.dexterity);
                        assertEquals(2, testChar.abilityModifier.intelligence);
                        assertEquals(2, testChar.abilityModifier.strength);
                        assertEquals(2, testChar.abilityModifier.wisdom);
                    }

                    if (statLevel == 30) {
                        assertEquals(10, testChar.abilityModifier.charisma);
                        assertEquals(10, testChar.abilityModifier.constitution);
                        assertEquals(10, testChar.abilityModifier.dexterity);
                        assertEquals(10, testChar.abilityModifier.intelligence);
                        assertEquals(10, testChar.abilityModifier.strength);
                        assertEquals(10, testChar.abilityModifier.wisdom);
                    }

                }
            }
        }
    }
}
