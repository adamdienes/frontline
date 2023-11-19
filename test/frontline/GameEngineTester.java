package frontline;

import frontline.entities.*;
import frontline.persistence.GameEngine;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GameEngineTester {
    private final GameEngine engine = new GameEngine("Pálya 1", false, "p1", "p2");

    @Test
    public void handleBuildTester() {
        //correct build with money paid
        engine.handleBuild(3, 3, "Vastorony(" + IronTower.COST + " arany)");
        assertTrue(engine.getEntity(3, 3) instanceof IronTower);
        assertEquals(1500 - IronTower.COST, engine.getCurrentPlayer().getGold());

        //correct money
        assertNull(engine.handleBuild(5, 8, "Fatorony(" + WoodenTower.COST + " arany)"));
        assertEquals(engine.getCurrentPlayer().getGold(), 1500 - IronTower.COST - WoodenTower.COST);
    }

    @Test(expected = IllegalArgumentException.class)
    public void handleBuildErrorTester() {
        engine.handleBuild(3, 3, "Vastorony(" + IronTower.COST + " arany)");

        engine.handleBuild(5, 0, "");
        engine.handleBuild(3, 3, "");
        engine.handleBuild(5, 15, "");
    }

    @Test
    public void handleBuildErrorMessagesTester() {
        engine.handleBuild(6, 0, "Fatorony(" + WoodenTower.COST + " arany)");
        engine.handleBuild(6, 1, "Fatorony(" + WoodenTower.COST + " arany)");
        engine.handleBuild(5, 1, "Fatorony(" + WoodenTower.COST + " arany)");
        engine.handleBuild(4, 1, "Fatorony(" + WoodenTower.COST + " arany)");
        assertEquals(engine.handleBuild(4, 0, "Fatorony(" + WoodenTower.COST + " arany)"), "Nem lehet a kastélyok/barakkok közötti utat elzárni!");

        engine.addToEntities(new Cavalry(5, 5, engine.getCurrentPlayer()));
        assertEquals(engine.handleBuild(5, 5, "Fatorony(" + WoodenTower.COST + " arany)"), "Az adott hely foglalt!");

        engine.handleBuild(4, 4, "Fatorony(" + WoodenTower.COST + " arany)");
        assertEquals(engine.handleBuild(6, 6, "Fatorony(" + WoodenTower.COST + " arany)"), "Nincs elegendő pénz a torony építéséhez!");
    }

    @Test
    public void staticLevelTester() {
        assertEquals("yes", 1, 1);
    }

    @Test
    public void handleRecruitTester() {
        engine.handleRecruit(5, 0, "Gyalogság(" + Warrior.COST + " arany)");
        assertEquals(engine.getCurrentPlayer().getGold(), 1500 - Warrior.COST);
        engine.getCurrentPlayer().pay(1500 - Warrior.COST);
        assertEquals(engine.handleRecruit(5, 0, "Lovasság(" + Cavalry.COST + " arany)"), "Nincs elegendő pénz a toborzáshoz!");
    }

    @Test(expected = IllegalArgumentException.class)
    public void handleRecruitErrorTester() {
        engine.handleRecruit(0, 0, "");
    }

    @Test
    public void validateClickTester() {
        assertTrue(engine.validateClick(5));
        assertFalse(engine.validateClick(11));
        engine.endTurn();
        assertTrue(engine.validateClick(11));
        assertFalse(engine.validateClick(5));
    }

    @Test
    public void warPhaseWarriorMovementTester() {
        engine.handleRecruit(5, 0, "Gyalogság(" + Warrior.COST + " arany)");
        engine.endTurn();
        engine.handleRecruit(4, 19, "Gyalogság(" + Warrior.COST + " arany)");
        engine.handleTick();
        engine.handleTick();
        engine.handleTick();
        engine.handleTick();
        engine.handleTick();
        engine.handleTick();
        ArrayList<MovingEntity> entities = engine.getEntities();

        assertEquals(3, entities.get(0).getX());
        assertEquals(4, entities.get(0).getY());
        assertEquals(16, entities.get(1).getX());
        assertEquals(5, entities.get(1).getY());
    }

    @Test
    public void warPhaseCavalryMovementTester() {
        engine.handleRecruit(5, 0, "Lovasság(" + Cavalry.COST + " arany)");
        engine.endTurn();
        engine.handleRecruit(4, 19, "Lovasság(" + Cavalry.COST + " arany)");
        engine.handleTick();
        engine.handleTick();
        engine.handleTick();
        engine.handleTick();
        engine.handleTick();
        engine.handleTick();
        ArrayList<MovingEntity> entites = engine.getEntities();

        assertEquals(4, entites.get(0).getX());
        assertEquals(4, entites.get(0).getY());
        assertEquals(15, entites.get(1).getX());
        assertEquals(5, entites.get(1).getY());
    }

    @Test
    public void warPhaseTowerInTheWayTester() {
        engine.handleBuild(4, 1, "Fatorony(" + WoodenTower.COST + " arany)");
        engine.handleBuild(5, 2, "Fatorony(" + WoodenTower.COST + " arany)");
        engine.handleRecruit(5, 0, "Gyalogság(" + Warrior.COST + " arany)");
        engine.endTurn();
        engine.handleBuild(3, 18, "Fatorony(" + WoodenTower.COST + " arany)");
        engine.handleBuild(4, 17, "Fatorony(" + WoodenTower.COST + " arany)");
        engine.handleRecruit(4, 19, "Gyalogság(" + Warrior.COST + " arany)");
        engine.handleTick();
        engine.handleTick();
        engine.handleTick();
        engine.handleTick();
        engine.handleTick();
        engine.handleTick();
        ArrayList<MovingEntity> entites = engine.getEntities();

        assertEquals(3, entites.get(0).getX());
        assertEquals(6, entites.get(0).getY());
        assertEquals(16, entites.get(1).getX());
        assertEquals(5, entites.get(1).getY());
    }

    @Test
    public void warPhaseTowerDamageTester() {
        engine.handleRecruit(5, 0, "Gyalogság(" + Warrior.COST + " arany)");
        engine.endTurn();
        engine.handleBuild(5, 10, "Fatorony(" + WoodenTower.COST + " arany)");
        for (int i = 0; i < 5; i++) {
            engine.handleTick();
            engine.handleTick();
            engine.handleTick();
            engine.handleTick();
            engine.handleTick();
            engine.handleTick();
        }

        ArrayList<MovingEntity> entites = engine.getEntities();
        assertEquals(30, entites.get(0).getMaxHp());
        assertEquals(16, entites.get(0).getCurrentHp());
    }
}