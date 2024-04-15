package thibault.kuraima.core.awt.components.shapes;

import thibault.kuraima.core.awt.components.app.DrawingPanel;
import thibault.kuraima.core.awt.components.menus.MenuRectangle;
import thibault.kuraima.core.components.Exagone;
import thibault.kuraima.core.components.Shape;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class ExagoneAwt extends Exagone implements ShapeAwt{
    public ExagoneAwt(Point2D point, Point2D size) {
        position(point);
        size(size);
    }

    @Override
    public void draw(Graphics2D g) {
        AffineTransform old = g.getTransform();
        if (rotationCenter != null) {
            g.rotate(Math.toRadians(rotation), rotationCenter.getX(), rotationCenter.getY());
        }
        else {
            g.rotate(Math.toRadians(rotation), pos.getX(), pos.getY());
        }
        g.setColor(color);
        int[] x = new int[sides];
        int[] y = new int[sides];
        for (int i = 0; i < sides; i++) {
            x[i] = (int) (pos.getX() + sidesLength * Math.cos(2 * Math.PI * i / sides));
            y[i] = (int) (pos.getY() + sidesLength * Math.sin(2 * Math.PI * i / sides));
        }

        g.fillPolygon(x, y, sides);
        drawSelect(g);
        g.setTransform(old);
    }

    public void drawSelect(Graphics2D g) {
        if (selected) {
            g.setColor(Color.BLUE);
            g.setStroke(new BasicStroke(2));
            g.drawRect((int) (pos.getX() - size.getX() / 2),
                    (int) (pos.getY() - size.getY() / 2),
                    (int) (size.getX()),
                    (int) (size.getY()));
        }
    }


    @Override
    public String name() {
        return this.getClass().getSimpleName() + " " + id;
    }

    @Override
    public Point2D position() {
        return pos;
    }

    @Override
    public thibault.kuraima.core.components.Shape position(Point2D position) {
        pos = position;
        return this;
    }

    @Override
    public thibault.kuraima.core.components.Shape translate(Point2D vec) {
        pos.setLocation(pos.getX() + vec.getX(), pos.getY() + vec.getY());
        rotationCenter = new Point2D.Double((int)(pos.getX()), (int)(pos.getY()));
        return this;
    }

    @Override
    public boolean contains(int x, int y) {
        double translatedX = x - rotationCenter.getX();
        double translatedY = y - rotationCenter.getY();
        double unrotatedX = translatedX * Math.cos(-rotation) - translatedY * Math.sin(-rotation);
        double unrotatedY = translatedX * Math.sin(-rotation) + translatedY * Math.cos(-rotation);
        unrotatedX += rotationCenter.getX();
        unrotatedY += rotationCenter.getY();
        return unrotatedX >= pos.getX() - size.getX() / 2 && unrotatedX <= pos.getX() + size.getX() / 2
                && unrotatedY >= pos.getY() - size.getY() / 2 && unrotatedY <= pos.getY() + size.getY() / 2;
    }


    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public boolean isDragged() {
        return dragged;
    }

    @Override
    public void setDragged(boolean dragged) {
        this.dragged = dragged;
    }

    @Override
    public ArrayList<JLabel> getLabels() {
        JLabel label = new JLabel(this.name());
        label.setBorder(new EmptyBorder(5,5,5,5));
        ArrayList<JLabel> labels = new ArrayList<>();
        labels.add(label);
        return labels;
    }
    @Override
    public void setId(int count) {
        id = count;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public double rotation() {
        return rotation;
    }

    @Override
    public Point2D getRound() {
        return arcSize;
    }

    @Override
    public void setRound(Point2D round) {
        arcSize = round;
    }

    public void setSides(int sides) {
        this.sides = sides;
    }

    public int getSides() {
        return sides;
    }

    public void setLengthSides(int lengthSides) {
        this.sidesLength = lengthSides;
    }

    public int getLengthSides() {
        return sidesLength;
    }

    @Override
    public void setRotation(double v) {
        rotation = v;
    }

    @Override
    public void setRotationCenter(Point2D center) {
        rotationCenter = center;
    }

    @Override
    public Shape copy() {
        ExagoneAwt e = new ExagoneAwt(
                new Point2D.Double(pos.getX(), pos.getY()),
                new Point2D.Double(size.getX(), size.getY())
        );
        e.setColor(new Color(color.getRGB()));
        e.setRotation(rotation);
        if (rotationCenter != null) {
            e.setRotationCenter(new Point2D.Double(rotationCenter.getX(), rotationCenter.getY()));
        }
        e.setRound(new Point2D.Double(arcSize.getX(), arcSize.getY()));
        e.setLengthSides(sidesLength);
        e.setSides(sides);
        e.setSelected(false);
        e.setDragged(true);
        e.setNew(true);
        e.setId(id);
        return e;
    }

    @Override
    public JPopupMenu getMenu(DrawingPanel panel) {
        return new MenuRectangle(panel).create();
    }

    @Override
    public void unSelect() {
        selected = false;
    }
}
