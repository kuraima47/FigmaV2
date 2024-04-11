package thibault.kuraima.core.awt.application;

import thibault.kuraima.core.applications.App;
import thibault.kuraima.core.awt.components.app.CustomActionListener;
import thibault.kuraima.core.awt.components.app.DrawingPanel;
import thibault.kuraima.core.awt.components.buttons.LoadButton;
import thibault.kuraima.core.awt.components.buttons.SaveButton;
import thibault.kuraima.core.awt.components.buttons.ShapeButton;
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
import java.awt.event.ActionEvent;
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
        restoreToolbar();
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
        addSaveLoadInToolbar();
        createListeners();
        pack();
        setVisible(true);
    }

    @Override
    public void execute() {
        history.push(new Memento(this));
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
                if (type.equals("Panel")) {
                    oos.writeObject(drawingPanel);
                } else if (type.equals("Toolbar")) {
                    oos.writeObject(toolbar);
                } else {
                    oos.writeObject(this);
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
                AppAwt app = (AppAwt) ois.readObject();
                if (type.equals("Panel")) {
                    this.drawingPanel.restore(app.drawingPanel);
                } else if (type.equals("Toolbar")) {
                    this.toolbar.restore(app.toolbar);
                } else {
                    this.drawingPanel.restore(app.drawingPanel);
                    this.toolbar.restore(app.toolbar);
                }
                ois.close();
            }else{
                FileInputStream fis = new FileInputStream(Path);
                ObjectInputStream ois = new ObjectInputStream(fis);

                if (type.equals("Panel")) {
                    DrawingPanel panel = (DrawingPanel) ois.readObject();
                    this.drawingPanel.restore(panel);
                } else if (type.equals("Toolbar")) {
                    Toolbar tool = (Toolbar) ois.readObject();
                    this.toolbar.restore(tool);
                } else {
                    AppAwt app = (AppAwt) ois.readObject();
                    this.drawingPanel.restore(app.drawingPanel);
                    this.toolbar.restore(app.toolbar);
                }
                ois.close();
            }
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException occurred.");
        } catch (IOException e) {
            System.out.println("IOException occurred : " + e.getMessage());
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

    private void addSaveLoadInToolbar(){
        toolbar.addButton(new SaveButton("SavePanel", this));
        toolbar.addButton(new LoadButton("LoadPanel", this));
    }



    private void addShapeInToolbar() {
        toolbar.addButton(new ShapeButton("Rectangle", (ShapeAwt) _factory.createRectangle(100, 100, 50, 50), this));
    }

    private void restoreToolbar(){
        File file = new File(System.getProperty("user.dir") + "/src/main/resources/storage/awt/toolbar.ser");
        if (file.exists()) {
            restore(null, "Toolbar", System.getProperty("user.dir") + "/src/main/resources/storage/awt/toolbar.ser");
        }
    }

}
