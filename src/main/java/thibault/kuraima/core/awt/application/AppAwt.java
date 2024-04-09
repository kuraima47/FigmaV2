package thibault.kuraima.core.awt.application;

import thibault.kuraima.core.applications.App;
import thibault.kuraima.core.awt.components.app.DrawingPanel;
import thibault.kuraima.core.awt.listeners.DropListener;
import thibault.kuraima.core.awt.components.shapes.RectangleAwt;
import thibault.kuraima.core.awt.components.app.Toolbar;

import javax.swing.*;
import java.awt.*;

public class AppAwt extends App {
    public DrawingPanel drawingPanel = new DrawingPanel();
    public Toolbar toolbar = new Toolbar();

    public AppAwt() {
        createScene();
    }

    @Override
    public void run() {
    }

    public void createScene() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        createPanel();
        addShapeInToolbar();
        addComponent();
        createListeners();
        pack();
        setVisible(true);
    }

    private void createListeners(){
        DropListener dropListener = new DropListener(drawingPanel);
        drawingPanel.addMouseListener(dropListener);
        drawingPanel.addMouseMotionListener(dropListener);
        drawingPanel.addKeyListener(dropListener);
    }

    private void addComponent() {
        add(toolbar, BorderLayout.PAGE_START);
        add(drawingPanel.getShapeList(), BorderLayout.WEST);
        add(drawingPanel, BorderLayout.CENTER);
    }

    private void createPanel(){
        drawingPanel.setPreferredSize(new Dimension(400, 400));
        drawingPanel.setFocusable(true);
        drawingPanel.requestFocusInWindow();
    }


    private void addShapeInToolbar() {
        JButton button = new JButton("Circle");
        button.addActionListener(e -> drawingPanel.addShape(new RectangleAwt(new Point(100, 100), 50)));
        toolbar.add(button);
        toolbar.addSeparator();
        button = new JButton("Rectangle");
        button.addActionListener(e -> drawingPanel.addShape(new RectangleAwt(new Point(200, 200), 50)));
        toolbar.add(button);
    }

}
