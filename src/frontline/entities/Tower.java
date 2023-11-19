/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontline.entities;

import frontline.persistence.Player;

public abstract class Tower extends StaticEntity {
    int level = 1;

    public Tower(int x, int y, Player owner) {
        super(x, y, owner);
    }

    @Override
    public String toString() {
        return "Torony(" +
                "tulaj:" + owner.getName() +
                ", x:" + x +
                ", y:" + y +
                ", torony szintje:" + level +
                ')';
    }

    public abstract int getDamage();

    public void upgrade() {
        if (level < 2) {
            level++;
            owner.pay(100);
        }
    }

    public int getLevel() {
        return level;
    }

    public abstract int destroyValue();

    public abstract int getCost();
}
