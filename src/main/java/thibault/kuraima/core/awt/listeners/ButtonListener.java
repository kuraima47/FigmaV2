package thibault.kuraima.core.awt.listeners;

import thibault.kuraima.core.awt.application.AppAwt;
import thibault.kuraima.core.awt.application.Singleton.AppContext;
import thibault.kuraima.core.awt.components.buttons.ShapeButton;
import thibault.kuraima.core.utils.Command.BackupCommand;
import thibault.kuraima.core.utils.Command.Command;
import thibault.kuraima.core.utils.Command.RemoveBtnCommand;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;

public class ButtonListener implements Listener{

    public ShapeButton _btn;

    public ButtonListener(ShapeButton button) {
        _btn = button;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        AppAwt app = AppContext.instance().app();
        if (e.getButton() == MouseEvent.BUTTON3) {
            JPopupMenu popupMenu = new JPopupMenu();
            JMenuItem menuItem = new JMenuItem("Delete");
            ImageIcon icon = new ImageIcon("delete.png");
            Image image = icon.getImage();
            Image newimg = image.getScaledInstance(15, 15,  java.awt.Image.SCALE_SMOOTH);
            icon = new ImageIcon(newimg);
            menuItem.setIcon(icon);
            menuItem.addActionListener(e1 -> {
                RemoveBtnCommand c = new RemoveBtnCommand();
                c.setBtn(_btn);
                c.setToolbar(app.toolbar);
                app.execute(c);
                File file = new File(System.getProperty("user.dir") + "/toolbar.ser");
                BackupCommand backup = new BackupCommand();
                backup.setParams(file.getAbsolutePath(), "Toolbar");
                backup.execute();
            });
            popupMenu.add(menuItem);
            popupMenu.show(app.toolbar, _btn.getX() + e.getX(), _btn.getY() + e.getY());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
