/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontline.entities;

import frontline.persistence.Player;
import frontline.res.ResourceLoader;

import java.awt.*;

public class Barrack extends StaticEntity {

    public Barrack(int x, int y, Player owner) {
        super(x, y, owner);
        if (owner.getColor() == Color.BLUE) {
            this.img = ResourceLoader.loadImage("graphics/buildings/barracks/barrack_blue.png");
        } else {
            this.img = ResourceLoader.loadImage("graphics/buildings/barracks/barrack_red.png");
        }
    }

    @Override
    public int getSignalsToInvoke() {
        return 1;
    }

    @Override
    public String toString() {
        return "Barrack{" +
                "owner=" + owner +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}