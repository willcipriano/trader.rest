package trader.rest.combat.entity;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CharacterStatus {
    int currentStrength;

    int currentIntelligence;

    int currentWisdom;

    int currentDexterity;

    int currentCharisma;

    int currentHitPoints;

    int currentConstitution;
}

