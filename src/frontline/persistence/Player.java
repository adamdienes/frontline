package frontline.persistence;

import java.awt.*;

public class Player {
    private final String name;
    private final Color color;
    private int gold = 1500;
    private int hp = 50;

    public Player(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public boolean canAfford(int cost) {
        return gold >= cost;
    }

    public void pay(int cost) {
        gold -= cost;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                '}';
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}