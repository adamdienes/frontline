package frontline.persistence;

import frontline.entities.BrickTower;
import frontline.entities.IronTower;
import frontline.entities.WoodenTower;

import java.util.InputMismatchException;

public enum TowerType {
    Iron("Vastorony(" + IronTower.COST + " arany)"),
    Brick("Téglatorony(" + BrickTower.COST + " arany)"),
    Wood("Fatorony(" + WoodenTower.COST + " arany)");

    public final String value;

    TowerType(String s) {
        value = s;
    }

    public static TowerType getInstance(String c) {
        for (TowerType tt : TowerType.values()) {
            if (tt.value == c) return tt;
        }
        throw new InputMismatchException();
    }

    @Override
    public String toString() {
        return "" + value;
    }
}
