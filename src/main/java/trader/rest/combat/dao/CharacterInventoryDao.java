package trader.rest.combat.dao;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Data
@Entity
public class CharacterInventoryDao {
    @Column
    UUID uuid;

    @Id
    UUID characterSheetUuid;

    @Type(type = "jsonb")
    @Column(columnDefinition = "json")
    ArmorDao armorDao;
}
