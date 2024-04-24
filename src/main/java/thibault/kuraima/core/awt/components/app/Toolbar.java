package thibault.kuraima.core.awt.components.app;

import thibault.kuraima.core.awt.application.AppAwt;
import thibault.kuraima.core.awt.components.buttons.*;
import thibault.kuraima.core.awt.components.buttons.Button;
import thibault.kuraima.core.awt.components.shapes.ShapeAwt;
import thibault.kuraima.core.awt.listeners.ToolbarListener;

import javax.swing.*;
import java.awt.*;

public class Toolbar extends JToolBar{

    private static final long serialVersionUID = 1L;
    transient ToolbarListener listener;
    transient Component space;

    public Toolbar(ToolbarListener listener) {
        super();
        this.listener = listener;
        addMouseListener(listener);
        addMouseMotionListener(listener);
        addKeyListener(listener);
        setFloatable(false);
        setRollover(true);
        setBorderPainted(true);
    }

    public void addButton(Button button) {
        if (space != null) {
            remove(space);
        }
        add(button);
        addSeparator();
        space = Box.createHorizontalGlue();
        add(space);
    }

    public void restore(Toolbar backup) {
        for(Component c : this.getComponents()){
            this.remove(c);
        }
        for(Component c : backup.getComponents()){
            if (c instanceof ShapeButton){
                ShapeButton sb = (ShapeButton) c;
                ShapeButton sb2 = new ShapeButton(sb.shape.name(), (ShapeAwt) sb.shape.clone());
                this.addButton(sb2);
            } else if (c instanceof LoadButton){
                LoadButton lb = (LoadButton) c;
                LoadButton lb2 = new LoadButton(lb.getText());
                this.addButton(lb2);
            } else if (c instanceof SaveButton){
                SaveButton sb = (SaveButton) c;
                SaveButton sb2 = new SaveButton(sb.getText());
                this.addButton(sb2);
            }
        }
        repaint();
    }

    public void removeButton(Button button) {
        remove(button);
        revalidate();
        repaint();
    }
};

