package thibault.kuraima.core.awt.components.menus;

import thibault.kuraima.core.awt.application.AppAwt;
import thibault.kuraima.core.awt.application.Singleton.AppContext;
import thibault.kuraima.core.utils.Command.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;

public class MenuGroup implements Menu{


    AppAwt app;
    JPopupMenu popup = new JPopupMenu();

    JMenuItem delete;
    JMenuItem ungroup;
    JMenuItem color;
    JMenuItem rotate;
    JMenuItem scale;


    public MenuGroup(){
        this.app = AppContext.instance().app();
    }

    @Override
    public JPopupMenu create() {
        createItem();
        attachListener();
        return popup;
    }

    private void createItem(){
        ImageIcon icon = new ImageIcon("src/main/resources/images/delete.png");
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(16, 16,  java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        delete = new JMenuItem("Delete");
        delete.setIcon(icon);
        ungroup = new JMenuItem("unGroup");
        color = new JMenuItem("Color");
        rotate = new JMenuItem("Rotate");
        scale = new JMenuItem("Scale");
        popup.add(delete);
        popup.add(ungroup);
        popup.add(color);
        popup.add(rotate);
        popup.add(scale);
    }

    private void attachListener(){
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteCommand c = new DeleteCommand();
                c.setPanel(app.drawingPanel);
                app.execute(c);
            }
        });

        ungroup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UngroupCommand c = new UngroupCommand();
                c.setPanel(app.drawingPanel);
                app.execute(c);
            }
        });

        color.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color newColor = JColorChooser.showDialog(null, "Choose a color", app.drawingPanel.getSelectedShape().getColor());
                ColorCommand c = new ColorCommand();
                c.setPanel(app.drawingPanel);
                c.setColor(newColor);
                app.execute(c);
            }
        });

        rotate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog d = new JDialog();
                JLabel l3 = new JLabel("Select the rotation angle : ");
                String defaultValue3 = String.valueOf(app.drawingPanel.getSelectedShape().rotation());
                JTextField tf3 = new JTextField(defaultValue3, defaultValue3.length());
                JButton b = new JButton("Submit");
                b.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        RotateCommand c = new RotateCommand();
                        c.setPanel(app.drawingPanel);
                        c.setAngle(Integer.parseInt(tf3.getText()));
                        app.execute(c);
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

        scale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog d = new JDialog();
                JLabel l = new JLabel("Select the width factor : ");
                String defaultValue = String.valueOf(app.drawingPanel.getSelectedShape().size().getX());
                JTextField tf = new JTextField(defaultValue, defaultValue.length());
                JLabel l2 = new JLabel("Select the height factor : ");
                String defaultValue2 = String.valueOf(app.drawingPanel.getSelectedShape().size().getY());
                JTextField tf2 = new JTextField(defaultValue2, defaultValue2.length());

                JButton b = new JButton("Submit");
                b.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        SizeCommand c = new SizeCommand();
                        c.setPanel(app.drawingPanel);
                        c.setSize(new Point2D.Double(Double.parseDouble(tf.getText()), Double.parseDouble(tf2.getText())));
                        app.execute(c);
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
    }

}
