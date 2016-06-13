/**
 * Created by jrmylee on 2/22/16.
 */


import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
/**
 * Jeremy Lee Period 2 2/23/16
 * This lab took 2 hour 30 minutes
 * THis lab required me to use mouselistener and mousemotionlistener, which was interesting.  I personally like
 * to use MouseAdapter simply because it does not require you to implement and override all of the classes methods,
 * This lab was interesting. It also took a very long time to figure out how to open files.
 */

public class P2_Lee_Jeremy_SimpleDraw {
    public static void main(String[] args) {
        MyGUI gui = new MyGUI();
    }
}

class MyGUI implements ActionListener, MouseListener, MouseMotionListener {
    public Color[][] arr = new Color[20][20];
    // Attributes
    Color color = Color.RED;
    MyDrawingPanel drawingPanel;
    int currRow = -1;
    int currCol = -1;
    JColorChooser colorPanel;
    JButton reset;
    JFrame window;
    JFrame window2;
    JButton win;

    MyGUI() {
        reset = new JButton("Reset");

        win = new JButton("Color Chooser");
        win.addActionListener(this);
        JButton ok = new JButton();
        ok.addActionListener(this);
        window2 = new JFrame("Color Chooser");
        window2.setBounds(200, 100, 500, 500);
        window2.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                window2.dispose();
            }
        });
        window2.setResizable(true);
        colorPanel = new JColorChooser();
        colorPanel.setColor(Color.black);
        window2.add(colorPanel);
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                arr[i][j] = Color.WHITE;
            }
        }
        // Create Java Window
        window = new JFrame("SimpleDraw");
        window.setLayout(new BorderLayout());
        window.setBounds(100, 100, 445, 500);
        window.setResizable(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create GUI elements

        // JPanel to draw in
        drawingPanel = new MyDrawingPanel();
        drawingPanel.setBounds(20, 20, 400, 400);
        drawingPanel.setBorder(BorderFactory.createEtchedBorder());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);


        window.getContentPane().add(drawingPanel);
        JPanel pane = new JPanel();
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 1;
        pane.add(reset, c);
        c.gridy = 2;
        pane.add(win,c);
        reset.addActionListener(this);
        window.getContentPane().add(pane, BorderLayout.SOUTH);
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
                    arr[row][col] = color;
                }
                drawingPanel.repaint();
            } else if (e.getButton() == MouseEvent.BUTTON3) {
                if (row < 20 && col < 20) {
                    arr[row][col] = Color.WHITE;
                }
                drawingPanel.repaint();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getX() >= 0 && e.getY() >= 0 && e.getX() <= drawingPanel.getWidth() && e.getY() <= drawingPanel.getHeight()) {
            color = colorPanel.getColor();
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
                    arr[row][col] = color;
                }
                drawingPanel.repaint();
            } else if (e.getModifiers() == MouseEvent.BUTTON3_MASK) {
                if (row < 20 && col < 20) {
                    arr[row][col] = Color.WHITE;
                }
                drawingPanel.repaint();
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() != null) {
             if (e.getActionCommand().equals("Color Chooser")) {
                window2.setVisible(true);
            }else if(e.getSource() == reset){
                 for(int i = 0; i < arr.length; i++){
                     for(int j = 0; j < arr[0].length; j++){
                         arr[i][j] = color.WHITE;
                     }
                 }
                 drawingPanel.repaint();
             }
        }
    }

    public void clearDraw() {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                arr[i][j] = Color.WHITE;
            }
        }
        drawingPanel.repaint();
        drawingPanel.paintComponent(drawingPanel.getGraphics());
    }

    private class MyDrawingPanel extends JPanel {
        static final long serialVersionUID = 1234567890L;
        Color color = Color.BLACK;
        public void setColor(Color color){
            this.color = color;
        }
        public void paintComponent(Graphics g) {
            g.setColor(Color.WHITE);
            g.fillRect(2, 2, this.getWidth() - 2, this.getHeight() - 2);
            for (int row = 0; row < 20; row++) {
                for (int col = 0; col < 20; col++) {
                    if (arr[row][col] != Color.WHITE) {
                        g.setColor(arr[row][col]);
                        g.fillRect(col * 20, row * 20, (this.getWidth() - 2) / 20, (this.getWidth() - 2) / 20);
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
}