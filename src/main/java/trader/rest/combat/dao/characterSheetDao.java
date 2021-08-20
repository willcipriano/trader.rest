package trader.rest.combat.dao;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Data
@Entity
public class characterSheetDao {
    @Id
    UUID uuid;

    @Column
    String name;

    @Column
    int maxStrength;

    @Column
    int maxIntelligence;

    @Column
    int maxWisdom;

    @Column
    int maxDexterity;

    @Column
    int maxCharisma;

    @Column
    int level;
}
