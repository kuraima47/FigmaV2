package thibault.kuraima.core.awt.application;

import thibault.kuraima.core.applications.App;
import thibault.kuraima.core.awt.application.Singleton.AppContext;
import thibault.kuraima.core.awt.components.app.DrawingPanel;
import thibault.kuraima.core.awt.components.buttons.DeleteButton;
import thibault.kuraima.core.awt.components.buttons.LoadButton;
import thibault.kuraima.core.awt.components.buttons.SaveButton;
import thibault.kuraima.core.awt.components.buttons.ShapeButton;
import thibault.kuraima.core.awt.components.shapes.PolygonAwt;
import thibault.kuraima.core.awt.components.shapes.ShapeAwt;
import thibault.kuraima.core.awt.listeners.AppListener;
import thibault.kuraima.core.awt.listeners.DrawingListener;
import thibault.kuraima.core.awt.components.app.Toolbar;
import thibault.kuraima.core.awt.listeners.ToolbarListener;
import thibault.kuraima.core.utils.Command.InitCommand;
import thibault.kuraima.core.utils.Command.RestoreCommand;
import thibault.kuraima.core.utils.Factory.Menu.FactoryMenu;
import thibault.kuraima.core.utils.Factory.Shape.ShapeFactory;
import thibault.kuraima.core.utils.Factory.Shape.ShapeFactoryAwt;
import thibault.kuraima.core.utils.Command.Command;
import thibault.kuraima.core.utils.Memento.History;
import thibault.kuraima.core.utils.Memento.Memento;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Base64;

public class AppAwt extends App implements Serializable {

    public DrawingPanel drawingPanel;
    public Toolbar toolbar;

    public int version = 0;

    private transient History history;

    public AppAwt() {
        AppContext.instance().app(this);
        createFactory();
        drawingPanel = new DrawingPanel();
        history = new History();
        createScene();
        AppListener appListener = new AppListener();
        addKeyListener(appListener);
        requestFocus();
        restoreToolbar();
        InitCommand init = new InitCommand();
        execute(init);
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
        addComponent();
        createListeners();
        pack();
        setVisible(true);
    }

    @Override
    public void execute(Command c) {
        try {
            c.execute();
            history.push(c, new Memento());
        }catch (Exception e){
        }
    }

    @Override
    public void undo() throws IOException {
        if (history != null && history.undo())
            drawingPanel.repaint();
    }

    @Override
    public void redo() throws IOException {
        if (history != null && history.redo())
            drawingPanel.repaint();
    }

    protected void createFactory() {
        if (_factory == null)
            _factory = new ShapeFactoryAwt();
        if (_menuFactory == null)
            _menuFactory = new FactoryMenu();
    }

    private void createListeners(){
        DrawingListener drawingListener = new DrawingListener(drawingPanel);
        drawingPanel.addMouseListener(drawingListener);
        drawingPanel.addMouseMotionListener(drawingListener);
        drawingPanel.addKeyListener(drawingListener);
    }

    private void addComponent() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.add(toolbar);
        topPanel.add(new DeleteButton("Delete"));
        add(topPanel, BorderLayout.PAGE_START);
        add(drawingPanel.getShapeList(), BorderLayout.WEST);
        add(drawingPanel, BorderLayout.CENTER);
    }

    private void createPanel(){
        drawingPanel.setPreferredSize(new Dimension(400, 400));
        drawingPanel.setFocusable(true);
        drawingPanel.requestFocusInWindow();
    }

    private void addSaveLoadInToolbar(){
        toolbar.addButton(new SaveButton("SavePanel"));
        toolbar.addButton(new LoadButton("LoadPanel"));
    }

    private void addShapeInToolbar() {
        toolbar.addButton(new ShapeButton("Rectangle", (ShapeAwt) _factory.createRectangle(100, 100, 50, 50)));
        toolbar.addButton(new ShapeButton("Exagone", _factory.createShape(new PolygonAwt(new Point(100, 100), new Point(50, 50)))));
    }

    private void restoreToolbar(){
        File file = new File(System.getProperty("user.dir") + "/toolbar.ser");
        if (file.exists()) {
            try {
                RestoreCommand restore = new RestoreCommand();
                restore.setParams(null, "Toolbar", file.getAbsolutePath());
                restore.execute();
            } catch (Exception e) {
                addSaveLoadInToolbar();
                addShapeInToolbar();
            }
        } else {
            addSaveLoadInToolbar();
            addShapeInToolbar();
        }
    }

}
