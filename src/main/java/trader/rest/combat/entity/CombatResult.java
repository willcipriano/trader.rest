package trader.rest.combat.entity;

import lombok.Builder;
import trader.rest.combat.exception.DoubleCombatResultApplicationException;

import javax.validation.constraints.NotNull;
import java.util.*;

@Builder
public class CombatResult {

    @NotNull
    private final Character belligerent;

    @NotNull
    private final Character defender;

    @NotNull
    private Map<StatTypeEnum, Integer> defenderStatChanges;

    @NotNull
    private Map<StatTypeEnum, Integer> attackerStatChanges;

    @NotNull
    private List<CombatFailureEnum> combatFailureReasons;

    @NotNull
    private List<EffectResult> effectResults;

    private boolean applied = false;

    public void setChangesApplied() throws DoubleCombatResultApplicationException {
        if (!applied) {
        applied = true; } else {
//            Safeguard against applying changes to characters multiple times
            throw new DoubleCombatResultApplicationException(belligerent, defender);
        }
    }

    public void setNewStatChange(HostilityModeEnum hostile, StatTypeEnum statType, Integer value) {
        switch (hostile) {
            case DEFENDER:
                if (this.defenderStatChanges == null) {
                    this.defenderStatChanges = new HashMap<>(); }
                this.defenderStatChanges.put(statType, value);
                return;

            case BELLIGERENT:
                if (this.attackerStatChanges == null) {
                    this.attackerStatChanges = new HashMap<>(); }
                this.attackerStatChanges.put(statType, value);
        }
    }

    private int getStatChange(HostilityModeEnum hostile, StatTypeEnum statTypeEnum) {
        switch (hostile) {
            case BELLIGERENT:
                if (attackerStatChanges == null) {
                    return 0;
                }
                return this.attackerStatChanges.getOrDefault(statTypeEnum, 0);


            case DEFENDER:
                if (defenderStatChanges == null) {
                    return 0;
                }
                return this.defenderStatChanges.getOrDefault(statTypeEnum, 0);

            default:
                return 0;
        }
    }

    public int getAtkHPChange() {
        return getStatChange(HostilityModeEnum.BELLIGERENT, StatTypeEnum.HP);
    }

    public int getDefHPChange() {
        return getStatChange(HostilityModeEnum.DEFENDER, StatTypeEnum.HP);
    }

    public Map<StatTypeEnum, Integer> getAtkStatChanges() {
        return this.attackerStatChanges;
    }

    public Map<StatTypeEnum, Integer> getDefStatChanges() {
        return this.defenderStatChanges;
    }

    private void setSuccess() {
        List<CombatFailureEnum> failureReasons = new ArrayList<>();
        failureReasons.add(CombatFailureEnum.SUCCESS);
        this.combatFailureReasons = failureReasons;
    }

    public void signOff() {
        if (this.defenderStatChanges.size() > 0) {
            setSuccess();
        }
    }

    public CombatResult signOffAndSend() {
        signOff();
        return this;
    }


    public void setCombatFailureReasons(List<CombatFailureEnum> failures) {
        this.combatFailureReasons = failures;
    }

    public void setEffectResults(List<EffectResult> effects) {
        this.effectResults = effects;
    }

    public static class CombatResultBuilder {
        @NotNull
        private Character belligerent;

        @NotNull
        private Character defender;

        @NotNull
        private List<CombatFailureEnum> combatFailureReasons;

        @NotNull
        private List<EffectResult> effectResults;

        public CombatResult quickReport(Character belligerent, Character defender, CombatFailureEnum failureEnum) {
            this.belligerent = belligerent;
            this.defender = defender;
            this.combatFailureReasons = Collections.singletonList(failureEnum);
            return this.build();
        }
    }
    }
