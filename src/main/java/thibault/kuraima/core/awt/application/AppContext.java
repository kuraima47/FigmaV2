package thibault.kuraima.core.awt.application;

import javax.management.RuntimeErrorException;
import java.awt.*;

/*
* Singleton to share Graphics between all AWT implementation and to 
* prevent to poluate the Shape interface.
*/
public class AppContext {
    static private AppContext _singleton = null;
    private AppAwt _app = null;
    static public AppContext instance() {
        if (_singleton == null)
            _singleton = new AppContext();
        return _singleton;
    }
    private AwtContext() {
    }

    public AppAwt app() {
        if (_app == null)
            throw new RuntimeErrorException(null, "Graphics has not been set contex is not valid");
        return _app;
    }

    public AppContext app(AppAwt app) {
        _app = app;
        return this;
    }
}
