package thibault.kuraima.core.awt.components.shapes;

import thibault.kuraima.core.awt.application.Singleton.AppContext;
import thibault.kuraima.core.awt.components.app.DrawingPanel;
import thibault.kuraima.core.awt.components.menus.MenuGroup;
import thibault.kuraima.core.components.Shape;
import thibault.kuraima.core.utils.Prototype.IPrototype;

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

    private Point2D lastPos = new Point2D.Double(0, 0);

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
        if (size.getX() == 0 || size.getY() == 0) {
            throw new IllegalArgumentException("Original size dimensions must be non-zero");
        }

        double scaleX = vec.getX() / size.getX();
        double scaleY = vec.getY() / size.getY();
        size = vec;
        Point2D refPoint = position();

        for (ShapeAwt shape : shapes) {
            double newWidth = shape.size().getX() * scaleX;
            double newHeight = shape.size().getY() * scaleY;
            Point2D newSize = new Point2D.Double(newWidth, newHeight);
            double dx = (shape.position().getX() - refPoint.getX()) * scaleX;
            double dy = (shape.position().getY() - refPoint.getY()) * scaleY;
            Point2D newPos = new Point2D.Double(refPoint.getX() + dx, refPoint.getY() + dy);
            shape.size(newSize);
            Point2D oldPos = shape.position();
            Point2D diff = new Point2D.Double(newPos.getX() - oldPos.getX(), newPos.getY() - oldPos.getY());
            shape.translate(diff);
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
        if (rotationCenter == null) {
            rotationCenter = new Point2D.Double((int)(pos.getX()), (int)(pos.getY()));
        }
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
    public JPopupMenu getMenu() {
        return AppContext.instance().app()._menuFactory.createMenuGroup().create();
    }

    @Override
    public void unSelect() {
        selected = false;
        for (ShapeAwt shape : shapes) {
            shape.unSelect();
        }
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
    public IPrototype clone() {
        Group g = new Group();
        for (ShapeAwt shape : shapes) {
            g.addShape((ShapeAwt) shape.clone());
        }
        if (color != null) {
            g.setColor(new Color(color.getRGB()));
        }
        g.setRotation(rotation);
        if (rotationCenter != null) {
            g.setRotationCenter(new Point2D.Double(rotationCenter.getX(), rotationCenter.getY()));
        }
        g.size(new Point2D.Double(size.getX(), size.getY()));
        Point2D oldPos = g.position();
        Point2D newPos = new Point2D.Double(pos.getX(), pos.getY());
        Point2D diff = new Point2D.Double(newPos.getX() - oldPos.getX(), newPos.getY() - oldPos.getY());
        g.translate(diff);
        g.setSelected(true);
        g.setDragged(true);
        g.setNew(true);
        g.setId(id);
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

    @Override
    public void setNewLastPos() {
        lastPos = new Point2D.Double(pos.getX(), pos.getY());
    }

    @Override
    public Point2D getLastPos() {
        return lastPos;
    }

}
