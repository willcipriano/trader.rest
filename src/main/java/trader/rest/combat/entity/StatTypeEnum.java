package trader.rest.combat.entity;

import java.util.Arrays;
import java.util.List;

public enum StatTypeEnum {
    STR("Strength", "str", 0),
    INT("Intelligence", "int", 1),
    WIZ("Wisdom", "wiz", 2),
    DEX("Dexterity", "dex", 3),
    CON("Constitution", "con", 4),
    CHA("Charisma", "cha", 5),
    HP("Hit Points", "hp", 6),
    DMG("Damage", "dmg", 7),
    HIT("Hit", "hit", 8);

    public final String name;
    public final String shortName;
    public final int num;

    private StatTypeEnum(String name, String shortName, int num) {
        this.name = name;
        this.shortName = shortName;
        this.num = num;
    }

    public List<String> getCoreNames() {
        return Arrays.asList(STR.name, INT.name, WIZ.name, DEX.name, CON.name, CHA.name);
    }

    public List<String> getCoreShortNames() {
        return Arrays.asList(STR.shortName, INT.shortName, WIZ.shortName, DEX.shortName, CON.shortName, CHA.shortName);
    }
}
