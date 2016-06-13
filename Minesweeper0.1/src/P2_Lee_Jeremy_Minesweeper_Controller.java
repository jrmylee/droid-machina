import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Jeremy Lee on 3/3/16 Period 2.
 * This lab took 1 hour
 * This lab was not that difficult.  I had trouble with the recursion part as I was doing a dumb mistake,
 * but I was able to finish in a reasonable amount of time.  I would assume that add the GUI would not be that
 * difficult, as everything coded is generalized.  The code utilizes MVC which makes it a structured attempt
 * at creating the game.
 *
 * This is a resubmit, I had an issue with the model and the checking of whether the neighbors of the a spot
 * is a bomb, but I was able to fix it.  It was a dumb mistake.
 */
public class P2_Lee_Jeremy_Minesweeper_Controller {

    P2_Lee_Jeremy_Minesweeper_View view;
    P2_Lee_Jeremy_Minesweeper_Model model;
    int numMines;
    int numRows;
    int numCols;
    String[][] board;
    P2_Lee_Jeremy_Minesweeper_Controller(int numMines, int numRows, int numCols) {
        this.numMines = numMines;
        this.numCols = numCols;
        this.numRows = numRows;
        board = new String[numRows][numCols];
        view = new P2_Lee_Jeremy_Minesweeper_View();
        model = new P2_Lee_Jeremy_Minesweeper_Model(numRows, numCols, 99);
        interpret();
        view.printBoard(board, 0, 0);
        run();
    }
    public void reset(){
        view = new P2_Lee_Jeremy_Minesweeper_View();
        model = new P2_Lee_Jeremy_Minesweeper_Model(numRows, numCols, numMines);
        interpret();
        view.printBoard(board, 0, 0);
        run();
    }
    public void interpret(){
        for(int row=0; row < board.length;row++){
            for(int col=0; col < board.length; col++){
                if(model.getBoardVal(row,col).equals("empty")){
                    board[row][col] = "-";
                }else if(model.getBoardVal(row,col).equals("B")){
                    board[row][col] = "*";
                }else{
                    board[row][col] = model.getBoardVal(row,col);
                }
            }
        }
    }
    public void run() {
        while (true) {
            view.askUser(false, "");
            if (view.command.equals("r")) {
                recurse(view.row, view.col);
                interpret();
                if (win()) {
                    view.printBoard(board, 0, 0);
                    view.askUser(true, "Win!");
                    if (view.getReplay().equals("y")) {
                        reset();
                    } else {
                        view.replayEntered("n");
                        break;
                    }
                } else if (lose()) {
                    view.printBoard(board, view.row, view.col);
                    view.askUser(true, "Lose!");
                    if (view.getReplay().equals("y")) {
                        reset();
                    } else if(view.getReplay().equals("n")){
                        break;
                    }
                }
            } else if (view.command.equals("f")) {
                model.setBoard("f", view.row, view.col);
                interpret();
            }
            if(view.getReplay().equals("n")){
                break;
            }else{
                view.printBoard(board, 0, 0);
            }
        }

    }

    public void recurse(int row, int col) {
        model.setBoard(model.getBeneathVal(row, col), row, col);
        if (model.getBeneathVal(row, col).equals(" ")) {
            ArrayList<Point> p = getN(model.getBeneath(), row, col);
            for (int i = 0; i < p.size(); i++) {
                model.setBoard(model.getBeneathVal((int) p.get(i).getX(), (int) p.get(i).getY()), (int) p.get(i).getX(), (int) p.get(i).getY());
                if (model.getBeneathVal((int) p.get(i).getX(), (int) p.get(i).getY()).equals(" ")) {
                    recurse((int) p.get(i).getX(), (int) p.get(i).getY());
                }
            }
        }
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


    public boolean win() {
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

    public boolean lose() {
        boolean boo = false;
        for (int row = 0; row < model.getBoard().length; row++) {
            for (int col = 0; col < model.getBoard()[0].length; col++) {
                if (model.getBeneathVal(row, col).equals("B")) {
                    if (model.getBoardVal(row, col).equals(model.getBeneathVal(row, col))) {
                        boo = true;
                        break;
                    }
                }
            }
        }
        return boo;
    }

    public int getWidth() {
        return model.getBeneath()[0].length;
    }

    public int getHeight() {
        return model.getBeneath().length;
    }

    public String stringAtBeneath(int row, int col) {
        return model.getBeneathVal(row, col);
    }

    public String stringAtBoard(int row, int col) {
        return model.getBoardVal(row, col);
    }


}
