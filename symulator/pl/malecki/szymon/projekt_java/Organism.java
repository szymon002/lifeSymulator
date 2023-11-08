package pl.malecki.szymon.projekt_java;
import javax.swing.*;
import java.awt.*;

public abstract class Organism {
    protected char symbol;
    protected Color color;
    protected int strength;
    protected int initiative;
    protected int age;
    protected int x, y, position;
    protected int newX, newY;
    protected boolean alive;
    protected World world;

    public Organism(World world, int x, int y, int strength, int initiative, char symbol, boolean alive, int age, Color color) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.strength = strength;
        this.initiative = initiative;
        this.symbol = symbol;
        this.alive = alive;
        this.age = age;
        this.color = color;
    }

    public abstract void action();
    public abstract void collision();
    public abstract String getName();

    public boolean didBounceAttack(Organism attacker, Organism defender) {
        return false;
    }

    public void effectAfterEat(Organism org) {}

    public void draw() {
        System.out.print(this.getSymbol());
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setPosition(int pos) {
        this.position = pos;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void kill() {
        this.alive = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getInitiative() {
        return initiative;
    }

    public int getStrength() {
        return strength;
    }

    public int getPosition() {
        return position;
    }

    public int getAge() {
        return age;
    }

    public int getNewX() {
        return newX;
    }

    public int getNewY() {
        return newY;
    }

    public char getSymbol() {
        return symbol;
    }

    public Color getColor() {
        return color;
    }

    public boolean getAlive() {
        return alive;
    }
}
