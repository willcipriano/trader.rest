package trader.rest.combat.entity;

public enum CharacterDaoComponentEnum {
    SHEET("Character Sheet", "sheet", -1),
    STATUS("Character Status", "status", 0),
    INVENTORY("Character Inventory", "inventory", 1);

    public final String name;
    public final String shortName;
    public final int num;

    private CharacterDaoComponentEnum(String name, String shortName, int num) {
        this.name = name;
        this.shortName = shortName;
        this.num = num;
    }

    }
