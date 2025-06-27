
package view;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

public class RoundImageLabel extends JLabel {
    private BufferedImage image;

    public RoundImageLabel(BufferedImage image) {
        this.image = image;
        setPreferredSize(new Dimension(50, 50));
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (image == null) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int size = Math.min(getWidth(), getHeight()); // Ensures square crop
        int x = (getWidth() - size) / 2;
        int y = (getHeight() - size) / 2;

        Shape circle = new Ellipse2D.Double(x, y, size, size);
        g2.setClip(circle);
        g2.drawImage(image, x, y, size, size, null);
        g2.dispose();
    }

    public void setImage(BufferedImage image) {
        this.image = image;
        repaint();
    }
}
