package pl.malecki.szymon.projekt_java;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import pl.malecki.szymon.projekt_java.animals.*;
import pl.malecki.szymon.projekt_java.plants.*;

public class World {
    private final int width;
    private final int height;
    private int turn;
    private ArrayList<Organism> newOrganisms = new ArrayList<Organism>();
    private ArrayList<Integer> deadOrganisms = new ArrayList<Integer>();
    private Organism map[][];
    protected ArrayList<Organism> organisms = new ArrayList<Organism>();
    private WorldGui worldGui;
    private Human human;
    public Comments commentator = new Comments();

    public World(int width, int height, WorldGui worldGui) {
        this.width = width;
        this.height = height;
        this.turn = 0;
        this.map = new Organism[height][width];
        this.worldGui = worldGui;
        this.human = new Human(10, 10, this);
        organisms.add(human);
        map[10][10] = human;
    }

    public void createWorld() {
        if(turn == 0) {
            addOrganisms();
        }
        worldGui.drawWorld(height, width, map);
    }

    private void addOrganisms(){
        Organism organism;
        for(int i = 0; i < (height+width); i++) {
            organism = generateOrganism();
            organisms.add(organism);
            map[organism.getY()][organism.getX()] = organism;
        }
        sortOrganisms();
        setPosition();
        System.out.println("Number of organism: " + organisms.size());
    }

    private void sortOrganisms() {
        Organism temp;
        for(int i = 0; i < organisms.size() - 1; i++) {
            for(int j = 0; j < organisms.size() - i - 1; j++) {
                if(organisms.get(j).getInitiative() < organisms.get(j+1).getInitiative()) {
                    temp = organisms.get(j);
                    organisms.set(j, organisms.get(j+1));
                    organisms.set(j+1, temp);
                } else if (organisms.get(j).getInitiative() == organisms.get(j+1).getInitiative()){
                    if(organisms.get(j).getAge() < organisms.get(j+1).getAge()) {
                        temp = organisms.get(j);
                        organisms.set(j, organisms.get(j+1));
                        organisms.set(j+1, temp);
                    }
                }
            }
        }
    }

    private void deleteOrganisms() {
        for(int i = 0; i < deadOrganisms.size(); i++) {
            if(i != 0) {
                for(int j = 0; j < deadOrganisms.size(); j++) {
                    int temp = deadOrganisms.get(j);
                    deadOrganisms.set(j, temp);
                }
            }
            if(deadOrganisms.get(i) < 0 || deadOrganisms.get(i) >= organisms.size()) {
                return;
            }
            organisms.remove(deadOrganisms.get(i));
        }
    }

    private void setPosition() {
        for(int i = 0; i < organisms.size(); i++) {
            organisms.get(i).setPosition(i);
        }
    }

    private Organism generateOrganism() {
        Organism organism = null;
        Random rand = new Random();
        int x = rand.nextInt(width - 1);
        int y = rand.nextInt(height - 1);
        int o = rand.nextInt(10);
        while(getOrganism(y, x) != null) {
            x = rand.nextInt(width - 1);
            y = rand.nextInt(height - 1);
        }
        switch(o) {
            case 0: organism = new Wolf(x, y, this); break;
            case 1: organism = new Sheep(x, y, this); break;
            case 2: organism = new Fox(x, y, this); break;
            case 3: organism = new Turtle(x, y, this); break;
            case 4: organism = new Antelope(x, y, this); break;
            case 5: organism = new Dandelion(x, y, this); break;
            case 6: organism = new Guarana(x, y, this); break;
            case 7: organism = new DeadlyNightshade(x, y, this); break;
            case 8: organism = new SosnowskisHogweed(x, y, this); break;
            case 9: organism = new Grass(x, y, this); break;
        }
        return organism;
    }

    public boolean isEmptyField(int y, int x) {
        if(map[y][x] != null) return false;
        return true;
    }

    public Organism getOrganism(int y, int x) {
        return map[y][x];
    }

    public void printOrganisms() {
        for(int i = 0; i < organisms.size(); i++) {
            System.out.print(organisms.get(i).getSymbol());
        }
    }

    public void addOrganism(Organism org) {
        newOrganisms.add(newOrganisms.size(), org);
        map[org.getY()][org.getX()] = org;
    }

    public void deleteOrganism(Organism org) {
        deadOrganisms.add(deadOrganisms.size(), org.getPosition());
        map[org.getY()][org.getX()] = null;
    }

    public void nextTurn() {
        this.commentator.addText("Turn: " + (turn+1) + "\n");
        for(Organism o : organisms) {
            if(o.getAlive() == true) {
                map[o.getY()][o.getX()] = null;
                o.action();
                if(o.getAlive() == true) {
                    map[o.getY()][o.getX()] = o;
                    o.setAge(o.getAge()+1);
                }
            }
        }
        deleteOrganisms();
        organisms.addAll(organisms.size(), newOrganisms);
        newOrganisms.clear();
        deadOrganisms.clear();
        sortOrganisms();
        setPosition();
        turn++;
        System.out.println(commentator.getText());
        createWorld();
        commentator.deleteComments();
    }

    public void deleteFromMap(Organism org) {
        map[org.getY()][org.getX()] = null;
    }

    public Organism createOrganism(String symbol, int x, int y, World world) {
        Organism organism = null;
        switch(symbol) {
            case "W": organism = new Wolf(x, y, world); break;
            case "A": organism = new Antelope(x, y, world); break;
            case "L": organism = new Fox(x, y, world); break;
            case "O": organism = new Sheep(x, y, world); break;
            case "Z": organism = new Turtle(x, y, world); break;
            case "M": organism = new Dandelion(x, y, world); break;
            case "J": organism = new DeadlyNightshade(x, y, world); break;
            case "B": organism = new SosnowskisHogweed(x, y, world); break;
            case "T": organism = new Grass(x, y, world); break;
            case "G": organism = new Guarana(x, y, world); break;
                case "C": organism = new Human(x, y, world); break;
        }
        return organism;
    }

    public void saveToFile(String nameOfFile) {
        try {
            nameOfFile = nameOfFile + ".txt";
            File file = new File(nameOfFile);
            file.createNewFile();
            PrintWriter line = new PrintWriter(file);
            line.print(width + " ");
            line.print(height + " ");
            line.print(turn + "\n");
            for(int i = 0; i < organisms.size(); i++) {
                line.print(organisms.get(i).getSymbol() + " ");
                line.print(organisms.get(i).getX() + " ");
                line.print(organisms.get(i).getY() + " ");
                line.print(organisms.get(i).getStrength() + " ");
                line.print(organisms.get(i).getAlive() + " ");
                if(organisms.get(i).getSymbol() == 'C') {
                    line.print(human.getAbility() + " ");
                    line.print(human.getTimeOfAbility() + " ");
                    line.print(human.getWaitingTime() + " ");
                }
                line.println();
            }
            line.close();
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    public World loadGame(String nameOfFile) {
        try {
            nameOfFile = nameOfFile + ".txt";
            File file = new File(nameOfFile);

            Scanner scanner = new Scanner(file);
            String line = scanner.nextLine();
            String[] properties = line.split(" ");
                int width = Integer.parseInt(properties[0]);
            int height = Integer.parseInt(properties[1]);
            World tmp = new World(width, height, null);
            int turn = Integer.parseInt(properties[2]);
            tmp.turn = turn;
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                properties = line.split(" ");
                String symbol = properties[0];
                int x = Integer.parseInt(properties[1]);
                int y = Integer.parseInt(properties[2]);

                Organism tmpOrg = createOrganism(symbol, x, y, tmp);
                int strength = Integer.parseInt(properties[3]);
                tmpOrg.setStrength(strength);

                boolean alive = Boolean.parseBoolean(properties[4]);
                if(alive == true) {
                    if (tmpOrg.getSymbol() == 'C') {
                        boolean ability = Boolean.parseBoolean(properties[5]);
                        tmp.human.setAbility(ability);
                        int abilityTime = Integer.parseInt(properties[6]);
                        tmp.human.setTimeOfAbility(abilityTime);
                        int waitingTime = Integer.parseInt(properties[7]);
                        tmp.human.setWaitingTime(waitingTime);
                        tmp.human.x = x;
                        tmp.human.y = y;
                        tmp.map[y][x] = human;
                        tmp.map[10][10] = null;
                    } else {
                        tmp.organisms.add(tmpOrg);
                        tmp.map[tmpOrg.getY()][tmpOrg.getX()] = tmpOrg;
                    }
                }
            }
            scanner.close();
            return tmp;
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
        return null;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getTurn() {
        return turn;
    }

    public Human getHuman() {
        return human;
    }

    public Organism[][] getMap() {
        return map;
    }

    public void setGui(WorldGui worldgui) {
        this.worldGui = worldgui;
    }
}
