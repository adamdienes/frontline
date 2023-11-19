package frontline;

import frontline.entities.BrickTower;
import frontline.entities.IronTower;
import frontline.entities.Tower;
import frontline.entities.WoodenTower;
import frontline.persistence.Player;
import frontline.persistence.TowerFactory;
import frontline.persistence.TowerType;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertTrue;

public class TowerFactoryTester {
    @Test
    public void createIronTowerTester() {
        Tower tower = TowerFactory.makeTower(TowerType.Iron, 0, 0, new Player("", Color.blue));
        assertTrue(tower instanceof IronTower);
    }

    @Test
    public void createBrickTowerTester() {
        Tower tower = TowerFactory.makeTower(TowerType.Brick, 0, 0, new Player("", Color.blue));
        assertTrue(tower instanceof BrickTower);
    }

    @Test
    public void createWoodenTowerTester() {
        Tower tower = TowerFactory.makeTower(TowerType.Wood, 0, 0, new Player("", Color.blue));
        assertTrue(tower instanceof WoodenTower);
    }
}
