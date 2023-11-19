/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontline.entities;

import frontline.persistence.Player;
import frontline.res.ResourceLoader;

import java.awt.*;

public class Cavalry extends MovingEntity {
    public static final int COST = 125;

    public Cavalry(int x, int y, Player owner) {
        super(x, y, owner);
        if (owner.getColor() == Color.BLUE) {
            this.img = ResourceLoader.loadImage("graphics/units/cavalry/cavalry_blue.png");
        } else {
            this.img = ResourceLoader.loadImage("graphics/units/cavalry/cavalry_red.png");
        }
        maxHp = 20;
        currentHp = 20;
    }

    @Override
    public int getSignalsToInvoke() {
        return 2;
    }

    @Override
    public int getCost() {
        return Cavalry.COST;
    }
}
