/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontline.entities;

import frontline.persistence.Player;
import frontline.res.ResourceLoader;

import java.awt.*;

public class Warrior extends MovingEntity {
    public static final int COST = 100;

    public Warrior(int x, int y, Player owner) {
        super(x, y, owner);
        if (owner.getColor() == Color.BLUE) {
            this.img = ResourceLoader.loadImage("graphics/units/warrior/warrior_blue_abstract.png");

        } else {
            this.img = ResourceLoader.loadImage("graphics/units/warrior/warrior_red_abstract.png");
        }
        maxHp = 30;
        currentHp = 30;
    }

    @Override
    public int getSignalsToInvoke() {
        return 3;
    }

    @Override
    public int getCost() {
        return Warrior.COST;
    }
}