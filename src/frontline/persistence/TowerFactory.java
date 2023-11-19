package frontline.persistence;

import frontline.entities.BrickTower;
import frontline.entities.IronTower;
import frontline.entities.Tower;
import frontline.entities.WoodenTower;

import java.util.InputMismatchException;

public class TowerFactory {
    public static Tower makeTower(TowerType type, int i, int j, Player owner) {
        switch (type) {
            case Iron -> {
                return new IronTower(j, i, owner);
            }
            case Brick -> {
                return new BrickTower(j, i, owner);
            }
            case Wood -> {
                return new WoodenTower(j, i, owner);
            }
            default -> {
                throw new InputMismatchException();
            }
        }
    }
}
