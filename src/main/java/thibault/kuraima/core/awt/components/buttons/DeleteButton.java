package thibault.kuraima.core.awt.components.buttons;

import thibault.kuraima.core.awt.application.AppAwt;
import thibault.kuraima.core.awt.application.Singleton.AppContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteButton extends Button{

    public DeleteButton(String text) {
        super();
        ImageIcon icon = new ImageIcon("src/main/resources/images/delete.png");
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(28, 28,  java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        setIcon(icon);
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        AppAwt appAwt = AppContext.instance().app();
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (appAwt.drawingPanel.getNewShape() != null)
                    appAwt.drawingPanel.deleteSelectedShape();
            }
        });
    }

}
