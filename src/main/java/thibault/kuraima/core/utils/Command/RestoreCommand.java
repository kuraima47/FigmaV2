package thibault.kuraima.core.utils.Command;

import thibault.kuraima.core.awt.application.AppAwt;
import thibault.kuraima.core.awt.application.Singleton.AppContext;
import thibault.kuraima.core.awt.components.app.DrawingPanel;
import thibault.kuraima.core.awt.components.app.Toolbar;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Base64;

public class RestoreCommand implements Command {

    private String backup;
    private String type;
    private String Path;

    @Override
    public String getName() {
        return "redo";
    }

    public void setParams(String backup, String type, String Path) {
        this.backup = backup;
        this.type = type;
        this.Path = Path;
    }

    @Override
    public void execute() {
        AppAwt this_app = AppContext.instance().app();
        try {
            if (Path == null) {
                byte[] data = Base64.getDecoder().decode(backup);
                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
                if (type == null){
                    AppAwt app = (AppAwt) ois.readObject();
                    this_app.drawingPanel.restore(app.drawingPanel);
                    this_app.toolbar.restore(app.toolbar);
                }
                else if (type.equals("Panel")) {
                    DrawingPanel panel = (DrawingPanel) ois.readObject();
                    this_app.drawingPanel.restore(panel);
                } else if (type.equals("Toolbar")) {
                    Toolbar tool = (Toolbar) ois.readObject();
                    this_app.toolbar.restore(tool);
                }
                ois.close();
            }else{
                FileInputStream fis = new FileInputStream(Path);
                ObjectInputStream ois = new ObjectInputStream(fis);
                if (type == null){
                    AppAwt app = (AppAwt) ois.readObject();
                    this_app.drawingPanel.restore(app.drawingPanel);
                    this_app.toolbar.restore(app.toolbar);
                }
                else if (type.equals("Panel")) {
                    DrawingPanel panel = (DrawingPanel) ois.readObject();
                    this_app.drawingPanel.restore(panel);
                } else if (type.equals("Toolbar")) {
                    Toolbar tool = (Toolbar) ois.readObject();
                    this_app.toolbar.restore(tool);
                }
                ois.close();
            }
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException occurred.");
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("IOException occurred : " + e.getMessage());
            throw new RuntimeException(e);
        }

    }
}
