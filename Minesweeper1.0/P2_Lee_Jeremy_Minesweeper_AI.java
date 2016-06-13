import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Jeremy Lee Period 2 March 21 2016
 * this lab took 2 hours
 *  this lab was interesting.  The end result is so cool because it is actually able to beat the minesweeper game
 *  given that only about 20 mines are on the screen(consistently).  This lab was not TOO difficult but was still
 *  fairly challenging.
 */
public class P2_Lee_Jeremy_Minesweeper_AI {
    P2_Lee_Jeremy_AIInterface control;
    private String state;
    int row;
    int col;
    int count;
    P2_Lee_Jeremy_Minesweeper_AI(int row, int col){
        state = "USER_MODE";
        this.row = row;
        this.col = col;
        count = 0;
    }
    public void setControl(P2_Lee_Jeremy_AIInterface control){
        this.control = (control);
    }
    public void setState(String state) {
        this.state = state;
    }
    public void run(){
        Random rand = new Random();
        if(state.equals("GUESS_MODE")){
            int randRow = rand.nextInt(row);
            int randCol = rand.nextInt(col);
            if(count == 0) {
                ArrayList<Point> list = control.getNotBombs();

                if (list.size() > 0) {
                    while (!list.contains(new Point(randRow, randCol))) {
                        randRow = rand.nextInt(row);
                        randCol = rand.nextInt(col);
                    }
                }
            }else{
                if(areBlanks()) {
                    while (!control.getStateAt(randRow, randCol).equals("BLANK")) {
                        randRow = rand.nextInt(row);
                        randCol = rand.nextInt(col);
                    }
                }
            }
            control.recurse(randRow,randCol);
            if(possible("FLAG")){
                state = "FLAG_MODE";
            }else if(possible("REVEAL")){
                state = "REVEAL_NEIGHBORS_MODE";
            }
        }else if(state.equals("FLAG_MODE")){
            for(int z = 0; z < control.boardHeight(); z++){
                for(int a = 0; a < control.boardLength(); a++){
                    if(isNumber(z,a)){
                        int num = Integer.parseInt(control.getStateAt(z,a));
                        if(num == countNum(z,a, "BLANK")){
                            Boolean boo = true;
                            for(int i = -1; i <= 1; i++){
                                for(int j = -1; j <= 1; j++){
                                    if(isValid(z+i,a+j)) {
                                        if(isNumber(z+i,a+j)&&isSatisfied(z+i,a+j)){
                                            boo = false;
                                        }
                                    }
                                }
                            }
                            if(boo) {
                                flagUncovered(z, a);
                            }
                        }
                    }
                }
            }
            if(possible("REVEAL")){
                state = "REVEAL_NEIGHBORS_MODE";
            }else{
                state = "GUESS_MODE";
            }
        }else if(state.equals("REVEAL_NEIGHBORS_MODE")){
            for(int r = 0; r < control.boardHeight(); r++){
                for(int c = 0; c < control.boardLength(); c++){
                    if(isNumber(r,c)){
                        int num = Integer.parseInt(control.getStateAt(r,c));
                        if(num == countNum(r,c,"FLAG")){
                            uncover(r,c);
                        }
                    }
                }
            }
            if(possible("FLAG")){
                state = "FLAG_MODE";
            }    else{
                state = "GUESS_MODE";
            }
        }else{

        }
        count++;
    }
    public boolean areBlanks(){
        for(int row = 0; row < control.boardHeight();row++){
            for(int col = 0; col < control.boardLength();col++){
                if(control.getStateAt(row,col).equals("BLANK")){
                    return true;
                }
            }
        }
        return false;
    }
    public boolean possible(String str){
        if(str.equals("FLAG")){
            for(int row = 0; row < control.boardHeight();row++){
                for(int col = 0; col < control.boardLength();col++){
                    Boolean boo = true;
                    if(isNumber(row,col)){
                        int num = Integer.parseInt(control.getStateAt(row,col));
                        if(num == countNum(row,col,"BLANK")){
                            for(int i = -1; i <= 1; i++){
                                for(int j = -1; j <= 1; j++){
                                    if(isValid(row+i,col+j)) {
                                        if(isNumber(row+i,col+j)&&isSatisfied(row+i,col+j)){
                                            boo = false;
                                        }
                                    }
                                }
                            }
                            if(boo){
                                return true;
                            }
                        }
                    }
                }
            }
        }else if(str.equals("REVEAL")){
            for(int r = 0; r < control.boardHeight(); r++){
                for(int c = 0; c < control.boardLength(); c++){
                    if(isNumber(r,c)){
                        int num = Integer.parseInt(control.getStateAt(r,c));
                        if(num == countNum(r,c,"FLAG")){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    public boolean isNumber(int row, int col){
        Boolean boo = true;
        if(control.getStateAt(row,col).equals("BLANK")){
            boo = false;
        }else if(control.getStateAt(row,col).equals("DEAD")){
            boo = false;
        }else if(control.getStateAt(row,col).equals("FLAG")){
            boo = false;
        }else if(control.getStateAt(row,col).equals(" ")){
            boo = false;
        }
        return boo;
    }

    public boolean isValid(int row, int col) {
        if (row < 0 || col < 0 || row >= control.boardHeight() || col >= control.boardLength()) {
            return false;
        }
        return true;
    }
    public int getNumberFlags(int row, int col){
        int count = 0;
        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                if(isValid(row+i,col+j)) {
                    if(control.getStateAt(row+i,col+j).equals("FLAG")){
                        count++;
                    }
                }
            }
        }
        return count;
    }
    public boolean isSatisfied(int row, int col){
        int bombs = getNumberFlags(row,col);
        int num = Integer.parseInt(control.getStateAt(row,col));
        if(bombs  == num){
            return true;
        }else{
            return false;
        }
    }
    public int countNum(int row, int col, String str){
        int count = 0;
        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                if(isValid(row+i,col+j)) {
                    if(control.getStateAt(row+i,col+j).equals(str)){
                        count++;
                    }
                }
            }
        }
        return count;
    }
    public void flagUncovered(int row, int col){
        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                if(isValid(row+i,col+j)) {
                    if(control.getStateAt(row+i,col+j).equals("BLANK")){
                        control.setBoard("FLAG",row+i,col+j);
                    }
                }
            }
        }
    }
    public void uncover(int row, int col){
        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                if(isValid(row+i,col+j)) {
                    if(!control.getStateAt(row+i,col+j).equals("FLAG")){
                        control.recurse(row+i,col+j);
                    }
                }
            }
        }
    }

}
