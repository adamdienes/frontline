package frontline;

import frontline.entities.Cavalry;
import frontline.entities.MovingEntity;
import frontline.entities.Warrior;
import frontline.persistence.Player;
import frontline.persistence.UnitFactory;
import frontline.persistence.UnitType;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertTrue;

public class UnitFactoryTester {

    @Test
    public void createCavalryTester() {
        MovingEntity entity = UnitFactory.makeUnit(UnitType.Cavalry, 0, 0, new Player("", Color.blue));
        assertTrue(entity instanceof Cavalry);
    }

    @Test
    public void createWarriorTester() {
        MovingEntity entity = UnitFactory.makeUnit(UnitType.Warrior, 0, 0, new Player("", Color.blue));
        assertTrue(entity instanceof Warrior);
    }
}
