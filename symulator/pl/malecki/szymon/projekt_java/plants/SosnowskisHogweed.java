package pl.malecki.szymon.projekt_java.plants;
import pl.malecki.szymon.projekt_java.Plant;
import pl.malecki.szymon.projekt_java.World;
import pl.malecki.szymon.projekt_java.Organism;

import java.awt.*;
import java.util.Random;

public class SosnowskisHogweed extends Plant{
    public SosnowskisHogweed(int x, int y, World world) {
        super(world, x, y, 10, 0, 'B', true, Color.red);
    }

    @Override
    public void action() {
        int x = this.getX();
        int y = this.getY();
        Organism org;
        for(int i = -1; i < 2; i++) {
            for(int j = -1; j < 2; j++) {
                if((y + i < 0 || y + i > world.getHeight() - 1) || (x + j < 0 || x + j > world.getWidth() - 1)) {
                    continue;
                } else {
                    if(world.getOrganism(y + i, x + j) != null && world.getOrganism(y + i, x + j).getInitiative() > 0) {
                        org = world.getOrganism(y + i, x + j);
                        world.commentator.addText("Sosnowski's Hogweed is killing: " + org.getName());
                        org.kill();
                        world.deleteOrganism(org);
                    }
                }
            }
        }
        Random rand = new Random();
        int chance = rand.nextInt(100);
        if (chance == 0 && this.getAge() > 5) {
            this.sow();
        }
    }

    @Override
    public String getName() {
        return "Sosnowski's Hogweed";
    }
}
