package thibault.kuraima.core.awt.components.buttons;

import thibault.kuraima.core.awt.application.AppAwt;
import thibault.kuraima.core.awt.components.shapes.ShapeAwt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class ShapeButton extends Button{

    public ShapeAwt shape;

    public ShapeButton(String text, ShapeAwt awtShape, AppAwt appAwt) {
        super();
        this.shape = awtShape;
        int width = 28;
        int height = 28;
        ShapeAwt s = (ShapeAwt) shape.copy();
        setPreferredSize(new Dimension(width, height));
        double aspectRatio = s.size().getX() / s.size().getY();
        double newWidth = height * aspectRatio;
        Point2D oldPos = s.position();
        Point2D newPos = new Point(width / 2, height / 2);
        Point2D diff = new Point2D.Double(newPos.getX() - oldPos.getX(), newPos.getY() - oldPos.getY());
        s.translate(diff);
        s.size(new Point2D.Double(newWidth, height));
        s.unSelect();
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        s.draw(g2d);
        g2d.dispose();
        ImageIcon icon = new ImageIcon(image);
        setIcon(icon);

        repaint();
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                appAwt.drawingPanel.addShape(appAwt._factory.createShape(shape));
                appAwt.execute();
            }
        });
    }


}
