package pl.malecki.szymon.projekt_java;

import pl.malecki.szymon.projekt_java.plants.*;

import java.awt.*;
import java.util.Random;


public abstract class Plant extends Organism{
    public Plant(World world, int x, int y, int strength, int initiative, char symbol, boolean alive, Color color) {
        super(world, x, y, strength, 0, symbol, alive, 0, color);
    }

    protected void sow() {
        Organism org = null;
        if(!doesExistEmptyField(this.x, this.y)) {
            return;
        }
        int newX = this.findXTarget();
        int newY = this.findYTarget();

        if(newX == this.getX() && newY == this.getY()) {
            this.action();
        }

        if(newX < world.getWidth() && newY < world.getHeight() && newX >= 0 && newY >= 0) {
            if(world.getOrganism(newY, newX) != null) {
                this.action();
            } else {
                switch(this.getSymbol()) {
                    case 'T': org = new Grass(newX, newY, this.world); break;
                    case 'J': org = new DeadlyNightshade(newX, newY, this.world); break;
                    case 'B': org = new SosnowskisHogweed(newX, newY, this.world); break;
                    case 'M': org = new Dandelion(newX, newY, this.world); break;
                    case 'G': org = new Guarana(newX, newY, this.world); break;
                }
                world.commentator.addText(this.getName() + " sow yourself ");
                world.addOrganism(org);
            }
        }
    }

    protected int findXTarget() {
        Random rand = new Random();
        int i = rand.nextInt(2);
        int newX = this.x;
        switch(i) {
            case 0: newX = this.x + 1; break;
            case 1: newX = this.x - 1; break;
            case 2: newX = this.x; break;
        }
        return newX;
    }

    protected int findYTarget() {
        Random rand = new Random();
        int i = rand.nextInt(2);
        int newY = this.y;
        switch(i) {
            case 0: newY = this.y + 1; break;
            case 1: newY = this.y - 1; break;
            case 2: newY = this.y; break;
        }
        return newY;
    }

    protected boolean doesExistEmptyField(int x, int y) {
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
        Random rand = new Random();
        int chance = rand.nextInt(3);
        if(chance == 0 && this.getAge() > 5) {
            this.sow();
        }
    }

    @Override
    abstract public String getName();

    @Override
    public void collision() {}

    @Override
    public void effectAfterEat(Organism org) {}

}
