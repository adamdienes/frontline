package frontline.entities;

import frontline.res.ResourceLoader;

import java.awt.*;

public abstract class Entity {
    public static int SIZE = 70;
    protected int x;
    protected int y;
    protected Image img = ResourceLoader.loadImage("graphics/buildings/towers/wooden_tower_blue.png");

    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        g.drawImage(img, x * SIZE, y * SIZE, SIZE, SIZE, null);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}
