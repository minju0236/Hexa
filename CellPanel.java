import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class CellPanel extends JPanel {
    private int shape; // 1:네모, 2:동그라미, 3:세모

    public void setShape(int shape) {
        this.shape = shape;
    }

    public int getShape() {
        return this.shape;
    }

    public void paintComponent (Graphics g) {
        super.paintComponent(g);

        if (shape == 0) {
            //System.out.println(">>>>>>>>>> blank");
        } else if (shape == 1) {
            //System.out.println(">>>>>>>>>> RED");
            g.setColor(Color.RED);
            g.fillRect(10,10,30,30);
        } else if (shape == 2) {
            //System.out.println(">>>>>>>>>> BLUE");
            g.setColor(Color.BLUE);
            g.fillOval(10,10,30,30);
        } else if (shape == 3) {
            //System.out.println(">>>>>>>>>> GREEN");
            int[] xarr = {10, 25, 40};
            int[] yarr = {40, 10, 40};
            g.setColor(Color.GREEN);
            g.fillPolygon(xarr, yarr, 3);
        }

    }
}
