package thibault.kuraima.core.awt.components.app;

import thibault.kuraima.core.applications.App;
import thibault.kuraima.core.awt.application.AwtContext;
import thibault.kuraima.core.awt.components.shapes.Group;
import thibault.kuraima.core.awt.components.shapes.ShapeAwt;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class DrawingPanel extends JPanel implements Serializable {

    private ShapeAwt selectedShape;

    private ArrayList<ShapeAwt> shapes = new ArrayList<>(){
    };

    private HashMap<String, Integer> shapeCount = new HashMap<>();

    private transient JPanel shapesListPanel;

    public transient App _app;

    public transient ShapeAwt copiedShape;

    public DrawingPanel(App app) {
        super();
        _app = app;
        Graphics g = getGraphics();
        AwtContext.instance().graphics(g);
        setBorder(BorderFactory.createLineBorder(Color.black));
        shapesListPanel = new JPanel();
        shapesListPanel.setLayout(new BoxLayout(shapesListPanel, BoxLayout.Y_AXIS));
        shapesListPanel.setPreferredSize(new Dimension(200, getHeight()));
        shapesListPanel.setBorder(BorderFactory.createTitledBorder("Shapes"));
        setFocusable(true);
    }

    public ShapeAwt getDraggedShape() {
        if (selectedShape == null)
            return null;
        if (selectedShape.isDragged()) {
            return selectedShape;
        }
        return null;
    }

    public void setDraggedShape(boolean dragged) {
        selectedShape.setDragged(dragged);
    }

    public void getShapeAt(int x, int y) {
        selectShapeAt(x, y);
    }

    private void selectShapeAt(int x, int y) {
        for (ShapeAwt shape : shapes) {
            if (shape.contains(x, y)) {
                selectedShape = shape;
                selectedShape.setDragged(false);
                return;
            }
        }
        selectedShape = null;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (ShapeAwt shape : shapes) {
            shape.draw(g2d);
        }
    }

    public void unselectShape() {
        for (ShapeAwt shape : shapes) {
            if (shape.isSelected()) {
                shape.unSelect();
                shape.setDragged(false);
            }
        }
        selectedShape = null;
        repaint();
    }

    public ShapeAwt getNewShape() {
        if (selectedShape == null)
            return null;
        if (selectedShape.isNew())
            return selectedShape;
        return null;
    }

    public void addShape(ShapeAwt s) {
        selectedShape = s;
        selectedShape.setSelected(true);
        String shapeName = s.name();
        int count = shapeCount.getOrDefault(shapeName, 0) + 1;
        s.setId(count);
        shapes.add(s);
        shapeCount.put(shapeName, count);
        actualiseShapeList();
    }

    private void actualiseShapeList() {
        shapesListPanel.removeAll();
        for (ShapeAwt shape : shapes) {
            for (JLabel label : shape.getLabels()) {
                shapesListPanel.add(label);
            }
        }
        shapesListPanel.revalidate();
        shapesListPanel.repaint();
    }

    public JPanel getShapeList() {
        return shapesListPanel;
    }

    public ShapeAwt getSelectedShape() {
        return selectedShape;
    }

    public void groupSelectedShapes() {
        Group group = new Group();
        for (ShapeAwt shape : shapes) {
            if (shape.isSelected()) {
                shape.setSelected(false);
                group.addShape(shape);
            }
        }
        for (ShapeAwt shape : group.getShapes()) {
            shapes.remove(shape);
        }

        addShape(group);
        unselectShape();
        actualiseShapeList();
        group.setSelected(true);
        repaint();
    }

    public void deleteSelectedShape() {
        if (selectedShape != null) {
            selectedShape.setDragged(false);
            selectedShape.setSelected(false);
            selectedShape.setNew(false);
            shapes.remove(selectedShape);
            actualiseShapeList();
            repaint();
        }
    }

    public void unGroupSelectedShapes() {

        if (selectedShape instanceof Group group) {
            shapes.addAll(group.getShapes());
            shapes.remove(group);
            actualiseShapeList();
            repaint();
        }
    }

    public ArrayList<ShapeAwt> getAllShapes() {
        return shapes;
    }

    public void restore(DrawingPanel backup) {
        shapes = new ArrayList<>();
        for (ShapeAwt shape : backup.getAllShapes()) {
            shapes.add((ShapeAwt) shape.copy());
        }
        shapeCount = backup.shapeCount;
        actualiseShapeList();
    }

    public void duplicateSelectedShape() {
        if (selectedShape != null) {
            ShapeAwt shape = (ShapeAwt) selectedShape.copy();
            shape.setNew(true);
            shape.translate(new Point(25, 25));
            addShape(shape);
            actualiseShapeList();
            repaint();
        }
    }

    public void copySelectedShape() {
        copiedShape = selectedShape;
    }

    public void pasteSelectedShape() {
        if (copiedShape != null) {
            ShapeAwt shape = (ShapeAwt) copiedShape.copy();
            shape.setNew(true);
            shape.translate(new Point(25, 25));
            addShape(shape);
            actualiseShapeList();
            repaint();
        }
    }
}
