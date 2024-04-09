package thibault.kuraima.core.awt.components.app;

import thibault.kuraima.core.awt.listeners.ToolbarListener;

import javax.accessibility.Accessible;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Toolbar extends JToolBar{

    public Toolbar() {
        super();
        ToolbarListener listener = new ToolbarListener(this);
        addMouseListener(listener);
        addMouseMotionListener(listener);
        addKeyListener(listener);
        setFloatable(false);
        setRollover(true);
        setBorderPainted(true);
    }

};

