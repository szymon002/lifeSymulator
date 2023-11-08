package pl.malecki.szymon.projekt_java.animals;
import pl.malecki.szymon.projekt_java.*;

import java.awt.*;
import java.util.Random;


public class Turtle extends Animal{
    public Turtle(int x, int y, World world) {
        super(world, x, y, 2, 1, 'Z', true, Color.green);
    }

    @Override
    public void action() {
        Random rand = new Random();
        int chance = rand.nextInt(3);
        if(chance == 0) {
            super.action();
        }
    }

    @Override
    public boolean didBounceAttack(Organism attacker, Organism defender) {
        if(attacker.getStrength() < 5) {
            return true;
        } else return false;
    }

    @Override
    public String getName() {
        return "Turtle";
    }
}
