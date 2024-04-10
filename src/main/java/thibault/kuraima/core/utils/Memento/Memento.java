package thibault.kuraima.core.utils.Memento;

import thibault.kuraima.core.applications.App;
import thibault.kuraima.core.awt.application.AppAwt;

import java.io.IOException;
import java.io.Serializable;

public class Memento implements Serializable {

    private String _backup;
    private App _app;

    public Memento(App app) {
        _app = app;
        System.out.println("Memento created");
        System.out.println(((AppAwt) app).drawingPanel.getAllShapes());
        try {
            _backup = app.backup();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void restore() {
        _app.restore(_backup);
    }
}
