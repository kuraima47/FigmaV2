package thibault.kuraima.core.awt.application;

import thibault.kuraima.core.applications.App;
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
import thibault.kuraima.core.utils.AbstractFactory.ShapeFactory;
import thibault.kuraima.core.utils.AbstractFactory.ShapeFactoryAwt;
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
        if (_factory == null) {
            _factory = createFactory();
        }
        drawingPanel = new DrawingPanel();
        history = new History();
        createScene();
        AppListener appListener = new AppListener();
        addKeyListener(appListener);
        requestFocus();
        restoreToolbar();
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
        addComponent();
        createListeners();
        pack();
        setVisible(true);
    }

    @Override
    public void execute() {
        history.push(new Memento());
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

    @Override
    public String backup(String Path, String type) {
        try {
            if(Path == null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                if (type == null) {
                    oos.writeObject(this);
                } else if (type.equals("Panel")) {
                    oos.writeObject(drawingPanel);
                } else if (type.equals("Toolbar")) {
                    oos.writeObject(toolbar);
                }
                oos.close();
                version++;
                return Base64.getEncoder().encodeToString(baos.toByteArray());
            }else{
                FileOutputStream fos = new FileOutputStream(Path);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                if (type == null) {
                    oos.writeObject(this);
                } else if (type.equals("Panel")) {
                    oos.writeObject(drawingPanel);
                } else if (type.equals("Toolbar")) {
                    oos.writeObject(toolbar);
                }
                oos.close();
                version++;
                return "";
            }
        } catch (IOException e) {
            return "";
        }
    }

    @Override
    public void restore(String backup, String type, String Path) {
        try {
            if (Path == null) {
                byte[] data = Base64.getDecoder().decode(backup);
                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
                if (type == null){
                    AppAwt app = (AppAwt) ois.readObject();
                    this.drawingPanel.restore(app.drawingPanel);
                    this.toolbar.restore(app.toolbar);
                }
                else if (type.equals("Panel")) {
                    DrawingPanel panel = (DrawingPanel) ois.readObject();
                    this.drawingPanel.restore(panel);
                } else if (type.equals("Toolbar")) {
                    Toolbar tool = (Toolbar) ois.readObject();
                    this.toolbar.restore(tool);
                }
                ois.close();
            }else{
                FileInputStream fis = new FileInputStream(Path);
                ObjectInputStream ois = new ObjectInputStream(fis);
                if (type == null){
                    AppAwt app = (AppAwt) ois.readObject();
                    this.drawingPanel.restore(app.drawingPanel);
                    this.toolbar.restore(app.toolbar);
                }
                else if (type.equals("Panel")) {
                    DrawingPanel panel = (DrawingPanel) ois.readObject();
                    this.drawingPanel.restore(panel);
                } else if (type.equals("Toolbar")) {
                    Toolbar tool = (Toolbar) ois.readObject();
                    this.toolbar.restore(tool);
                }
                ois.close();
            }
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException occurred.");
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("IOException occurred : " + e.getMessage());
            throw new RuntimeException(e);
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
                restore(null, "Toolbar", file.getAbsolutePath());
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
