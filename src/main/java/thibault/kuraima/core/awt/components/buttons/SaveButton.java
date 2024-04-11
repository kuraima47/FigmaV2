package thibault.kuraima.core.awt.components.buttons;

import thibault.kuraima.core.awt.application.AppAwt;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class SaveButton extends Button{

    public SaveButton(String text, AppAwt appAwt) {
        super(text);
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("SER files", "ser"));
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
                int result = fileChooser.showSaveDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    String filePath = file.getAbsolutePath();
                    if (!filePath.endsWith(".ser")) {
                        filePath += ".ser";
                    }
                    appAwt.backup(filePath, "Panel");
                }
            }
        });

    }

}
