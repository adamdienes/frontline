package frontline;

import frontline.persistence.Player;
import org.junit.Test;
import java.awt.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PlayerTester {
    private final Player player = new Player("p1", Color.blue);

    @Test
    public void playerFunctionTester() {
        player.pay(100);
        assertEquals(player.getGold(), 1500 - 100);
        assertTrue(player.canAfford(1000));
        assertEquals(player.getColor(), Color.blue);
    }
}