package thibault.kuraima.core.awt.application;

import thibault.kuraima.core.applications.App;
import thibault.kuraima.core.awt.components.app.DrawingPanel;
import thibault.kuraima.core.awt.components.shapes.ShapeAwt;
import thibault.kuraima.core.awt.listeners.AppListener;
import thibault.kuraima.core.awt.listeners.DrawingListener;
import thibault.kuraima.core.awt.components.shapes.RectangleAwt;
import thibault.kuraima.core.awt.components.app.Toolbar;
import thibault.kuraima.core.awt.listeners.ToolbarListener;
import thibault.kuraima.core.components.Shape;
import thibault.kuraima.core.utils.AbstractFactory.ShapeFactory;
import thibault.kuraima.core.utils.AbstractFactory.ShapeFactoryAwt;
import thibault.kuraima.core.utils.Memento.History;
import thibault.kuraima.core.utils.Memento.Memento;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Base64;

public class AppAwt extends App implements Serializable {

    public DrawingPanel drawingPanel = new DrawingPanel(this);
    public Toolbar toolbar;

    public int version = 0;

    private transient History history;

    public AppAwt() {
        if (_factory == null) {
            _factory = createFactory();
        }
        history = new History();
        createScene();
        AppListener appListener = new AppListener(this);
        addKeyListener(appListener);
        requestFocus();
        execute();
    }

    @Override
    public void run() {
    }

    public void createScene() {
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

    @Override
    public void execute() {
        history.push(new Memento(this));
    }

    @Override
    public void undo() {
        if (history != null && history.undo())
            drawingPanel.repaint();
    }

    @Override
    public void redo() {
        if (history != null && history.redo())
            drawingPanel.repaint();
    }

    @Override
    public String backup() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
            oos.close();
            version++;
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (IOException e) {
            return "";
        }
    }

    @Override
    public void restore(String backup) {
        try {
            byte[] data = Base64.getDecoder().decode(backup);
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
            AppAwt app = (AppAwt) ois.readObject();
            this.drawingPanel.restore(app.drawingPanel);
            this.toolbar.restore(app.toolbar);
            ois.close();
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException occurred.");
        } catch (IOException e) {
            System.out.println("IOException occurred.");
        }
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
