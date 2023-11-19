package frontline.persistence;

import java.util.InputMismatchException;

public enum UnitType {
    Warrior("Gyalogság(" + frontline.entities.Warrior.COST + " arany)"),
    Cavalry("Lovasság(" + frontline.entities.Cavalry.COST + " arany)");

    public final String value;

    UnitType(String s) {
        value = s;
    }

    public static UnitType getInstance(String c) {
        for (UnitType tt : UnitType.values()) {
            if (tt.value == c) return tt;
        }
        throw new InputMismatchException();
    }

    @Override
    public String toString() {
        return "" + value;
    }
}
