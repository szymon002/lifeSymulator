package pl.malecki.szymon.projekt_java.animals;
import pl.malecki.szymon.projekt_java.Animal;
import pl.malecki.szymon.projekt_java.Organism;
import pl.malecki.szymon.projekt_java.World;

import java.awt.*;
import java.util.Random;

public class Antelope extends Animal{
    public Antelope(int x, int y, World world) {
        super(world, x, y, 4, 4, 'A', true, Color.cyan);
    }

    @Override
    public void action() {
        for(int i = 0; i < 2; i++) {
            if(this.alive) {
                super.action();
            }
        }
    }

    @Override
    public boolean didBounceAttack(Organism attacker, Organism defender) {
        Random rand = new Random();
        int chance = rand.nextInt(2);
        if(chance == 0) {
            world.deleteFromMap(defender);
            defender.setX(defender.getX()+1);
            world.commentator.addText("Antelope run away from " + attacker.getName());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getName() {
        return "Antelope";
    }
}
