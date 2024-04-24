package thibault.kuraima.core.applications;

import thibault.kuraima.core.utils.Factory.Menu.FactoryMenu;
import thibault.kuraima.core.utils.Factory.Shape.ShapeFactory;
import thibault.kuraima.core.utils.Command.Command;

import javax.swing.*;
import java.io.IOException;

public abstract class App extends JFrame {

    public transient ShapeFactory _factory = null;

    public transient FactoryMenu _menuFactory = null;

    public abstract void run();

    protected abstract void createScene();

    public abstract void undo() throws IOException;

    public abstract void redo() throws IOException;

    public abstract void execute(Command c);
}
