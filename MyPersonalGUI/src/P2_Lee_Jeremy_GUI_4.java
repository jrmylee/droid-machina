import javafx.collections.ListChangeListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

/**
 * Jeremy Lee Period 2 February 21 2015
 * This lab took 1 hour
 * This lab was very fun to make.  I tried a multitude of things to make the GUI very user friendly, but all in all I did not have any trouble
 * making this program.  It was very intuitive and fun.
 */
public class P2_Lee_Jeremy_GUI_4 implements ActionListener, ChangeListener{
    private static JFrame window;
    JButton b;
    JToggleButton b2 = new JToggleButton("Background");

    JColorChooser colorChooser;
    JPanel subPanel1;
    Color color = Color.BLACK;
    MySketchPad pad;
    JTextField txt;
    JSlider s;
    JButton name;
    public static void main(String[] args){
        P2_Lee_Jeremy_GUI_4 gui_4 = new P2_Lee_Jeremy_GUI_4();
    }
    P2_Lee_Jeremy_GUI_4(){
        window = new JFrame("Material Design Goodness");

        String plafName = UIManager.getSystemLookAndFeelClassName();
        try
        {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        }
        catch (Exception ex) {
            System.out.println("*** " + plafName + " PLAF not installed ***");
        }

        window.setBounds(500,300, 1280, 500);
        window.getContentPane().setLayout(new BorderLayout());
        window.setDefaultCloseOperation(window.EXIT_ON_CLOSE);

        subPanel1 = new JPanel();
        JPanel subPanel2 = new JPanel();
        JPanel subPanel3 = new JPanel();
        txt = new JTextField();
        txt.setPreferredSize(new Dimension(145, 25));

        subPanel2.setLayout(new GridBagLayout());
        subPanel3.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        GridBagConstraints c2 = new GridBagConstraints();

        s = new JSlider(JSlider.HORIZONTAL, 5, 20, 7);
        s.setMajorTickSpacing(5);
        Hashtable labeTable = new Hashtable();
        for(int i = 5; i <= 20; i+=5){
            labeTable.put(new Integer(i), new JLabel(i+""));
        }
        s.setLabelTable(labeTable);
        s.setPaintTicks(true);
        s.setPaintLabels(true);
        s.addChangeListener(this);

        b = new JButton("Update Drawing");
        JLabel slide = new JLabel("Line Thickness Slider");
        JLabel textBox = new JLabel("Enter your name: ");
        name = new JButton("Update Name");
        name.addActionListener(this);
        b.addActionListener(this);
        b2.addActionListener(this);
        JLabel b2label = new JLabel("Set Color for: ");
        colorChooser = new JColorChooser();
        c2.gridy = 0;
        subPanel3.add(colorChooser,c2);
        c2.gridy = 1;
        subPanel3.add(b2label, c2);
        c2.gridy = 2;
        subPanel3.add(b2,c2);
        c2.gridy = 3;
        subPanel3.add(b,c2);


        c.gridy = 0;
        subPanel2.add(slide, c);
        c.gridy = 1;
        subPanel2.add(s,c);
        c.gridy = 2;
        subPanel2.add(textBox, c);
        c.gridy = 3;
        subPanel2.add(txt, c);
        c.gridy = 4;
        subPanel2.add(name,c);

        pad = new MySketchPad();
        window.getContentPane().add(pad, BorderLayout.CENTER);
        window.getContentPane().add(subPanel2, BorderLayout.WEST);
        window.getContentPane().add(subPanel3,BorderLayout.EAST);
        window.setVisible(true);

    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == b){
            color = colorChooser.getColor();
            if(b2.getText().equals("Background")){
                pad.setBack(color);
            }else if(b2.getText().equals("Circle")){
               pad.setColor(color);
            }else{
                pad.setTxtC(color);
            }
            pad.repaint();
        }else if(e.getSource() == b2){
            if(b2.getText().equals("Background")) {
                b2.setText("Circle");
            }else if(b2.getText().equals("Circle")){
                b2.setText("Text");
            }else{
                b2.setText("Background");
            }
        }else if(e.getSource() == name){
            if(!txt.getText().equals("")){
                pad.setName(txt.getText());
            }
            pad.repaint();
        }
    }
    public void stateChanged(ChangeEvent e){
        JSlider source = (JSlider)e.getSource();
        if (!source.getValueIsAdjusting()) {
           pad.setSize((int)source.getValue());
        }
        pad.repaint();
    }
}
class MySketchPad extends JPanel {

    public Color c = Color.BLACK;
    public Color back = Color.WHITE;
    public int size = 1;
    public String name = "Jim";
    public Color txtC = Color.BLACK;
    public void setColor(Color color){
        c = color;
    }
    public void setBack(Color coloe){
        back = coloe;
    }
    public void setSize(int size){
        this.size = size;
    }
    public void setTxtC(Color color){
        txtC = color;
    }
    public void setName(String name){
        this.name = name;
    }
    @Override
    protected void paintComponent(Graphics g) {

        // Ask our parent to paint itself
        super.paintComponent(g);

        // Set our background color to white
        this.setBackground(back);

        // Next, cast the Graphics parameter back into what
        // it really is - a more powerful Graphics2D object.
        // Or, if you want, you can leave it as a Graphics
        // and only use Graphics class methods.
        Graphics2D g2 = (Graphics2D)g;
        Stroke s = new BasicStroke(size);
        g2.setStroke(s);
        g2.setColor(c);
        // Finally, draw stuff
        g2.drawArc(getWidth()/2-50, getHeight()/2, 100, 100, 180, 180);
        g2.drawLine(getWidth()/2-50, getHeight()/2+50, getWidth()/2+50, getHeight()/2+50);

        g2.drawLine(getWidth()/2 - 30, getHeight()/2 + 10, getWidth()/2 - 30, getHeight()/2 - 50);
        g2.drawLine(getWidth()/2 + 30, getHeight()/2 + 10, getWidth()/2 + 30, getHeight()/2 - 50);
        Font newF = new Font("Impact", Font.PLAIN, 50);
        g2.drawOval(getWidth()/2-150, getHeight()/2-130, 300, 300);

        g2.setFont(newF);
        FontMetrics metrics = g2.getFontMetrics(newF);
        int w = metrics.stringWidth("This is "+ name);
        g2.setColor(txtC);
        g2.drawString("This is "+name, getWidth()/2 - w/2, getHeight()/5);
    }
}
