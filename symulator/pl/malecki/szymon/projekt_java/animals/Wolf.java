package pl.malecki.szymon.projekt_java.animals;
import pl.malecki.szymon.projekt_java.Animal;
import pl.malecki.szymon.projekt_java.World;

import java.awt.*;


public class Wolf extends Animal{
    public Wolf(int x, int y, World world) {
        super(world, x, y, 9, 5, 'W', true, Color.gray);
    }

    @Override
    public String getName() {
        return "Wolf";
    }
}
