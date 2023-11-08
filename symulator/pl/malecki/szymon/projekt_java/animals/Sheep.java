package pl.malecki.szymon.projekt_java.animals;
import pl.malecki.szymon.projekt_java.Animal;
import pl.malecki.szymon.projekt_java.World;

import java.awt.*;

public class Sheep extends Animal{
    public Sheep(int x, int y, World world) {
        super(world, x, y, 4, 4, 'O', true, Color.lightGray);
    }

    @Override
    public String getName() {
        return "Sheep";
    }
}
