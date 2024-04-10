package thibault.kuraima.core.awt.application;

import thibault.kuraima.core.applications.App;
import thibault.kuraima.core.awt.components.app.DrawingPanel;
import thibault.kuraima.core.awt.components.shapes.ShapeAwt;
import thibault.kuraima.core.awt.listeners.DrawingListener;
import thibault.kuraima.core.awt.components.shapes.RectangleAwt;
import thibault.kuraima.core.awt.components.app.Toolbar;
import thibault.kuraima.core.awt.listeners.ToolbarListener;
import thibault.kuraima.core.components.Shape;
import thibault.kuraima.core.utils.AbstractFactory.ShapeFactory;
import thibault.kuraima.core.utils.AbstractFactory.ShapeFactoryAwt;

import javax.swing.*;
import java.awt.*;

public class AppAwt extends App {
    public DrawingPanel drawingPanel = new DrawingPanel();
    public Toolbar toolbar;

    public AppAwt() {
        if (_factory == null) {
            _factory = createFactory();
        }
        createScene();
    }

    @Override
    public void run() {
    }

    public void createScene() {
        Shape shape = new RectangleAwt(new Point(0, 0), new Point(50, 50));
        ToolbarListener listener = new ToolbarListener(drawingPanel, _factory);
        toolbar = new Toolbar(listener);
        listener.setToolBar(toolbar);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        createPanel();
        addShapeInToolbar();
        addComponent();
        createListeners();
        pack();
        setVisible(true);
    }

    protected ShapeFactory createFactory() {
        return new ShapeFactoryAwt();
    }

    private void createListeners(){
        DrawingListener drawingListener = new DrawingListener(drawingPanel);
        drawingPanel.addMouseListener(drawingListener);
        drawingPanel.addMouseMotionListener(drawingListener);
        drawingPanel.addKeyListener(drawingListener);
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
        toolbar.addButton("Rectangle", e -> drawingPanel.addShape((ShapeAwt) _factory.createRectangle(100, 100, 50, 50)));
    }

}
