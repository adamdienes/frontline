/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontline.entities;

import frontline.persistence.Player;
import frontline.res.ResourceLoader;

import java.awt.*;

public class BrickTower extends Tower {
    public static final int COST = 500;

    public BrickTower(int x, int y, Player owner) {
        super(x, y, owner);
        if (owner.getColor() == Color.BLUE) {
            this.img = ResourceLoader.loadImage("graphics/buildings/towers/brick_tower_blue_abstract.png");
        } else {
            this.img = ResourceLoader.loadImage("graphics/buildings/towers/brick_tower_red_abstract.png");
        }
    }

    @Override
    public int getDamage() {
        return 4;
    }

    @Override
    public int getSignalsToInvoke() {
        return 1;
    }

    @Override
    public int destroyValue() {
        return level == 1 ? 150 : 175;
    }

    @Override
    public int getCost() {
        return BrickTower.COST;
    }
}
