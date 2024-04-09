package thibault.kuraima.core.applications;

import thibault.kuraima.core.utils.AbstractFactory.ShapeFactory;

import javax.swing.*;

public abstract class App extends JFrame {
    protected ShapeFactory _factory = null;

    public abstract void run();

    protected abstract void createScene();

}
