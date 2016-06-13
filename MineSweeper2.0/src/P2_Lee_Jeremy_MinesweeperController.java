import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 
 */
public class P2_Lee_Jeremy_MinesweeperController implements P2_Lee_Jeremy_GridViewInterface, ActionListener, MouseListener, KeyListener, P2_Lee_Jeremy_AIInterface {
    private final String fileMenu = "File";
    private final String exit = "Exit";
    private final String newGame = "New Game";
    private final String format = "Format";
    private final String help = "Help";
    private final String about = "About";
    private final String howToPlay = "How to Play";

    private int numRows;
    private int numCols;
    private int numMines;
    private P2_Lee_Jeremy_MSView view;
    private P2_Lee_Jeremy_MinesweeperModel model;
    private HashMap<String, BufferedImage> map;
    private BufferedImage[][] buff;
    private boolean gameOver;
    private int MinesLeft;
    private P2_Lee_Jeremy_Minesweeper_AI ai;
    private boolean aiRan;
    P2_Lee_Jeremy_MinesweeperController(int numRows, int numCols, int numMines) {
        aiRan = false;
        gameOver =false;
        this.numRows = numRows;
        this.numCols = numCols;
        ai = new P2_Lee_Jeremy_Minesweeper_AI(numRows,numCols);
        view = new P2_Lee_Jeremy_MSView();
        view.initiate(this);
        ai.setControl(this);
        view.setKeyListener(this);
        this.numMines = Integer.parseInt(view.numMiness.getText());

        buff = new BufferedImage[numRows][numCols];
        model = new P2_Lee_Jeremy_MinesweeperModel(numCols, numCols, numMines);
        map = new HashMap<String, BufferedImage>();
        run();
        MinesLeft = Integer.parseInt(view.numMiness.getText());

        String[] arr = {"blank.gif", "bomb_death.gif","bomb_flagged.gif","bomb_question.gif","bomb_revealed.gif","bomb_wrong.gif",
                "face_dead.gif","face_ooh.gif","face_smile.gif","face_win.gif","num_0.gif","num_1.gif","num_2.gif",
                "num_3.gif","num_4.gif","num_5.gif","num_6.gif","num_7.gif","num_8.gif"};
        for(int i = 0; i < arr.length; i++){
            try{
                map.put(arr[i], ImageIO.read(new File(arr[i])));
            }catch(IOException j){
                j.printStackTrace();
            }
        }
    }
    public void reset(){
        aiRan = false;
        view.count = 0;
        view.timer.start();
        model = new P2_Lee_Jeremy_MinesweeperModel(numRows,numCols,Integer.parseInt(view.numMiness.getText()));
        MinesLeft = Integer.parseInt(view.numMiness.getText());
        interpret();
        view.updateGridView();
    }
    public void interpret() {
        for (int row = 0; row < buff.length; row++) {
            for (int col = 0; col < buff[0].length; col++) {
                if (model.getBoardVal(row, col).equals("-")) {
                    buff[row][col] = map.get("blank.gif");
                } else if(model.getBoardVal(row,col).equals("?")){
                    buff[row][col] = map.get("bomb_question.gif");
                }else if (model.getBoardVal(row, col).equals("B")) {
                    buff[row][col] = map.get("bomb_death.gif");
                } else if (model.getBoardVal(row, col).equals("F")) {
                    buff[row][col] = map.get("bomb_flagged.gif");
                } else {
                    String str = model.getBoardVal(row,col);
                    if(str.equals(" ")){
                        buff[row][col] = map.get("num_0.gif");
                    }else if(str.equals("1")){
                        buff[row][col] = map.get("num_1.gif");
                    }else if(str.equals("2")){
                        buff[row][col] = map.get("num_2.gif");
                    }else if(str.equals("3")){
                        buff[row][col] = map.get("num_3.gif");
                    }else if(str.equals("4")){
                        buff[row][col] = map.get("num_4.gif");
                    }else if(str.equals("5")){
                        buff[row][col] = map.get("num_5.gif");
                    }else if(str.equals("6")){
                        buff[row][col] = map.get("num_6.gif");
                    }else if(str.equals("7")){
                        buff[row][col] = map.get("num_7.gif");
                    }
                }
            }
        }
    }

    public BufferedImage[][] getBuff() {
        return buff;
    }

    public void run() {
        view.setActionListener(this);
        view.setMouseListener(this);
        interpret();
        view.updateGridView();
    }
    public void lose(){
        for(int row = 0; row < model.getBeneath().length;row++){
            for(int col = 0; col < model.getBeneath()[0].length;col++){
                if(model.getBeneathVal(row,col).equals("B")&&!model.getBoardVal(row,col).equals("B")){
                    buff[row][col] = map.get("bomb_revealed.gif");
                }
            }
        }
        view.updateGridView();
        view.gameOverTxt.setText("You Lose");
        view.timer.stop();
        view.gamesOver.setVisible(true);
    }
    public void win(){
        if(winBoo()){
            if(!aiRan) {
                view.gameOverTxt.setText("You Win");
            }else{
                view.gameOverTxt.setText("You Win(You Cheated!)");
            }
            view.gamesOver.setVisible(true);
        }
    }
    public boolean winBoo() {
        boolean boo = true;
        for (int row = 0; row < model.getBeneath().length; row++) {
            for (int col = 0; col < model.getBeneath()[0].length; col++) {
                if (model.getBeneath()[row][col].equals("B")) {

                } else {
                    if (!model.getBeneathVal(row, col).equals(model.getBoardVal(row, col))) {
                        boo = false;
                        break;
                    }
                }
            }
        }
        return boo;
    }
    public void recurse(int row, int col) {
        model.setBoard(model.getBeneathVal(row, col), row, col);
        if(model.getBeneathVal(row, col).equals("B")){
            lose();
        }

        if (model.getBeneathVal(row, col).equals(" ")) {
            ArrayList<Point> p = getN(model.getBeneath(), row, col);
            for (int i = 0; i < p.size(); i++) {
                model.setBoard(model.getBeneathVal((int) p.get(i).getX(), (int) p.get(i).getY()), (int) p.get(i).getX(), (int) p.get(i).getY());
                if (model.getBeneathVal((int) p.get(i).getX(), (int) p.get(i).getY()).equals(" ")) {
                    recurse((int) p.get(i).getX(), (int) p.get(i).getY());
                }
            }
        }
        interpret();
        win();
    }

    public ArrayList<Point> getN(String[][] actual, int row, int col) {
        ArrayList<Point> arr = new ArrayList<Point>();

        for (int rowNum = -1; rowNum < 2; rowNum++) {
            for (int colNum = -1; colNum < 2; colNum++) {
                if (isValid(actual, row + rowNum, col + colNum)) {
                    arr.add(new Point(row + rowNum, col + colNum));
                }
            }
        }
        return arr;
    }

    public boolean isValid(String[][] table, int row, int col) {
        if (row < 0 || col < 0 || row >= table.length || col >= table[0].length) {
            return false;
        } else {
            if (!table[row][col].equals("B") && !model.getBoardVal(row, col).equals(" ")) {
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public String getStateAt(int row, int col){
        if(model.getBoardVal(row,col).equals("-")){
            return "BLANK";
        }else if(model.getBoardVal(row,col).equals("B")){
            return "DEAD";
        }else if(model.getBoardVal(row,col).equals("F")||model.getBoardVal(row,col).equals("?")){
            return "FLAG";
        }else if(model.getBoardVal(row,col).equals(" ")){
            return " ";
        }else{
            return model.getBoardVal(row,col);
        }
    }
    @Override
    public void setBoard(String command, int row, int col){
        if(command.equals("REVEAL")){
            model.setBoard(model.getBeneathVal(row,col),row,col);
        }else if(command.equals("FLAG")){
            model.setBoard("F",row,col);
        }
        view.updateGridView();
    }
    @Override
    public BufferedImage getImageAt(int row, int col) {
        interpret();
        return buff[row][col];
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(exit)) {
            System.exit(0);
        } else if (e.getActionCommand().equals(newGame)) {
            reset();
            view.minesRemaining.setText("Mines Remaining: " + MinesLeft);
        } if (e.getActionCommand().equals(about)) {
            JOptionPane.showMessageDialog(null, view.aboutPane, "About", JOptionPane.PLAIN_MESSAGE, null);

        } else if (e.getActionCommand().equals(howToPlay)) {
            JOptionPane.showMessageDialog(null, view.helpPane, "How To Play", JOptionPane.PLAIN_MESSAGE, null);

        }else if(e.getActionCommand().equals("Set Number of Mines")){
            view.window2.setVisible(true);
        }else if(e.getActionCommand().equals("Apply")){
            reset();
            view.minesRemaining.setText("Mines Remaining: " + MinesLeft);
        }else if(e.getActionCommand().equals("AI Mode")){
            ai.setState("GUESS_MODE");
            view.setAIMenuItem("User Mode");
        }else if(e.getActionCommand().equals("User Mode")){
            ai.setState("USER MODE");
            view.setAIMenuItem("AI Mode");

        }else if(e.getActionCommand().equals("OK")){
            view.gamesOver.dispose();
        }
        view.requestFocus();
    }
    @Override
    public int boardLength(){
        return numRows;
    }
    @Override
    public int boardHeight(){
        return numCols;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getX() >= 0 && e.getY() >= 0 && e.getX() <= 400 && e.getY() <= 400) {
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
                    recurse(row, col);
                }
                interpret();
                view.updateGridView();
            } else if (e.getButton() == MouseEvent.BUTTON3) {
                if (row < 20 && col < 20) {
                    if(model.getBoardVal(row,col).equals("-")){
                        model.setBoard("F",row,col);
                        MinesLeft--;
                    }else if(model.getBoardVal(row,col).equals("F")){
                        model.setBoard("?",row,col);
                        MinesLeft++;
                    }else if(model.getBoardVal(row,col).equals("?")){
                        model.setBoard("-",row,col);
                    }
                    view.minesRemaining.setText("Mines Remaining: " + MinesLeft);
                }
                interpret();
                view.updateGridView();
            }
        }
        view.requestFocus();
    }
    public ArrayList<Point> getNotBombs(){
        ArrayList<Point> arr = new ArrayList<>();
        for(int row = 0; row < model.getBeneath().length;row++){
            for(int col = 0; col < model.getBeneath()[0].length;col++){
                if(!model.getBeneathVal(row,col).equals("B") && model.getBoardVal(row,col).equals("-")){
                    arr.add(new Point(row,col));
                }
            }
        }
        return arr;
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
    public void keyTyped(KeyEvent e) {
        if(e.getKeyChar() == 'a'){
            ai.run();
            aiRan = true;
        }
        view.updateGridView();
    }
    @Override
    public void keyPressed(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
}
