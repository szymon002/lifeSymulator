package pl.malecki.szymon.projekt_java.plants;
import pl.malecki.szymon.projekt_java.Plant;
import pl.malecki.szymon.projekt_java.World;

import java.awt.*;

public class Grass extends Plant{
    public Grass(int x, int y, World world) {
        super(world, x, y, 0, 0, 'T', true, Color.GREEN);
    }

    @Override
    public String getName() {
        return "Grass";
    }
}
