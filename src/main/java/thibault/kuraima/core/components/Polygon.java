package thibault.kuraima.core.components;

import thibault.kuraima.core.utils.Prototype.IPrototype;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Arrays;

public abstract class Polygon implements Shape {
    protected Point2D pos  = new Point2D.Double(0, 0);
    protected Point2D size = new Point2D.Double(1, 1);

    protected int sides = 3;
    protected int sidesLength = 25;

    protected Color color = Color.GREEN;

    protected boolean isNew = true;

    protected boolean selected = true;

    protected boolean dragged = true;

    protected Point2D lastPos = new Point2D.Double(0, 0);

    protected int id;

    protected Point2D arcSize = new Point2D.Double(0, 0);
    protected double rotation = 0;
    protected Point2D rotationCenter;
    public int[] xPoints;
    public int[] yPoints;


    @Override
    public Point2D size() {
        return size;
    }

    @Override
    public Shape size(Point2D vec) {
        if (xPoints == null && yPoints == null){
            xPoints = new int[sides];
            yPoints = new int[sides];
            for (int i = 0; i < sides; i++) {
                xPoints[i] = (int) (pos.getX() + sidesLength * Math.cos(2 * Math.PI * i / sides));
                yPoints[i] = (int) (pos.getY() + sidesLength * Math.sin(2 * Math.PI * i / sides));
            }
        }
        int minX = Arrays.stream(xPoints).min().getAsInt();
        int maxX = Arrays.stream(xPoints).max().getAsInt();
        int minY = Arrays.stream(yPoints).min().getAsInt();
        int maxY = Arrays.stream(yPoints).max().getAsInt();
        if (maxX - minX == 0 || maxY - minY == 0) {
            return this;
        }

        double scaleX = vec.getX() / (maxX - minX);
        double scaleY = vec.getY() / (maxY - minY);
        double newSidesLength = sidesLength * Math.min(scaleX, scaleY);
        double newCenterX = (vec.getX() / 2) + minX;
        double newCenterY = (vec.getY() / 2) + minY;

        for (int i = 0; i < xPoints.length; i++) {
            xPoints[i] = (int) (newCenterX + newSidesLength * Math.cos(2 * Math.PI * i / sides));
            yPoints[i] = (int) (newCenterY + newSidesLength * Math.sin(2 * Math.PI * i / sides));
        }

        sidesLength = (int) newSidesLength;
        this.size.setLocation(vec);

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
    public abstract IPrototype clone();

}
