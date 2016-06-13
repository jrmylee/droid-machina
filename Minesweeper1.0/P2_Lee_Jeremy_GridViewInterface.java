import java.awt.image.BufferedImage;

/**
 * Created by jrmylee on 3/10/16.
 */
public interface P2_Lee_Jeremy_GridViewInterface {
    public int boardLength(); // gets the width of the grid (number of columns)
    public int boardHeight(); // gets the height of the grid (number of rows)
    public BufferedImage getImageAt(int x, int y); // returns the image that should be shown at tile (x, y)
}
