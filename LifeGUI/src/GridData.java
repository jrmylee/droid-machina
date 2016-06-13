import java.awt.*;

/**
 * Created by jrmylee on 2/28/16.
 */
public interface GridData {
    public int getWidth();

    public int getHeight();

    public Color getValueAt(int x, int y);

    public void setValueAt(int x,int y,Color val);

    public void onTick();
}
