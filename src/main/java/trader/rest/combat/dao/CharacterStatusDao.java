package trader.rest.combat.dao;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Data
@Entity
public class CharacterStatusDao implements CharacterComponent {
    @Id
    UUID characterSheetUuid;

    @Column
    UUID id;

    @Column
    int currentStrength;

    @Column
    int currentIntelligence;

    @Column
    int currentWisdom;

    @Column
    int currentDexterity;

    @Column
    int currentCharisma;

    @Column
    int currentHitPoints;
}
