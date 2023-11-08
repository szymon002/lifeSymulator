package pl.malecki.szymon.projekt_java.animals;
import pl.malecki.szymon.projekt_java.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Human extends Animal {

    private boolean ability;
    private int timeOfAbility, waitingTime, range;
    private char key;

    public Human(int x, int y, World world) {

        super(world, x, y, 5, 4, 'C', true, Color.black);
        this.ability = false;
        this.timeOfAbility = 5;
        this.waitingTime = 0;
        this.range = 1;
    }

    private void activeAbility() {
        this.timeOfAbility = 5;
        this.ability = true;
    }

    @Override
    public void action() {
        Random rand = new Random();
        int chance = rand.nextInt(1);

        if((timeOfAbility >= 2) && ability) {
            range = 2;
        } else if(timeOfAbility >= 0) {
            switch(chance) {
                case 0: range = 1; break;
                case 1: range = 2; break;
            }
        } else {
            range = 1;
            ability = false;
        }

        for(int i = 0; i < range; i++) {
            int newX = this.getX();
            int newY = this.getY();
            switch(key) {
                case 'w': newY--; break;
                case 's': newY++; break;
                case 'd': newX++; break;
                case 'a': newX--; break;
                case 'p': if(waitingTime <= 0) activeAbility(); return;
            }
            this.newX = newX;
            this.newY = newY;
            super.action();
        }
        if(ability) {
            this.timeOfAbility--;
            if(timeOfAbility <= 0) {
                waitingTime = 5;
            }
        } else {
            waitingTime--;
        }
    }

    @Override
    public String getName() {
        return "Human";
    }

    @Override
    public int findXTarget() {
        int newX = this.getNewX();
        return newX;
    }

    @Override
    public int findYTarget() {
        int newY = this.getNewY();
        return newY;
    }

    public void setKeyCode(char key) {
        this.key = key;
    }

    public void setAbility(boolean ability) {
        this.ability = ability;
    }

    public void setTimeOfAbility(int time) {
        this.timeOfAbility = time;
    }

    public void setWaitingTime(int time) {
        this.waitingTime = time;
    }

    public int getTimeOfAbility() {
        return timeOfAbility;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public boolean getAbility() {
        return ability;
    }

}
