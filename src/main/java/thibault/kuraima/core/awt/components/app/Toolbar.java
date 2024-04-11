package thibault.kuraima.core.awt.components.app;

import thibault.kuraima.core.awt.application.AppAwt;
import thibault.kuraima.core.awt.components.buttons.Button;
import thibault.kuraima.core.awt.components.buttons.LoadButton;
import thibault.kuraima.core.awt.components.buttons.SaveButton;
import thibault.kuraima.core.awt.components.buttons.ShapeButton;
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
        add(button);
        addSeparator();
    }

    public void restore(Toolbar backup) {
        for(Component c : this.getComponents()){
            this.remove(c);
        }
        for(Component c : backup.getComponents()){
            if (c instanceof ShapeButton){
                ShapeButton sb = (ShapeButton) c;
                ShapeButton sb2 = new ShapeButton(sb.getText(), (ShapeAwt) sb.shape.copy(), (AppAwt) listener.panel._app);
                this.add(sb2);
            } else if (c instanceof LoadButton){
                LoadButton lb = (LoadButton) c;
                LoadButton lb2 = new LoadButton(lb.getText(), (AppAwt) listener.panel._app);
                this.add(lb2);
            } else if (c instanceof SaveButton){
                SaveButton sb = (SaveButton) c;
                SaveButton sb2 = new SaveButton(sb.getText(), (AppAwt) listener.panel._app);
                this.add(sb2);
            }
            else {
                this.add(c);
            }
        }
        revalidate();
        repaint();
    }
};

