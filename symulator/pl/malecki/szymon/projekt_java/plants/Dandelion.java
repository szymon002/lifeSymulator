package pl.malecki.szymon.projekt_java.plants;
import pl.malecki.szymon.projekt_java.Plant;
import pl.malecki.szymon.projekt_java.World;

import java.awt.*;
import java.util.Random;

public class Dandelion extends Plant{
    public Dandelion(int x, int y, World world) {
        super(world, x, y, 0, 0, 'M', true, Color.yellow);
    }
    @Override
    public void action() {
        Random rand = new Random();
        for(int i = 0; i < 3; i++) {
            int chance = rand.nextInt(9);
            if(chance == 0 && this.getAge() > 5) {
                this.sow();
            }
        }
    }

    @Override
    public String getName() {
        return "Dandelion";
    }
}
