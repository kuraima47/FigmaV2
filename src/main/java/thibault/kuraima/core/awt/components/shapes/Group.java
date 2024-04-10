package thibault.kuraima.core.awt.components.shapes;

import thibault.kuraima.core.awt.components.app.DrawingPanel;
import thibault.kuraima.core.awt.components.menus.MenuGroup;
import thibault.kuraima.core.components.Shape;

import javax.swing.*;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Group implements ShapeAwt{

    ArrayList <ShapeAwt> shapes;
    private Point2D pos  = new Point2D.Double(0, 0);
    private Point2D size = new Point2D.Double(1, 1);
    private boolean isNew = false;
    private boolean selected = true;
    private boolean dragged = false;

    private int id;
    private Color color;
    private double rotation = 0;
    private Point2D rotationCenter;

    public Group(ArrayList<ShapeAwt> shapes) {
        this.shapes = shapes;
    }

    public Group() {
        this.shapes = new ArrayList<>();
    }

    @Override
    public void draw(Graphics2D g) {
        AffineTransform old = g.getTransform();
        g.rotate(Math.toRadians(rotation), rotationCenter.getX(), rotationCenter.getY());
        for (ShapeAwt shape : shapes) {
            shape.draw(g);
        }
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
    public Point2D size() {
        return size;
    }

    @Override
    public ShapeAwt size(Point2D vec) {
        double scaleX = vec.getX() / size.getX();
        double scaleY = vec.getY() / size.getY();
        size = vec;
        Point2D refPoint = position();
        for (ShapeAwt shape : shapes) {
            Point2D currentPosition = shape.position();
            Point2D currentSize = shape.size();
            Point2D newPosition = new Point((int) (refPoint.getX() + (currentPosition.getX() - refPoint.getX()) * scaleX),
                    (int) (refPoint.getY() + (currentPosition.getY() - refPoint.getY()) * scaleY));
            Point2D newSize = new Point((int) (currentSize.getX() * scaleX), (int) (currentSize.getY() * scaleY));
            shape.size(newSize);
            shape.position(newPosition);
        }
        return this;
    }

    @Override
    public Point2D position() {
        return pos;
    }

    @Override
    public ShapeAwt position(Point2D position) {
        pos = position;
        return this;
    }

    @Override
    public ShapeAwt translate(Point2D vec) {
        pos.setLocation(pos.getX() + vec.getX(), pos.getY() + vec.getY());
        for (ShapeAwt shape : shapes) {
            shape.translate(vec);
        }
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

    @Override
    public boolean isNew() {
        return isNew;
    }

    @Override
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
        JLabel label = new JLabel("Group "+ id);
        ArrayList<JLabel> labels = new ArrayList<>();
        labels.add(label);
        for (ShapeAwt shape : shapes) {
            for (JLabel l : shape.getLabels()) {
                l.setText("    " + l.getText());
                labels.add(l);
            }
        }
        return labels;
    }

    @Override
    public JPopupMenu getMenu(DrawingPanel panel) {
        return new MenuGroup(panel).create();
    }

    @Override
    public void setId(int count) {
        id = count;
    }

    public ArrayList<ShapeAwt> getShapes() {
        return shapes;
    }

    public void addShape(ShapeAwt shape) {
        shapes.add(shape);
        double minX = Double.MAX_VALUE;
        double minY = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;
        double maxY = Double.MIN_VALUE;

        for (ShapeAwt s : shapes) {
            Point2D[] corners = getCornersAfterRotation(s);
            for (Point2D corner : corners) {
                if (corner.getX() < minX) {
                    minX = corner.getX();
                }
                if (corner.getY() < minY) {
                    minY = corner.getY();
                }
                if (corner.getX() > maxX) {
                    maxX = corner.getX();
                }
                if (corner.getY() > maxY) {
                    maxY = corner.getY();
                }
            }
        }

        for (ShapeAwt s : shapes) {
            Point2D sPos = s.position();
            Point2D sSize = s.size();
            if (sPos.getX() - sSize.getX()/2 < minX) {
                minX = sPos.getX() - sSize.getX()/2;
            }
            if (sPos.getY() - sSize.getY()/2 < minY) {
                minY = sPos.getY() - sSize.getY()/2;
            }
            if (sPos.getX() + sSize.getX()/2 > maxX) {
                maxX = sPos.getX() + sSize.getX()/2;
            }
            if (sPos.getY() + sSize.getY()/2 > maxY) {
                maxY = sPos.getY() + sSize.getY()/2;
            }
        }
        position(new Point2D.Double((minX + maxX)/2, (minY + maxY)/2));
        size = new Point2D.Double(maxX - minX, maxY - minY);
        setRotationCenter(new Point2D.Double((int)(pos.getX()), (int)(pos.getY())));
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
        for (ShapeAwt shape : shapes) {
            shape.setColor(color);
        }
    }

    public Color getColor() {
        return color;
    }

    @Override
    public double rotation() {
        return rotation;
    }

    @Override
    public Point2D getRound() {
        return null;
    }

    @Override
    public void setRound(Point2D round) {

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
    public Shape clone() {
        Shape s = null;
        try {
            s = (Shape) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return s;
    }

    @Override
    public Shape copy() {
        Group g = new Group();
        for (ShapeAwt shape : shapes) {
            g.addShape((ShapeAwt) shape.copy());
        }
        if (color != null) {
            g.setColor(new Color(color.getRGB()));
        }
        g.setRotation(rotation);
        g.setRotationCenter(new Point2D.Double(rotationCenter.getX(), rotationCenter.getY()));
        g.size(new Point2D.Double(size.getX(), size.getY()));
        g.translate(new Point2D.Double(pos.getX(), pos.getY()));
        g.setSelected(true);
        g.setDragged(true);
        g.setNew(true);
        return g;
    }


    private Point2D rotatePoint(Point2D point, Point2D center, double angle) {
        double rad = Math.toRadians(angle);
        double cosAngle = Math.cos(rad);
        double sinAngle = Math.sin(rad);

        double x = point.getX() - center.getX();
        double y = point.getY() - center.getY();

        double rotatedX = x * cosAngle - y * sinAngle;
        double rotatedY = x * sinAngle + y * cosAngle;

        rotatedX += center.getX();
        rotatedY += center.getY();

        return new Point2D.Double(rotatedX, rotatedY);
    }

    private Point2D[] getCornersAfterRotation(ShapeAwt shape) {
        Point2D center = shape.position();
        double halfWidth = shape.size().getX() / 2;
        double halfHeight = shape.size().getY() / 2;
        double angle = shape.rotation();

        Point2D topLeft = new Point2D.Double(center.getX() - halfWidth, center.getY() - halfHeight);
        Point2D topRight = new Point2D.Double(center.getX() + halfWidth, center.getY() - halfHeight);
        Point2D bottomLeft = new Point2D.Double(center.getX() - halfWidth, center.getY() + halfHeight);
        Point2D bottomRight = new Point2D.Double(center.getX() + halfWidth, center.getY() + halfHeight);

        Point2D[] rotatedCorners = {
                rotatePoint(topLeft, center, angle),
                rotatePoint(topRight, center, angle),
                rotatePoint(bottomLeft, center, angle),
                rotatePoint(bottomRight, center, angle)
        };

        return rotatedCorners;
    }

}
