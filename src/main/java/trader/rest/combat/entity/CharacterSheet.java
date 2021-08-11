package trader.rest.combat.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class CharacterSheet {
    @Id
    @GeneratedValue(generator = "UUID")
    UUID uuid;

    @NotNull
    @Size(max = 75, min = 1)
    @Column
    String name;

    @Min(1)
    @Max(999)
    @Column
    int maxStrength;

    @Min(1)
    @Max(999)
    @Column
    int strength;

    @Min(1)
    @Max(999)
    @Column
    int maxIntelligence;

    @Min(1)
    @Max(999)
    @Column
    int intelligence;

    @Min(1)
    @Max(999)
    @Column
    int wisdom;

    @Min(1)
    @Max(999)
    @Column
    int maxWisdom;

    @Min(1)
    @Max(999)
    @Column
    int dexterity;

    @Min(1)
    @Max(999)
    @Column
    int maxDexterity;

    @Min(1)
    @Max(999)
    @Column
    int constitution;

    @Min(1)
    @Max(999)
    @Column
    int maxConstitution;

    @Min(1)
    @Max(999)
    @Column
    int charisma;

    @Min(1)
    @Max(999)
    @Column
    int maxCharisma;

    @Min(1)
    @Max(999)
    @Column
    int level;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    Armor armor;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    CharacterEffects effects;
}
