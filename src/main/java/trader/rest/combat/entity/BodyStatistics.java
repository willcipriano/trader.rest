package trader.rest.combat.entity;

import lombok.Builder;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Builder
public class BodyStatistics {
    Integer maxHP;
    Integer curMaxHp;
    Integer curHP;
    CharacterConsciousnessStatus status;
    Boolean validAttacker;
    Boolean validDefender;

    public void statusCheck() {
        if (this.curHP > 0) {
            this.status = CharacterConsciousnessStatus.ALIVE_WELL;
            this.validAttacker = true;
            this.validDefender = true;
        } else {
            this.status = CharacterConsciousnessStatus.UNCONSCIOUS;
            this.validAttacker = false;
            this.validDefender = true;
            this.curHP = 0;
        }

        if (this.curHP > this.curMaxHp) {
            this.curHP = this.curMaxHp;
        }
    }

    public void applyDamage(int damage) {
        this.curHP += damage;
        statusCheck();
    }

    public void applyHealing(int healing) {
        this.curHP += healing;
        statusCheck();
    }

    public void changeMaxHitPoints(int hp) {
        if (hp > 0) {
            this.curMaxHp = hp;
        } else {
            this.curMaxHp = 1;
        }
    }

    public void reduceMaxHitPoints(int hp) {
        changeMaxHitPoints(this.curHP - hp);
    }

    public void increaseMaxHitPoints(int hp) {
        changeMaxHitPoints(this.curHP + hp);
    }

    public void resetMaxHitPoints() {
        this.curMaxHp = this.maxHP;
    }

    public void heal() {
        this.curHP = this.curMaxHp;
        this.statusCheck();
    }

    public void reset() {
        resetMaxHitPoints();
        heal();
    }

    public void restore() {
        if (curMaxHp < maxHP) {
            reset();
        return;}
        heal();
    }

    public Boolean isValidDefender() {
        return this.validDefender;
    }

    public Boolean isValidAttacker() {
        return this.validAttacker;
    }

    public static class BodyStatisticsBuilder {
        @NotNull
        @Min(1)
        @Max(9999)
        Integer maxHP;

        @NotNull
        @Min(0)
        @Max(9999)
        Integer curHP;

        @NotNull
        CharacterConsciousnessStatus status;

        @NotNull
        Boolean validAttacker;

        @NotNull
        Boolean validDefender;

        private void makeAlive() {
            this.validAttacker = true;
            this.validDefender = true;
            this.status = CharacterConsciousnessStatus.ALIVE_WELL;
        }

        private void makeUnconscious() {
            this.validAttacker = false;
            this.validDefender = true;
            this.status = CharacterConsciousnessStatus.UNCONSCIOUS;
        }

        public BodyStatisticsBuilder hitPoints(int maxHP) {
            this.maxHP = maxHP;
            this.curMaxHp = maxHP;
            this.curHP = maxHP;

            if (maxHP > 0) {
                makeAlive();
            } else {
                makeUnconscious();
            }

            return this;
        }

        public BodyStatisticsBuilder calculateStartingHitPointsFromCharacterBuilder(Character.CharacterBuilder character) {
            int hp = character.sheet.level + 1;
            double conBonus = character.abilityModifier.constitution;

            if (conBonus > 1) {
                hp += hp + (conBonus * 2) + character.sheet.maxConstitution;
            } else {
                hp += hp + character.sheet.maxConstitution;
            }


            return this.hitPoints(hp);
        }

    }

    public int getCurrentHp() {
        return this.curHP;
    }
}
