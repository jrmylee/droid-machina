import java.awt.*;
import java.util.ArrayList;

/**
 * Created by jrmylee on 3/16/16.
 */
public interface P2_Lee_Jeremy_AIInterface {
    public void setBoard(String command, int row, int col);
    public int boardLength();
    public int boardHeight();
    public String getStateAt(int row, int col);
    public void recurse(int row, int col);
    public ArrayList<Point> getNotBombs();
}
