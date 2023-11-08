package pl.malecki.szymon.projekt_java;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class WorldGui implements ActionListener, KeyListener{
    private JFrame frame;
    private JMenu menu;
    private JMenuItem load, save;
    private JPanel commands_panel = new JPanel();
    private JPanel button_panel = new JPanel();
    private JPanel name_panel = new JPanel();
    private JLabel nameField = new JLabel();
    String commands;
    private JTextArea textArea;
    private JButton[][] buttons;
    private int interval;
    World world;

    public WorldGui(String title) {

        world = new World(30,20, this);
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(world.getWidth() * 40, world.getHeight() * 40);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setLayout(new BorderLayout());
        interval = 10;
        JMenuBar menuBar = new JMenuBar();
        menu = new JMenu("Menu");
        load = new JMenuItem("Load Game");
        save = new JMenuItem("Save Game");
        load.addActionListener(this);
        save.addActionListener(this);
        menu.add(load);
        menu.add(save);
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);
        frame.setVisible(true);

        nameField.setText("Szymon Malecki 188587 Enter - new turn, arrows - move Human (C) ");
        name_panel.setLayout(new CardLayout());
        name_panel.add(nameField);

        buttons = new JButton[world.getHeight()][world.getWidth()];
        button_panel.setLayout(new GridLayout(world.getHeight(),world.getWidth()));

            commands = Comments.getText();
        textArea = new JTextArea(commands);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFocusable(false);
        JScrollPane sp = new JScrollPane(textArea);

        commands_panel.setLayout(new CardLayout());
        commands_panel.add(sp);

        for(int i = 0; i < world.getHeight(); i++) {
            for(int j = 0; j < world.getWidth(); j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setBackground(Color.white);
                buttons[i][j].setFocusable(false);
                buttons[i][j].addActionListener(this);
                button_panel.add(buttons[i][j]);
            }
        }

        frame.getContentPane().setLayout(null);
        button_panel.setBounds(interval, interval, frame.getWidth() * 3 / 4, frame.getHeight() * 7 / 8);
        name_panel.setBounds(interval, button_panel.getHeight(), button_panel.getWidth(), 5 * interval);
        commands_panel.setBounds(button_panel.getWidth() + interval, interval, frame.getWidth() - interval - button_panel.getWidth(), frame.getHeight() - 100);
        frame.add(button_panel);
        frame.add(name_panel);
        frame.add(commands_panel);
        frame.addKeyListener(this);
        world.createWorld();
        frame.repaint();
    }

    public void drawWorld(int height, int width, Organism organism[][]) {
        frame.repaint();
        if(world.getTurn() != 0) {
            clearWorld(organism);
        }
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                if (organism[i][j] != null) {
                    buttons[i][j].setText(String.valueOf(organism[i][j].getSymbol()));
                    buttons[i][j].setForeground(organism[i][j].getColor());
                    buttons[i][j].setBackground(organism[i][j].getColor());
                    buttons[i][j].setOpaque(true);
                }
            }
        }
        textArea.setText(Comments.getText());
    }

    public void clearWorld(Organism organism[][]) {
        for(int i = 0; i < world.getHeight(); i++) {
            for(int j = 0; j < world.getWidth(); j++) {
                if(organism[i][j] == null) {
                    buttons[i][j].setText("");
                    buttons[i][j].setBackground(Color.white);
                    buttons[i][j].setOpaque(true);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == save) {
            String nameOfFile = JOptionPane.showInputDialog(frame, "Name of file: ");
            world.saveToFile(nameOfFile);
        }

        if(e.getSource() == load) {
            String nameOfFile = JOptionPane.showInputDialog(frame, "Name of file: ");
            world = world.loadGame(nameOfFile);
            world.setGui(this);
            world.createWorld();
            world.printOrganisms();
        }

        for(int i = 0; i < world.getHeight(); i++) {
            for(int j = 0; j < world.getWidth(); j++) {
                if(e.getSource() == buttons[i][j]) {
                    if(buttons[i][j].getText() == "") {
                        OrganismList organismList = new OrganismList(i, j);
                    }
                }
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if(world.getHuman().alive) {
            if (keyCode == KeyEvent.VK_UP) {
                world.getHuman().setKeyCode('w');
                world.nextTurn();
            } else if (keyCode == KeyEvent.VK_DOWN) {
                world.getHuman().setKeyCode('s');
                world.nextTurn();
            } else if (keyCode == KeyEvent.VK_RIGHT) {
                world.getHuman().setKeyCode('d');
                world.nextTurn();
            } else if (keyCode == KeyEvent.VK_LEFT) {
                world.getHuman().setKeyCode('a');
                world.nextTurn();
            } else if (keyCode == KeyEvent.VK_P) {
                if(world.getHuman().getWaitingTime() > 0) {
                        textArea.append("The ability is on cooldown, you have to wait " + world.getHuman().getWaitingTime() + " turn \n");
                } else {
                    world.getHuman().setKeyCode('p');
                    world.nextTurn();
                }
            }
        } else if(keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN ||
                keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_P) {
                String text = "The Human is dead \n";
                textArea.append(text);
        }

        if(keyCode == KeyEvent.VK_ENTER) {
            world.nextTurn();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private class OrganismList extends JFrame {
        private JList list;
        String[] listOfOrganisms;
        private int y;
        private int x;


        public OrganismList(int y, int x) {
            super("Choose Organism");
            this.y = y;
            this.x = x;
            setBounds(400, 200, 100, 250);
            listOfOrganisms = new String[]{"Sosnowski's Hogweed", "Guarana", "Grass", "Deadly Nightshade", "Dandelion",
            "Wolf", "Turtle", "Sheep", "Fox", "Antelope"};
            list = new JList(listOfOrganisms);
            list.setVisibleRowCount(listOfOrganisms.length);
            list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            list.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                   String symbol = getSymbol(list.getSelectedIndex());
                   Organism tmpOrg = world.createOrganism(symbol, x, y, world);
                   world.addOrganism(tmpOrg);
                   drawWorld(world.getHeight(), world.getWidth(), world.getMap());
                   textArea.append("You have created a new Organism: " + tmpOrg.getName());
                   dispose();
                }
            });

            JScrollPane sp = new JScrollPane(list);
            add(sp);

            setVisible(true);
        }
        String getSymbol(int i) {
            switch(i) {
                case 0: return "B";
                case 1: return "G";
                case 2: return "T";
                case 3: return "J";
                case 4: return "M";
                case 5: return "W";
                case 6: return "Z";
                case 7: return "O";
                case 8: return "L";
                case 9: return "A";
            } return null;
        }
    }
}
