package trader.rest.combat.dao;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Data
@Entity
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class CharacterInventoryDao implements CharacterComponent{
    @Column
    UUID uuid;

    @Id
    UUID characterSheetUuid;

    @Type(type = "jsonb")
    @Column(columnDefinition = "json")
    ArmorDao armorDao;
}
