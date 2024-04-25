package thibault.kuraima.core.utils.Command;

import thibault.kuraima.core.awt.application.AppAwt;
import thibault.kuraima.core.awt.application.Singleton.AppContext;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class LoadCommand implements Command{
    @Override
    public String getName() {
        return "LoadCommand";
    }

    @Override
    public void execute() {
        AppAwt _app = AppContext.instance().app();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("SER files", "ser"));
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String filePath = file.getAbsolutePath();
            if (!filePath.endsWith(".ser")) {
                filePath += ".ser";
            }
            RestoreCommand restoreCommand = new RestoreCommand();
            restoreCommand.setParams(null, "Panel", filePath);
            restoreCommand.execute();
        }
        _app.drawingPanel.repaint();
    }
}
