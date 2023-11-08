package pl.malecki.szymon.projekt_java.animals;
import pl.malecki.szymon.projekt_java.Animal;
import pl.malecki.szymon.projekt_java.World;

import java.awt.*;

public class Fox extends Animal{
    public Fox(int x, int y, World world) {
        super(world, x, y, 3, 7, 'L', true, Color.orange);
    }

    private boolean doesExistEmptyField() {
        int count = 0;
        for(int i = -1; i < 2; i++) {
            for(int j = -1; j < 2; j ++) {
                if((y + i < 0 || y + i > world.getHeight() - 1) || (x + j < 0 || x + j > world.getWidth() - 1)) {
                    count++;
                    continue;
                } else {
                    if(world.getOrganism(y + i, x + j) != null) {
                        count++;
                    }
                }
            }
        }
        if(count < 9) return true;
        return false;
    }

    @Override
    public void action() {
        this.newX = this.findXTarget();
        this.newY = this.findYTarget();
        if(this.newX > world.getWidth() - 1 || this.newX < 0 || this.newY < 0 || this.newY > world.getHeight() - 1) {
            return;
        }

        if(world.getOrganism(this.newY, this.newX) != null) {
            if(doesExistEmptyField(this.newX, this.newY)) {
                if(world.getOrganism(this.newY, this.newX).getStrength() > this.getStrength()) {
                    this.action();
                } else {
                    super.action();
                }
            } else {
                this.x = this.x;
                this.y = this.y;
            }
        } else {
            super.action();
        }
    }

    @Override
    public String getName() {
        return "Fox";
    }
}
