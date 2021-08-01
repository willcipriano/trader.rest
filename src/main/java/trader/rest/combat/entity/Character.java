package trader.rest.combat.entity;

import lombok.*;
import trader.rest.combat.exception.AbiltyBonusException;
import trader.rest.combat.exception.CharacterInitException;
import trader.rest.combat.exception.ValidationException;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class Character {
    @NotNull
    @Size(max = 75, min = 1)
    String name;

    @Min(1)
    @Max(999)
    int strength;

    @Min(1)
    @Max(999)
    int intelligence;

    @Min(1)
    @Max(999)
    int wisdom;

    @Min(1)
    @Max(999)
    int dexterity;

    @Min(1)
    @Max(999)
    int constitution;

    @Min(1)
    @Max(999)
    int charisma;

    @Min(1)
    @Max(999)
    int level;

    @NotNull
    Armor armor;

    @NotNull
    ArmorStats armorStats;

    @NotNull
    AbilityModifier abilityModifier;

    @NotNull
    CharacterEffects effects;

    @NotNull
    BodyStatistics body;

    public static class CharacterBuilder {
        /*
         *  this allows for validation in the builder itself, preventing
         *  out of spec characters from being built.
        */

        @NotNull
        @Size(max = 75, min = 1)
        String name;

        @Min(1)
        @Max(999)
        int strength;

        @Min(1)
        @Max(999)
        int intelligence;

        @Min(1)
        @Max(999)
        int wisdom;

        @Min(1)
        @Max(999)
        int dexterity;

        @Min(1)
        @Max(999)
        int constitution;

        @Min(1)
        @Max(999)
        int charisma;

        @Min(1)
        @Max(999)
        int level;

        @NotNull
        AbilityModifier abilityModifier;

    public CharacterBuilder calculateDerivedStats() throws CharacterInitException {
        this.abilityModifier = AbilityModifier.builder()
                .define(this.strength, this.intelligence, this.wisdom, this.dexterity, this.constitution, this.charisma)
                .build();

        this.armorStats = ArmorStats.builder()
                .define(this.armor.getArmorItemList(), this.abilityModifier.dexterity)
                .build();

        this.body = BodyStatistics.builder()
                .calculateStartingHitPointsFromCharacterBuilder(this)
                .build();
        return this;
    }

    public CharacterBuilder validate() throws ValidationException {
        Utils.validate(this);
        return this;
    }

    public Character calculateAndBuild() throws CharacterInitException, ValidationException {
        return this.calculateDerivedStats().validate().build();
    }

    }

    public Double getAbilityBonusModifier(StatTypeEnum type) throws AbiltyBonusException {
        switch (type) {

            case STR:
                return (double) this.abilityModifier.getStrength();

            case INT:
                return (double) this.abilityModifier.getIntelligence();

            case WIZ:
                return (double) this.abilityModifier.getWisdom();

            case CON:
                return (double) this.abilityModifier.getConstitution();

            case DEX:
                return (double) this.abilityModifier.getDexterity();

            case CHA:
                return (double) this.abilityModifier.getCharisma();

            default:
                throw new AbiltyBonusException();
        }

    }
    /*
     *  Damage and HP related functions, as well as alive or
     *  unconscious status.
     */
    public void applyDamage(int hp) {
        this.body.applyDamage(hp);
    }

    public void applyHealing(int hp) {
        this.body.applyHealing(hp);
    }

    public Boolean isValidDefender() { return this.body.isValidDefender(); }

    public Boolean isValidAttacker() { return this.body.isValidAttacker(); }


    /*
     *  AttackEffect stuff.
     */
    public Map<AttackEffect, EffectStatus> getAttackEffects() { return this.effects.getAttackEffects(); }

    public void expireAttackEffect(AttackEffect effect) {
        this.effects.getAttackEffects().remove(effect);
    }
}
