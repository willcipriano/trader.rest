package trader.rest.combat.entity;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@Builder
public class CharacterSheet {
    @NotNull
    @Size(max = 75, min = 1)
    String name;

    @Min(1)
    @Max(999)
    int maxStrength;

    @Min(1)
    @Max(999)
    int maxIntelligence;

    @Min(1)
    @Max(999)
    int maxWisdom;

    @Min(1)
    @Max(999)
    int maxDexterity;

    @Min(1)
    @Max(999)
    int maxConstitution;

    @Min(1)
    @Max(999)
    int maxCharisma;

    @Min(1)
    @Max(999)
    int level;

    public CharacterSheet() {}

    public CharacterSheet(String name, int maxStrength, int maxIntelligence, int maxWisdom, int maxDexterity, int maxConstitution, int maxCharisma, int level) {
        this.name = name;
        this.maxStrength = maxStrength;
        this.maxIntelligence = maxIntelligence;
        this.maxWisdom = maxWisdom;
        this.maxDexterity = maxDexterity;
        this.maxCharisma = maxCharisma;
        this.level = level;
    }

    public static class CharacterSheetBuilder {
        UUID uuid;

        String name;

        int maxStrength;

        int maxIntelligence;

        int maxWisdom;

        int maxDexterity;

        int maxConstitution;

        int maxCharisma;

        int level;

    }
}
