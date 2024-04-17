package thibault.kuraima.core.applications;

import thibault.kuraima.core.utils.AbstractFactory.ShapeFactory;
import thibault.kuraima.core.utils.Command.Command;

import javax.swing.*;
import java.io.IOException;

public abstract class App extends JFrame {

    public ShapeFactory _factory = null;

    public abstract void run();

    protected abstract void createScene();

    public abstract void undo() throws IOException;

    public abstract void redo() throws IOException;

    public abstract String backup(String Path, String type) throws IOException;

    public abstract void restore(String backup, String type, String Path) throws IOException;

    public abstract void execute(Command c);
}
