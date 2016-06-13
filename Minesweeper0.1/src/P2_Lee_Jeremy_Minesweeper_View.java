import java.util.Scanner;

/**
 * Created by jrmylee on 3/13/16.
 */
public class P2_Lee_Jeremy_Minesweeper_View {
    String command;
    int row;
    int col;

    public String getReplay() {
        return replay;
    }

    public void setReplay(String replay) {
        this.replay = replay;
    }

    private String replay;
    Boolean running;

    P2_Lee_Jeremy_Minesweeper_View() {
        replay = "";
        System.out.println("        _                                                   \n" +
                "  /\\/\\ (_)_ __   ___  _____      _____  ___ _ __   ___ _ __ \n" +
                " /    \\| | '_ \\ / _ \\/ __\\ \\ /\\ / / _ \\/ _ \\ '_ \\ / _ \\ '__|\n" +
                "/ /\\/\\ \\ | | | |  __/\\__ \\\\ V  V /  __/  __/ |_) |  __/ |   \n" +
                "\\/    \\/_|_| |_|\\___||___/ \\_/\\_/ \\___|\\___| .__/ \\___|_|   \n" +
                "                                           |_|              \n");
    }

    public void askUser(Boolean redo, String yesorno) {
        Scanner in = new Scanner(System.in);
        if(!redo) {
            System.out.print("Would you like to flag a cell or reveal a cell?");
            System.out.println();
            System.out.print("Enter 'f' or 'r' > ");
            command = in.next();

            System.out.println();
            System.out.print("Enter row: ");
            row = in.nextInt();
            System.out.print("Enter col: ");
            col = in.nextInt();
        }else{
            System.out.println("You " + yesorno);
            System.out.print("Would you like to play again?(enter 'y' or 'n')");
            replay = in.next();
            System.out.println(replay);
        }
    }
    public void printBoard(String[][] board, int row, int col) {
        for (int r = -1; r < board.length; r++) {
            for (int c = -1; c < board[0].length; c++) {
                if (r == -1) {
                    if (c == -1) {
                        System.out.print("  ");
                    } else {
                        System.out.print(c + " ");
                    }
                } else {
                    if (c == -1) {
                        if (r < 10) {
                            System.out.print(r + " ");
                        } else {
                            System.out.print(r + "");
                        }
                    } else {
                        if (row + col != 0) {
                            if (r == row && c == col) {
                                System.out.print("* ");
                            } else {
                                System.out.print(board[r][c] + " ");
                            }
                        } else {
                            System.out.print(board[r][c] + " ");
                        }
                    }
                }
            }
            System.out.println();
        }
    }

    public void commandEntered(String command) {
        this.command = command;
    }

    public void rowEntered(String x) {
        row = Integer.parseInt(x);
    }

    public void colEntered(String y) {
        col = Integer.parseInt(y);
    }

    public void replayEntered(String replay) {
        this.replay = replay;
    }

    public void setRunning() {
        running = false;
    }
}
