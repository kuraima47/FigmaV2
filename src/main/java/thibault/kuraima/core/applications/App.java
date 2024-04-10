package thibault.kuraima.core.applications;

import thibault.kuraima.core.utils.AbstractFactory.ShapeFactory;

import javax.swing.*;
import java.io.IOException;

public abstract class App extends JFrame {

    protected ShapeFactory _factory = null;

    public abstract void run();

    protected abstract void createScene();

    public abstract void undo();

    public abstract void redo();

    public abstract String backup() throws IOException;

    public abstract void restore(String backup);

    public void execute() {
    }
}
