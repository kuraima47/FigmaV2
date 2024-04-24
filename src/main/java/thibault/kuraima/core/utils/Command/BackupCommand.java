package thibault.kuraima.core.utils.Command;

import thibault.kuraima.core.awt.application.AppAwt;
import thibault.kuraima.core.awt.application.Singleton.AppContext;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Base64;

public class BackupCommand implements Command{

    private String Path;

    private String type;

    private String backup;

    @Override
    public String getName() {
        return "undo";
    }

    public void setParams(String Path, String type) {
        this.Path = Path;
        this.type = type;
    }

    @Override
    public void execute() {
        AppAwt this_app = AppContext.instance().app();
        try {
            if(Path == null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                if (type == null) {
                    oos.writeObject(this_app);
                } else if (type.equals("Panel")) {
                    oos.writeObject(this_app.drawingPanel);
                } else if (type.equals("Toolbar")) {
                    oos.writeObject(this_app.toolbar);
                }
                oos.close();
                this_app.version++;
                backup = Base64.getEncoder().encodeToString(baos.toByteArray());
            }else{
                FileOutputStream fos = new FileOutputStream(Path);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                if (type == null) {
                    oos.writeObject(this_app);
                } else if (type.equals("Panel")) {
                    oos.writeObject(this_app.drawingPanel);
                } else if (type.equals("Toolbar")) {
                    oos.writeObject(this_app.toolbar);
                }
                oos.close();
                this_app.version++;
                backup = "";
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            backup = "";
        }
    }

    public String result() {
        return backup;
    }

}
