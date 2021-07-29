package trader.rest.combat.entity;

public enum CombatFailureEnum {
    HIT("Low Hit", "low_hit", 0),
    DMG("Low Damage", "low_dmg", 1),
    EQUAL_HIT("Equal Hit", "equal_hit", 2),
    EQUAL_DMG("Equal Damage", "equal_dmg", 3),
    SUCCESS("Success", "success", 4);

    public final String name;
    public final String shortName;
    public final int num;

    private CombatFailureEnum(String name, String shortName, int num) {
        this.name = name;
        this.shortName = shortName;
        this.num = num;
    }

    }
