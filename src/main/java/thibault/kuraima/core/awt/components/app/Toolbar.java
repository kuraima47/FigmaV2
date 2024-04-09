package thibault.kuraima.core.awt.components.app;

import thibault.kuraima.core.awt.listeners.Listener;
import thibault.kuraima.core.awt.listeners.ToolbarListener;

import javax.accessibility.Accessible;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Toolbar extends JToolBar{

    ToolbarListener listener;

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

    public void addButton(String btn, ActionListener actionListener) {
        JButton button = new JButton(btn);
        button.addActionListener(actionListener);
        add(button);
        addSeparator();
    }
};

