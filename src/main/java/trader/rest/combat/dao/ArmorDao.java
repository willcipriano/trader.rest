package trader.rest.combat.dao;

import lombok.Data;
import org.hibernate.annotations.Type;
import trader.rest.combat.entity.ArmorItem;
import trader.rest.combat.entity.ArmorSlotEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Map;
import java.util.UUID;

@Data
@Entity
public class ArmorDao {
    @Id
    UUID uuid;

    @Type(type = "jsonb")
    @Column(columnDefinition = "json")
    Map<ArmorSlotEnum, ArmorItem> items;
}
