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
        try {
            _backup = app.backup(null, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void restore() throws IOException {
        _app.restore(_backup, null, null);
    }
}
