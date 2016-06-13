import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;

/**
 * Created by jrmylee on 3/9/16.
 */
public class P2_Lee_Jeremy_MSView{
    public JMenuItem newGame;
    public JMenuItem exit;
    public JMenuItem numMines;
    public JMenuItem how;
    public JMenuItem about;
    public JTextField elapsedTime;
    public JTextField minesRemaining;
    public JFrame win;
    public String[][] board;
    public JFrame window2;
    public JTextField numMiness;
    public JLabel numMi;
    public JButton apply;
    public JFrame window3;
    public JFrame window4;
    public JEditorPane helpContent;
    public JScrollPane helpPane;
    public JScrollPane aboutPane;
    public JEditorPane aboutContent;
    public JFrame gamesOver;
    public JLabel gameOverTxt;
    public JMenuItem AI;
    public Timer timer;
    public int count;
    public JButton OK;
    P2_Lee_Jeremy_MinesweeperController control;
    P2_Lee_Jeremy_MSGridView drawingPanel;
    public void initiate(P2_Lee_Jeremy_MinesweeperController control){
        setGridViewController(control);
    }
    P2_Lee_Jeremy_MSView(){
        drawingPanel = new P2_Lee_Jeremy_MSGridView();
        count = 0;
        win = new JFrame("Minesweeper");
        win.setLayout(new BorderLayout());
        win.setBounds(100,100,445, 550);
        win.setResizable(true);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.addWindowFocusListener(new WindowAdapter() {
            public void windowGainedFocus(WindowEvent e) {
                win.requestFocusInWindow();
            }
        });
        win.requestFocusInWindow();
        elapsedTime = new JTextField("Time Elapsed: ");
        minesRemaining = new JTextField("Mines Remaining: 70");
        elapsedTime.setPreferredSize(new Dimension(200,20));
        minesRemaining.setPreferredSize(new Dimension(150,20));
        JPanel fields = new JPanel();
        fields.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        fields.add(elapsedTime, c);
        c.gridx = 1;
        fields.add(minesRemaining, c);


        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        newGame = new JMenuItem("New Game");
        exit = new JMenuItem("Exit");
        fileMenu.add(newGame);
        fileMenu.add(exit);

        JMenu format = new JMenu("Format");
        numMines = new JMenuItem("Set Number of Mines");
        AI = new JMenuItem("AI Mode");
        format.add(numMines);
        format.add(AI);
        gamesOver = new JFrame("Games Over");
        gamesOver.setBounds(100,100,150,150);
        OK = new JButton("OK");
        gamesOver.getContentPane().setLayout(new GridBagLayout());

        gamesOver.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                window2.dispose();
            }
        });
        gameOverTxt = new JLabel();
        GridBagConstraints z = new GridBagConstraints();
        z.gridy = 1;
        gamesOver.getContentPane().add(gameOverTxt,z);
        z.gridy = 2;
        gamesOver.getContentPane().add(OK,z);
        JMenu help = new JMenu("Help");
        how = new JMenuItem("How to Play");
        about = new JMenuItem("About");
        help.add(how);
        help.add(about);
        JPanel mainPanel = new JPanel();
        numMiness = new JTextField("70");
        numMiness.setPreferredSize(new Dimension(250,50));
        apply = new JButton("Apply");
        apply.setPreferredSize(new Dimension(50,50));
        window2 = new JFrame("Enter Number of Mines");
        window2.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.gridy = 0;
        window2.setBounds(100,100,350,350);
        window2.getContentPane().add(numMiness,g);
        g.gridy = 1;
        window2.getContentPane().add(apply,g);
        window2.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                window2.dispose();
            }
        });
        //window3.setBounds(100,100,350,350);
        try {
            helpContent = new JEditorPane(new URL("file:///Users/jrmylee/IdeaProjects/MinesweeperGUI/help.html"));
            helpPane = new JScrollPane(helpContent);
        }catch(java.net.MalformedURLException a){
            a.printStackTrace();
        }catch(IOException i){
            i.printStackTrace();
        }
        try {
            aboutContent = new JEditorPane(new URL("file:///Users/jrmylee/IdeaProjects/MinesweeperGUI/About.html"));
            aboutPane = new JScrollPane(aboutContent);
        }catch(java.net.MalformedURLException a){
            a.printStackTrace();
        }catch(IOException i){
            i.printStackTrace();
        }

        mainPanel.setLayout(null);

        menuBar.add(fileMenu);
        menuBar.add(format);
        menuBar.add(help);


        menuBar.setPreferredSize(new Dimension(100,20));
        drawingPanel.setBounds(20, 20, 400, 400);
        drawingPanel.setBorder(BorderFactory.createEtchedBorder());
        win.getContentPane().add(drawingPanel);
        win.getContentPane().add(menuBar,BorderLayout.NORTH);
        win.getContentPane().add(fields, BorderLayout.SOUTH);
        win.getContentPane().add(mainPanel);
        win.setVisible(true);
        timeElapsed();
    }
    public void timeElapsed(){
        ActionListener ac = new ActionListener(){
            public void actionPerformed(ActionEvent actionEvent){
                count++;
                elapsedTime.setText("Time Elapsed: " + count + "sec");
            }
        };
        timer = new Timer(1000, ac);
        timer.start();
    }
    public void setGridViewController(P2_Lee_Jeremy_GridViewInterface controller) {
        drawingPanel.setController(controller);
        updateGridView();
    }

    // adds a mouse listener to the gridView
    public void setMouseListener(MouseListener listener) {
        drawingPanel.addMouseListener(listener);
    }
    public void setAIMenuItem(String str){
        AI.setText(str);
    }
    // Adds an action listener (
    public void setActionListener(ActionListener listener) {
        newGame.addActionListener(listener);
        exit.addActionListener(listener);
        AI.addActionListener(listener);
        numMines.addActionListener(listener);
        how.addActionListener(listener);
        about.addActionListener(listener);
        apply.addActionListener(listener);
        OK.addActionListener(listener);
    }
    public void setKeyListener(KeyListener key){
        win.addKeyListener(key);
    }
    public void requestFocus(){
        window2.requestFocus();
    }
    public void updateGridView() {
        drawingPanel.repaint();
    }
}
