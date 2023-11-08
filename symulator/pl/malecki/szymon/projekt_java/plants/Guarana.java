package pl.malecki.szymon.projekt_java.plants;
import pl.malecki.szymon.projekt_java.Plant;
import pl.malecki.szymon.projekt_java.World;
import pl.malecki.szymon.projekt_java.Organism;

import java.awt.*;

public class Guarana extends Plant{
    public Guarana(int x, int y, World world) {
        super(world, x, y, 0, 0, 'G', true, Color.blue  );
    }

    @Override
    public String getName() {
        return "Guarana";
    }

    @Override
    public void effectAfterEat(Organism org) {
        int strength = org.getStrength();
        org.setStrength(strength + 3);
    }
}
