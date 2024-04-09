package thibault.kuraima.core.awt.components.shapes;

import thibault.kuraima.core.awt.components.app.DrawingPanel;
import thibault.kuraima.core.components.Rectangle;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class RectangleAwt extends Rectangle implements ShapeAwt {


    public RectangleAwt(Point point, int i) {
        super(point, i);
    }

    @Override
    public void draw(Graphics2D g) {
        AffineTransform old = g.getTransform();
        g.rotate(Math.toRadians(rotation), rotationCenter.getX(), rotationCenter.getY());
        g.setColor(color);
        g.fillRoundRect((int)(pos.getX() - size.getX()/2),
                (int)(pos.getY() - size.getY()/2),
                (int)(size.getX()),
                (int)(size.getY()), (int) arcSize.getX(), (int) arcSize.getY());
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
    public Point2D size() {
        return size;
    }

    @Override
    public thibault.kuraima.core.components.Shape size(Point2D vec) {;
        size = vec;
        return this;
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
        JLabel label = new JLabel(this.getClass().getSimpleName() + " " + id);
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

    @Override
    public void setRotation(double v) {
        rotation = v;
    }

    @Override
    public void setRotationCenter(Point2D center) {
        rotationCenter = center;
    }

    @Override
    public JPopupMenu getMenu(DrawingPanel panel) {
        JPopupMenu popup = new JPopupMenu();
        JMenuItem delete = new JMenuItem("Delete");
        ImageIcon icon = new ImageIcon("src/main/resources/images/delete.png");
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(16, 16,  java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        delete.setIcon(icon);
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.deleteSelectedShape();
            }
        });
        JMenuItem color = new JMenuItem("Color");
        color.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color newColor = JColorChooser.showDialog(null, "Choose a color", getColor());
                setColor(newColor);
                panel.repaint();
            }
        });
        JMenuItem rotate = new JMenuItem("Rotate");
        rotate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog d = new JDialog();
                JLabel l3 = new JLabel("Select the rotation angle : ");
                String defaultValue3 = String.valueOf(rotation());
                JTextField tf3 = new JTextField(defaultValue3, defaultValue3.length());
                JButton b = new JButton("Submit");
                b.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        setRotation(Double.parseDouble(tf3.getText()));
                        panel.repaint();
                        d.dispose();
                    }
                });
                JPanel p = new JPanel(new GridLayout(0, 1));
                Box box1 = Box.createHorizontalBox();
                box1.add(l3);
                box1.add(tf3);
                p.add(box1);
                p.add(b);
                d.add(p);
                d.setSize(300, 100);
                d.setLayout(new FlowLayout());
                d.setVisible(true);
            }
        });
        JMenuItem scale = new JMenuItem("Scale");
        scale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog d = new JDialog();
                JLabel l = new JLabel("Select the width factor : ");
                String defaultValue = String.valueOf(size().getX());
                JTextField tf = new JTextField(defaultValue, defaultValue.length());
                JLabel l2 = new JLabel("Select the height factor : ");
                String defaultValue2 = String.valueOf(size().getY());
                JTextField tf2 = new JTextField(defaultValue2, defaultValue2.length());

                JButton b = new JButton("Submit");
                b.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        size(new Point2D.Double(Double.parseDouble(tf.getText()), Double.parseDouble(tf2.getText())));
                        panel.repaint();
                        d.dispose();
                    }
                });
                JPanel p = new JPanel(new GridLayout(0, 1));
                Box box1 = Box.createHorizontalBox();
                box1.add(l);
                box1.add(tf);
                Box box2 = Box.createHorizontalBox();
                box2.add(l2);
                box2.add(tf2);
                p.add(box1);
                p.add(box2);
                p.add(b);
                d.add(p);
                d.setSize(300, 150);
                d.setLayout(new FlowLayout());
                d.setVisible(true);
            }
        });
        JMenuItem rounder = new JMenuItem("Round");
        rounder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog d = new JDialog();
                JLabel l = new JLabel("Select the round width factor : ");
                String defaultValue = String.valueOf(getRound().getX());
                JTextField tf = new JTextField(defaultValue, defaultValue.length());
                JLabel l2 = new JLabel("Select the round height factor : ");
                String defaultValue2 = String.valueOf(getRound().getY());
                JTextField tf2 = new JTextField(defaultValue2, defaultValue2.length());

                JButton b = new JButton("Submit");
                b.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        setRound(new Point2D.Double(Double.parseDouble(tf.getText()), Double.parseDouble(tf2.getText())));
                        panel.repaint();
                        d.dispose();
                    }
                });
                JPanel p = new JPanel(new GridLayout(0, 1));
                Box box1 = Box.createHorizontalBox();
                box1.add(l);
                box1.add(tf);
                Box box2 = Box.createHorizontalBox();
                box2.add(l2);
                box2.add(tf2);
                p.add(box1);
                p.add(box2);
                p.add(b);
                d.add(p);
                d.setSize(300, 150);
                d.setLayout(new FlowLayout());
                d.setVisible(true);
            }
        });

        popup.add(delete);
        popup.add(color);
        popup.add(rotate);
        popup.add(scale);
        popup.add(rounder);
        return popup;
    }
}
