package frontline.persistence;

import frontline.entities.Cavalry;
import frontline.entities.MovingEntity;
import frontline.entities.Warrior;

import java.util.InputMismatchException;

public class UnitFactory {
    public static MovingEntity makeUnit(UnitType type, int i, int j, Player owner) {
        switch (type) {
            case Warrior -> {
                return new Warrior(i, j, owner);
            }
            case Cavalry -> {
                return new Cavalry(i, j, owner);
            }
            default -> {
                throw new InputMismatchException();
            }
        }
    }
}
