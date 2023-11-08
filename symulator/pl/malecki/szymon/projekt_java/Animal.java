package pl.malecki.szymon.projekt_java;
import pl.malecki.szymon.projekt_java.animals.*;

import java.awt.*;
import java.util.Random;

public abstract class Animal extends Organism{
    public Animal(World world, int x, int y, int strength, int initiative, char symbol, boolean alive, Color color) {
        super(world, x, y, strength, initiative, symbol, alive, 0, color);
    }

    protected void procreate() {
        Organism org = null;
        if(this.x + 1 >= world.getWidth() - 1 || world.getOrganism(this.y, this.x + 1) != null) {
            return;
        } else {
            switch(this.getSymbol()) {
                case 'W': org = new Wolf(this.x + 1, this.y, this.world); break;
                case 'O': org = new Sheep(this.x + 1, this.y, this.world); break;
                case 'A': org = new Antelope(this.x + 1, this.y, this.world); break;
                case 'L': org = new Fox(this.x + 1, this.y, this.world); break;
                case 'Z': org = new Turtle(this.x + 1, this.y, this.world); break;
            }
        }
        world.addOrganism(org);
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
    public boolean didBounceAttack(Organism attacker, Organism defender) {
        return false;
    }

    @Override
    public void action() {
        if(this.getSymbol() != 'L') {
            this.newX = this.findXTarget();
            this.newY = this.findYTarget();
        }
        if(this.newX == this.getX() && this.newY == this.getY() && this.getSymbol() != 'C') {
            this.action();
        } else {
            if(this.newX < world.getWidth() && this.newY < world.getHeight() && this.newX >= 0 && this.newY >= 0) {
                if(world.isEmptyField(this.newY, this.newX)) {
                    this.x = this.newX;
                    this.y = this.newY;
                } else {
                    this.collision();
                }
            } else {
                this.action();
            }
        }
    }

    @Override
    public void collision() {
        Organism organism = world.getOrganism(this.newY, this.newX);
        if(this.getSymbol() == organism.getSymbol()) {
            procreate();
        } else if (this.getStrength() > organism.getStrength()) {
            if(!organism.didBounceAttack(this, organism)) {
                if(organism.getInitiative() == 0) {
                    organism.effectAfterEat(this);
                }
                if(organism.getInitiative() == 0) {
                    world.commentator.addText(this.getName() + " is eating " + organism.getName());
                } else {
                    world.commentator.addText(this.getName() + " is killing " + organism.getName());
                }
                organism.kill();
                world.deleteOrganism(organism);
                this.x = this.newX;
                this.y = this.newY;
            } else {
                world.commentator.addText(this.getName() + " has been bounced by " + organism.getName());
                this.x = this.x;
                this.y = this.y;
            }
        } else {
            world.commentator.addText(this.getName() + " is killed by " + organism.getName());
            this.kill();
            world.deleteOrganism(this);
        }
    }

    @Override
    abstract public String getName();


    protected int findXTarget() {
        Random rand = new Random();
        int i = rand.nextInt(2);
        int newX = switch (i) {
            case 0 -> this.x + 1;
            case 1 -> this.x - 1;
            case 2 -> this.x;
            default -> this.x;
        };
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
}
