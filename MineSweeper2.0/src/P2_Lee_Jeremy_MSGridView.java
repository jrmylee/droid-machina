import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by jrmylee on 3/9/16.
 */
public class P2_Lee_Jeremy_MSGridView extends JPanel {
    Color penColor = Color.BLACK;
    String[][] arr;
    P2_Lee_Jeremy_GridViewInterface controller;
    P2_Lee_Jeremy_MSGridView(){
        this.controller = null;
    }
    public void setController(P2_Lee_Jeremy_GridViewInterface controller) {
        this.controller = controller;
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(2, 2, this.getWidth() - 2, this.getHeight() - 2);
        if(controller != null) {
            for (int y = 0; y < 20; y++) {
                for (int x = 0; x < 20; x++) {
                    g.drawImage(controller.getImageAt(y, x), x * 20, y * 20, this.getWidth() / 20, this.getHeight() / 20, null);
                }
            }
        }
        g.setColor(Color.lightGray);
        for (int x = 0; x < this.getWidth(); x += 20)
            g.drawLine(x, 0, x, this.getHeight());
        for (int y = 0; y < this.getHeight(); y += 20)
            g.drawLine(0, y, this.getWidth(), y);
    }


}
