import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by jrmylee on 3/13/16.
 */
public class P2_Lee_Jeremy_Minesweeper_Model {
    private String[][] board;
    private String[][] beneath;
    private int col;
    private int row;
    private int numMines;

    P2_Lee_Jeremy_Minesweeper_Model(int row, int col, int numMines){
        this.row = row;
        this.col = col;
        this.numMines = numMines;
        board = new String[row][col];
        beneath = new String[row][col];
        fill(beneath,row,col);
        fillBoard(beneath, row, col, numMines);
        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                board[i][j] = "empty";
            }
        }
    }
    public void fillBoard(String[][] actual, int row, int col, int mines) {
        ArrayList<Point> blacklist = new ArrayList<Point>();
        int mineCount = 0;
        Random rand = new Random();
        int bombR = rand.nextInt(row);
        int bombC = rand.nextInt(col);
        ArrayList<Point> bombs;
        int count = 0;

        while (mineCount < mines) {
            mineCount++;
            actual[bombR][bombC] = "B";
            blacklist.add(new Point(bombR, bombC));
            bombR = rand.nextInt(row);
            bombC = rand.nextInt(col);
            Point temp = new Point(bombR, bombC);
            while (blacklist.contains(temp)) {
                bombR = rand.nextInt(row);
                bombC = rand.nextInt(col);
                temp = new Point(bombR, bombC);
            }
        }

        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                Point p = new Point(r, c);
                if (!blacklist.contains(p)) {
                    bombs = getBombs(actual, r, c);
                    count = bombs.size();
                    if (count > 0) {
                        actual[r][c] = count + "";
                    } else {
                        actual[r][c] = " ";
                    }
                }

            }
        }
    }
    public void fill(String[][] actual, int row, int col) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                actual[i][j] = "j";
            }
        }
    }


    public ArrayList<Point> getBombs(String[][] actual, int row, int col) {
        ArrayList<Point> points = new ArrayList<Point>();
        if (isValid2(actual, row, col + 1)) {
            points.add(new Point(row, col + 1));
        }
        if (isValid2(actual, row + 1, col + 1)) {
            points.add(new Point(row + 1, col + 1));
        }
        if (isValid2(actual, row + 1, col)) {
            points.add(new Point(row + 1, col));
        }
        if (isValid2(actual, row + 1, col - 1)) {
            points.add(new Point(row + 1, col - 1));
        }
        if (isValid2(actual, row, col - 1)) {
            points.add(new Point(row, col - 1));
        }
        if (isValid2(actual, row - 1, col - 1)) {
            points.add(new Point(row - 1, col - 1));
        }
        if (isValid2(actual, row - 1, col)) {
            points.add(new Point(row - 1, col));
        }
        if (isValid2(actual, row - 1, col + 1)) {
            points.add(new Point(row - 1, col + 1));
        }
        return points;
        /*
        ArrayList<Point> points = new ArrayList<Point>();
        for(int rowCount = -1;rowCount<2;rowCount++){
            for(int colCount = -1; colCount<2;colCount++){
                if(isValid(actual, row + rowCount, row + colCount)){
                    points.add(new Point(row + rowCount, row + colCount));
                }
            }
        }
        return points;
        */
    }

    public boolean isValid2(String[][] table, int row, int col) {
        if (row < 0 || col < 0 || row >= table.length || col >= table[0].length) {
            return false;
        } else
        if (table[row][col].equals("B")) {
            return true;
        } else {
            return false;
        }
    }

    public String[][] getBoard(){
        return board;
    }

    public String[][] getBeneath() {
        return beneath;
    }
    public String getBeneathVal(int row, int col){
        return beneath[row][col];
    }
    public String getBoardVal(int row, int col){
        return board[row][col];
    }
    public void setBeneath(String val, int row, int col) {
        beneath[row][col] = val;
    }

    public void setBoard(String val, int row, int col) {
        board[row][col] = val;
    }
}
