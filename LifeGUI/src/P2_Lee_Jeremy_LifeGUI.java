import javafx.collections.ListChangeListener;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Hashtable;
import java.util.Scanner;

/**
 * Created by Jeremy Lee on 2/28/16 Period 2.
 * This lab took 2 hours
 *  This lab was very fun to write.  It required my SimpleDraw program and some modifications to it.  It was not that
 *  hard to do because I had already wrote most of the code.  I used a Timer to allow the program to run through each generation
 *  with out using a while loop.
 */
public class P2_Lee_Jeremy_LifeGUI implements MouseListener, MouseMotionListener, ActionListener, ChangeListener{
    private MyDrawingPanel drawingPanel;
    private javax.swing.Timer timer;
    Color[][] arr = new Color[20][20];
    int currRow = -1;
    int currCol = -1;
    JColorChooser colorPanel;
    JMenuItem quitItem;
    JMenuItem openItem;
    JMenuItem saveItem;
    JMenuItem reset;
    JFrame window;
    JFrame window2;
    JButton win;

    JButton start;
    JSlider slide;
    JToggleButton cont;
    Color color;
    static Life life;
    int delay = 50;
    JTextArea area;
    JButton ok;
    private long tStart;
    public static void main(String[] args){
        new P2_Lee_Jeremy_LifeGUI();
    }
    P2_Lee_Jeremy_LifeGUI(){
        area = new JTextArea("Generation #: ");
        area.setPreferredSize(new Dimension(150,20));
        ok = new JButton("OK");
        ok.addActionListener(this);

        slide = new JSlider(JSlider.HORIZONTAL, 5, 405, 100);
        slide.setMajorTickSpacing(100);
        Hashtable labeTable = new Hashtable();
        for(int i = 5; i <= 405; i+=100){
            labeTable.put(new Integer(i), new JLabel(i+""));
        }
        slide.setLabelTable(labeTable);
        slide.setPaintTicks(true);
        slide.setPaintLabels(true);
        slide.addChangeListener(this);

        cont = new JToggleButton("Single Step");
        cont.addActionListener(this);
        start = new JButton("Run Life");
        start.addActionListener(this);
        win = new JButton("Color Chooser");
        win.addActionListener(this);

        window2 = new JFrame("Color Chooser");
        window2.setLayout(new BorderLayout());
        window2.setBounds(200, 100, 500, 600);
        window2.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                window2.dispose();
            }
        });
        window2.setResizable(true);
        colorPanel = new JColorChooser();
        colorPanel.setColor(Color.black);
        ok.setPreferredSize(new Dimension(20,20));
        window2.add(colorPanel,BorderLayout.NORTH);
        window2.getContentPane().add(ok, BorderLayout.SOUTH);
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                arr[i][j] = Color.WHITE;
            }
        }
        // Create Java Window
        window = new JFrame("LifeGUI");
        window.setLayout(new BorderLayout());
        window.setBounds(100, 100, 445, 550);
        window.setResizable(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create GUI elements

        // JPanel to draw in
        life = new Life(20,20);
        drawingPanel = new MyDrawingPanel(life);
        drawingPanel.setBounds(20, 20, 400, 400);
        drawingPanel.setBorder(BorderFactory.createEtchedBorder());
        JPanel bot = new JPanel();
        bot.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        c.gridx = 1;
        bot.add(area,c);
        c.gridx = 1;
        c.gridy = 1;
        bot.add(win, c);
        c.gridx = 2;
        bot.add(slide,c);

        c.gridx = 1;
        c.gridy = 2;
        bot.add(start, c);
        c.gridx = 2;
        bot.add(cont, c);
        JPanel mainPanel = new JPanel();

        mainPanel.setLayout(null);

        // create a JMenuBar
        JMenuBar menuBar = new JMenuBar();


        // create a JMenu called "File"
        JMenu fileMenu = new JMenu("File");

        // Create a "Save" JMenuItem
        saveItem = new JMenuItem("Save");

        // add an action listener to save item
        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                life.setColor(color);

                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("PPM FILES", "ppm", ".ppm");
                chooser.setFileFilter(filter);
                String content = "";
                PrintStream out = null;
                int result = chooser.showSaveDialog(window);
                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        out = new PrintStream(chooser.getSelectedFile() + ".ppm");
                        content += "P3 \n";
                        content += "# This is a photo  \n";
                        content += "" + 20 + " " + 20 + "\n";
                        content += "255 \n";
                        for (int row = 0; row < 20; row++) {
                            for (int col = 0; col < 20; col++) {
                                content += life.getValueAt(row, col).getRed() + " " + life.getValueAt(row, col).getGreen() + " " + life.getValueAt(row, col).getBlue() + "  ";
                            }
                            content += "\n";
                        }
                        out.print(content);
                    } catch (IOException i) {
                        i.printStackTrace();
                    }
                }
            }

        });
        fileMenu.add(saveItem);
        JMenuItem openItem = new JMenuItem("Open");
        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                life.setColor(color);

                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("PPM FILES", "ppm", "PPM");
                chooser.setFileFilter(filter);
                int ret = chooser.showOpenDialog(window);
                String type = "";
                if (ret == JFileChooser.APPROVE_OPTION) {
                    try {
                        File file = chooser.getSelectedFile();

                        Scanner in = new Scanner(file);
                        type = in.next();
                        if(!type.equals("P3")){
                            throw new IOException("Not proper PPM format.");
                        }
                        if(in.next().equals("#")) {
                            in.nextLine();
                            in.next();
                            in.next();
                            in.next();
                        }else{
                            in.next();
                            in.next();
                        }

                        int row = 0;
                        int col = 0;
                        while (in.hasNext()) {
                            String n = "";
                            n = in.next();
                            if (n.equals("#")) {
                                in.nextLine();
                            } else {
                                String wid = n;
                                String he = in.next();

                                int R = Integer.parseInt(wid);
                                int G = Integer.parseInt(he);
                                int B = Integer.parseInt(in.next());

                                life.setValueAt(row,col,new Color(R, G, B));
                                if (col < 19) {
                                    col++;
                                } else if (col == 19) {
                                    col = 0;
                                    row++;
                                }

                            }
                            drawingPanel.repaint();
                        }
                    } catch (IOException k) {
                        k.printStackTrace();
                    }
                }
            }
        });
        fileMenu.add(openItem);
        JMenuItem quitItem = new JMenuItem("Quit");
        quitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu.add(quitItem);

        menuBar.add(fileMenu);

        JMenu edit = new JMenu("Edit");
        reset = new JMenuItem("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearDraw();
                life.setGen(0);
            }
        });
        edit.add(reset);
        menuBar.add(edit);

        menuBar.setPreferredSize(new Dimension(100, 20));
        window.getContentPane().add(menuBar, BorderLayout.NORTH);
        window.getContentPane().add(drawingPanel);
        window.getContentPane().add(bot, BorderLayout.SOUTH);
        window.getContentPane().add(mainPanel);

        // Let there be light
        window.setVisible(true);
        drawingPanel.addMouseListener(this);
        drawingPanel.addMouseMotionListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getX() >= 0 && e.getY() >= 0 && e.getX() <= 600 && e.getY() <= 445) {
            color = colorPanel.getColor();
            life.setColor(color);

            int row = e.getY();
            int col = e.getX();

            while (row % 20 != 0) {
                row--;
            }
            while (col % 20 != 0) {
                col--;
            }
            row = row / 20;
            col = col / 20;
            if (e.getButton() == MouseEvent.BUTTON1) {
                if (row < 20 && col < 20) {
                    life.setValueAt(row,col,color);
                }
                drawingPanel.repaint();
            } else if (e.getButton() == MouseEvent.BUTTON3) {
                if (row < 20 && col < 20) {
                    life.setValueAt(row,col,Color.WHITE);
                }
                drawingPanel.repaint();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getX() >= 0 && e.getY() >= 0 && e.getX() <= drawingPanel.getWidth() && e.getY() <= drawingPanel.getHeight()) {
            color = colorPanel.getColor();
            life.setColor(color);
            int row = e.getY();
            int col = e.getX();

            while (row % 20 != 0) {
                row--;
            }
            while (col % 20 != 0) {
                col--;
            }
            row = row / 20;
            col = col / 20;
            if (e.getModifiers() == MouseEvent.BUTTON1_MASK) {
                if (row < 20 && col < 20) {
                    life.setValueAt(row,col,color);
                }
                drawingPanel.repaint();
            } else if (e.getModifiers() == MouseEvent.BUTTON3_MASK) {
                if (row < 20 && col < 20) {
                    life.setValueAt(row,col,Color.WHITE);
                }
                drawingPanel.repaint();
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    public void actionPerformed(ActionEvent e) {
        life.setColor(color);
        if (e.getActionCommand() != null) {
           if (e.getActionCommand().equals("Color Chooser")) {
               window2.setVisible(true);
            }else if(e.getSource().equals(ok)){
               drawingPanel.setColor(colorPanel.getColor());
               drawingPanel.repaint();
           }else if(e.getSource() == start){
               if(start.getText().equals("Run Life")) {
                   if (cont.getText().equals("Single Step")) {
                       life.nextGeneration();
                       drawingPanel.updateData(life);
                       drawingPanel.repaint();
                       area.setText("Generation #: "+ life.getGen());
                   } else if(cont.getText().equals("Continuous")){
                       tStart = System.currentTimeMillis();
                       timer = new javax.swing.Timer(delay, new ActionListener() {
                           @Override
                           public void actionPerformed(ActionEvent e) {
                               drawingPanel.updateData(life);
                               drawingPanel.onTick();

                               area.setText("Generation #: "+ life.getGen());
                           }
                       });
                       start.setText("Stop Life");
                       timer.start();
                   }
               }else if(start.getText().equals("Stop Life")){
                   start.setText("Run Life");
                   timer.stop();
               }
            }else if(e.getSource() == cont){
                if(cont.getText().equals("Single Step")){
                    cont.setText("Continuous");
                }else if(cont.getText().equals("Continuous")){
                    cont.setText("Single Step");
                }
            }
        }
    }

    public void clearDraw() {
        life.clearGrid();
        drawingPanel.repaint();
        area.setText("Generation #: 0");
        drawingPanel.paintComponent(drawingPanel.getGraphics());
    }
    public void setLifeBoard(Color[][] arr){
        for(int row = 0; row < arr.length; row++){
            for(int col = 0; col < arr[0].length;col++){
                life.setValueAt(row, col, arr[row][col]);
            }
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider)e.getSource();
        if (!source.getValueIsAdjusting()) {
            delay = (int)source.getValue();
            timer.setDelay(delay);
        }
    }
}

class Life implements GridData {
    Color[][] grid;
    Color color = Color.black;
    int gen = 0;
    public Life(int w, int h) {
        grid = new Color[w][h];
        for(int row = 0; row < w; row ++){
            for(int col = 0; col < h; col++){
                grid[row][col] = Color.WHITE;
            }
        }
    }

    public int getWidth() {
        return grid.length;
    }

    public int getHeight() {
        return grid[0].length;
    }

    public Color getValueAt(int x, int y) {
        return grid[x][y];
    }
    public void setGen(int gen){
        this.gen = gen;
    }
    public void setValueAt(int x, int y, Color val) {
        grid[x][y] = val;
    }
    public void setColor(Color color){
        this.color = color;
    }
    public void onTick() {
        nextGeneration();
    }
    public Color[][] returnGrid(){
        return grid;
    }
    public void clearGrid(){
        gen = 0;
        for(int row = 0; row < grid.length;row++){
            for(int col = 0; col < grid[0].length;col++){
                grid[row][col] = Color.white;
            }
        }
    }
    public int getGen(){
        return gen;
    }
    public void nextGeneration() {
        gen++;
        Color[][] arr = new Color[grid.length][grid[0].length];
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (grid[row][col] == color) {
                    if (numNeighbors(row, col) == 1 || numNeighbors(row, col) == 0 || numNeighbors(row, col) >= 4) {
                        arr[row][col] = Color.WHITE;
                    } else if (numNeighbors(row, col) == 2 || numNeighbors(row, col) == 3) {
                        arr[row][col] = color;
                    }
                } else {
                    if (numNeighbors(row, col) == 3) {
                        arr[row][col] = color;
                    } else {
                        arr[row][col] = color.WHITE;
                    }
                }
            }
        }
        grid = arr;

    }

    public int numNeighbors(int row, int col) {
        int nums = 0;
        if (row - 1 >= 0 && col - 1 >= 0) {
            if (grid[row - 1][col - 1] != Color.WHITE) {
                nums++;
            }
        }
        if (row - 1 >= 0 && col + 1 < grid[0].length) {
            if (grid[row - 1][col + 1] != Color.WHITE) {
                nums++;
            }
        }
        if (row - 1 >= 0) {
            if (grid[row - 1][col] != Color.WHITE) {
                nums++;
            }
        }
        if (row + 1 < grid.length && col + 1 < grid[0].length) {
            if (grid[row + 1][col + 1] != Color.WHITE) {
                nums++;
            }
        }
        if (row + 1 < grid.length) {
            if (grid[row + 1][col] != Color.WHITE) {
                nums++;
            }
        }
        if (row + 1 < grid.length && col - 1 >= 0) {
            if (grid[row + 1][col - 1] != Color.WHITE) {
                nums++;
            }
        }
        if (col + 1 < grid[0].length) {
            if (grid[row][col + 1] != Color.WHITE) {
                nums++;
            }
        }
        if (col - 1 >= 0) {
            if (grid[row][col - 1] != Color.WHITE) {
                nums++;
            }
        }
        return nums;
    }
}

class MyDrawingPanel extends JPanel {
    GridData dataSource;
    Color penColor = Color.BLACK;

    public MyDrawingPanel(GridData dataSource) {
        this.dataSource = dataSource;
        penColor = Color.black;
    }
    public void updateData(GridData dataSource){
        this.dataSource = dataSource;
        this.repaint();
    }
    public void setColor(Color color){
        penColor = color;
    }
    public void onTick() {
        dataSource.onTick();
        repaint();
    }

    public void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(2, 2, this.getWidth() - 2, this.getHeight() - 2);

        for(int y = 0; y < dataSource.getWidth(); y++){
            for(int x = 0; x < dataSource.getHeight(); x++){
                if (dataSource.getValueAt(x,y) != Color.WHITE) {
                    g.setColor(penColor);
                    g.fillRect(y * 20, x * 20, (this.getWidth()) / 20, (this.getWidth()) / 20);
                }
            }
            g.setColor(Color.WHITE);
        }
        g.setColor(Color.lightGray);
        for (int x = 0; x < this.getWidth(); x += 20)
            g.drawLine(x, 0, x, this.getHeight());
        for (int y = 0; y < this.getHeight(); y += 20)
            g.drawLine(0, y, this.getWidth(), y);
    }
}