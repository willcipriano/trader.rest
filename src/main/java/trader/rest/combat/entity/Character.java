package trader.rest.combat.entity;

import lombok.*;
import trader.rest.combat.exception.AbiltyBonusException;
import trader.rest.combat.exception.CharacterInitException;
import trader.rest.combat.exception.ValidationException;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
public class Character {
    @NotNull
    @Size(max = 75, min = 1)
    String name;

    @NotNull
    CharacterSheet sheet;

    @NotNull
    CharacterStatus status;

    @NotNull
    CharacterInventory inventory;

    @NotNull
    ArmorStats armorStats;

    @NotNull
    AbilityModifier abilityModifier;

    @NotNull
    BodyStatistics body;

    public static class CharacterBuilder {
        @NotNull
        @Size(max = 75, min = 1)
        String name;

        @NotNull
        CharacterSheet sheet;

        @NotNull
        CharacterStatus status;

        @NotNull
        CharacterInventory inventory;

        @NotNull
        ArmorStats armorStats;

        @NotNull
        AbilityModifier abilityModifier;

    public CharacterBuilder calculateDerivedStats() throws CharacterInitException {
        this.abilityModifier = AbilityModifier.builder()
                .define(this.status.currentStrength, this.status.currentIntelligence, this.status.currentWisdom, this.status.currentDexterity, this.status.currentConstitution, this.status.currentCharisma)
                .build();

        this.armorStats = ArmorStats.builder()
                .define(this.inventory.armor.getArmorItemList(), this.abilityModifier.dexterity)
                .build();

        this.body = BodyStatistics.builder()
                .calculateStartingHitPointsFromCharacterBuilder(this)
                .build();

//        if (this.characterConsciousnessStatus.effects == null) {
//        this.effects = CharacterEffects.builder().buildEmpty(); }

        return this;
    }

    public CharacterBuilder validate() throws ValidationException {
        Utils.validate(this);
        return this;
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
        this.status.currentHitPoints = this.body.curHP;
    }

    public void applyHealing(int hp) {
        this.body.applyHealing(hp);
        this.status.currentHitPoints = this.body.curHP;
    }

    public Boolean isValidDefender() { return this.body.isValidDefender(); }

    public Boolean isValidAttacker() { return this.body.isValidAttacker(); }

//    public Map<Effect, EffectStatus> getEffects() {
//        return this.effects.getEffects(); }
//
//    private void expireEffect(Effect effect) {
//        this.effects.getEffects().remove(effect);
//    }
//
//    public void incrementEffect(Effect effect) {
//        this.effects.getEffects().get(effect).increment();
//
//        if (!this.effects.getEffects().get(effect).isValid()) {
//            expireEffect(effect);
//        }
//    }

    public void rebuildAbilityModifiers() {
        try {
        this.abilityModifier = AbilityModifier.builder()
                .define(this.status.currentStrength, this.status.currentIntelligence, this.status.currentWisdom, this.status.currentDexterity, this.status.currentConstitution, this.status.currentCharisma)
                .build(); }
        catch (CharacterInitException ignore) {};

    }

    public void applyStatusChange(StatTypeEnum statTypeEnum, int change) {
        switch (statTypeEnum) {
            case STR:
                this.status.currentStrength += change;
                rebuildAbilityModifiers();
                return;

            case INT:
                this.status.currentIntelligence += change;
                rebuildAbilityModifiers();
                return;

            case WIZ:
                this.status.currentWisdom += change;
                rebuildAbilityModifiers();
                return;

            case CON:
                this.status.currentConstitution += change;
                rebuildAbilityModifiers();
                return;

            case DEX:
                this.status.currentDexterity += change;
                rebuildAbilityModifiers();
                return;

            case CHA:
                this.status.currentCharisma += change;
                rebuildAbilityModifiers();
                return;

            case HP:
                if (change > 0) {
                    this.applyHealing(change);
                    return;
                }
                this.applyDamage(change);
        }

    }

    public int getCurrentHp() {
        return this.getBody().getCurrentHp();
    }
}
