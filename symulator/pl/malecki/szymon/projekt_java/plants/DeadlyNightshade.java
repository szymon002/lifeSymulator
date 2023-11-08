package pl.malecki.szymon.projekt_java.plants;
import pl.malecki.szymon.projekt_java.Plant;
import pl.malecki.szymon.projekt_java.World;

import java.awt.*;
import java.util.Random;

public class DeadlyNightshade extends Plant{
    public DeadlyNightshade(int x, int y, World world) {
        super(world, x, y, 99, 0, 'J', true, Color.pink);
    }
    @Override
    public void action() {
        Random rand = new Random();
        int chance = rand.nextInt(20);
        if (chance == 0 && this.getAge() > 5) {
            this.sow();
        }
    }

    @Override
    public String getName() {
        return "Deadly Nightshade";
    }
}
