package thibault.kuraima.core.applications;

import javax.swing.*;

public abstract class App extends JFrame {

    public abstract void run();

    protected abstract void createScene();

}
