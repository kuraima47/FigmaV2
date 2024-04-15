package thibault.kuraima.core.components;

import java.awt.*;
import java.awt.geom.Point2D;

public abstract class Exagone implements Shape {
    protected Point2D pos  = new Point2D.Double(0, 0);
    protected Point2D size = new Point2D.Double(1, 1);

    protected int sides = 3;
    protected int sidesLength = 25;

    protected Color color = Color.GREEN;

    protected boolean isNew = true;

    protected boolean selected = true;

    protected boolean dragged = true;

    protected int id;

    protected Point2D arcSize = new Point2D.Double(0, 0);
    protected double rotation = 0;
    protected Point2D rotationCenter;


    @Override
    public Point2D size() {
        return size;
    }

    @Override
    public Shape size(Point2D vec) {
        double currentWidth = sidesLength * 2;
        double currentHeight = sidesLength * Math.sqrt(3);
        double scaleFactorWidth = vec.getX() / currentWidth;
        double scaleFactorHeight = vec.getY() / currentHeight;
        double scaleFactor = Math.min(scaleFactorWidth, scaleFactorHeight);
        sidesLength *= (int) scaleFactor;
        return this;
    }

    @Override
    public Point2D position() {
        return pos;
    }

    @Override
    public Shape position(Point2D position) {
        pos = position;
        return this;
    }

    @Override
    public Shape translate(Point2D vec) {
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
    public void setId(int count) {
        id = count;
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

    @Override
    public void setRotation(double v) {
        rotation = v;
    }

    @Override
    public void setRotationCenter(Point2D center) {
        rotationCenter = center;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public Shape clone() {
        try {
            return (Shape) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
