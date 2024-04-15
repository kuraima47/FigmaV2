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
        if (getComponents().length > 4) {
            remove(getComponents().length - 1);
        }
        add(button);
        addSeparator();
        if (getComponents().length > 4) {
            add(Box.createHorizontalGlue());
        }
    }

    public void restore(Toolbar backup) {
        for(Component c : this.getComponents()){
            this.remove(c);
        }
        for(Component c : backup.getComponents()){
            if (c instanceof ShapeButton){
                ShapeButton sb = (ShapeButton) c;
                ShapeButton sb2 = new ShapeButton(sb.shape.name(), (ShapeAwt) sb.shape.copy(), (AppAwt) listener.panel._app);
                this.addButton(sb2);
            } else if (c instanceof LoadButton){
                LoadButton lb = (LoadButton) c;
                LoadButton lb2 = new LoadButton(lb.getText(), (AppAwt) listener.panel._app);
                this.addButton(lb2);
            } else if (c instanceof SaveButton){
                SaveButton sb = (SaveButton) c;
                SaveButton sb2 = new SaveButton(sb.getText(), (AppAwt) listener.panel._app);
                this.addButton(sb2);
            }
        }
        add(Box.createHorizontalGlue());
        revalidate();
        repaint();
    }
};

