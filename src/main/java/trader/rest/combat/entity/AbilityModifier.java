package trader.rest.combat.entity;

import lombok.*;
import trader.rest.combat.exception.CharacterDexAcBonusCalculationException;
import trader.rest.combat.exception.CharacterInitException;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@Builder
public class AbilityModifier {
    @Min(-6)
    @Max(10)
    int strength;

    @Min(-6)
    @Max(10)
    int intelligence;

    @Min(-6)
    @Max(10)
    int wisdom;

    @Min(-6)
    @Max(10)
    int dexterity;

    @Min(-6)
    @Max(10)
    int constitution;

    @Min(-6)
    @Max(10)
    int charisma;

    public static class AbilityModifierBuilder {
        private int strength;
        private int intelligence;
        private int wisdom;
        private int dexterity;
        private int constitution;
        private int charisma;

        private int calculateAbilityModifier(int score) throws CharacterDexAcBonusCalculationException {
            switch (score) {
                case 0:
                    return -7;

                case 1:
                    return -6;

                case 2:
                case 3:
                    return -5;

                case 4:
                case 5:
                    return -4;

                case 6:
                case 7:
                    return -3;

                case 8:
                case 9:
                    return -2;

                case 10:
                case 11:
                    return -1;

                case 12:
                    return 0;

                case 13:
                case 14:
                    return 1;

                case 15:
                case 16:
                    return 2;

                case 17:
                case 18:
                    return 3;

                case 19:
                case 20:
                    return 4;

                case 21:
                case 22:
                    return 5;

                case 23:
                case 24:
                    return 6;

                case 25:
                case 26:
                    return 7;

                case 27:
                case 28:
                    return 8;

                case 29:
                    return 9;

                case 30:
                    return 10;

                case 31:
                    return 11;

                default:
                    if (score <= -1) {
                        return -8;
                    }
                    return 12;
            }
        }

        public AbilityModifierBuilder strength(int strength) throws CharacterInitException {
            this.strength = calculateAbilityModifier(strength);
            return this;
        }

        public AbilityModifierBuilder intelligence(int intelligence) throws CharacterInitException {
            this.intelligence = calculateAbilityModifier(intelligence);
            return this;
        }

        public AbilityModifierBuilder wisdom(int wisdom) throws CharacterInitException {
            this.wisdom = calculateAbilityModifier(wisdom);
            return this;
        }

        public AbilityModifierBuilder dexterity(int dexterity) throws CharacterInitException {
            this.dexterity = calculateAbilityModifier(dexterity);
            return this;
        }

        public AbilityModifierBuilder constitution(int constitution) throws CharacterInitException {
            this.constitution = calculateAbilityModifier(constitution);
            return this;
        }

        public AbilityModifierBuilder charisma(int charisma) throws CharacterInitException {
            this.charisma = calculateAbilityModifier(charisma);
            return this;
        }

        public AbilityModifierBuilder define(int strength, int intelligence, int wisdom, int dexterity, int constitution, int charisma) throws CharacterInitException{
            this.strength = calculateAbilityModifier(strength);
            this.intelligence = calculateAbilityModifier(intelligence);
            this.wisdom = calculateAbilityModifier(wisdom);
            this.dexterity = calculateAbilityModifier(dexterity);
            this.constitution = calculateAbilityModifier(constitution);
            this.charisma = calculateAbilityModifier(charisma);
            return this;
        }
    } }

