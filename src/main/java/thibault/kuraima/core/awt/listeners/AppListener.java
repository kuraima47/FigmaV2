package thibault.kuraima.core.awt.listeners;

import thibault.kuraima.core.applications.App;
import thibault.kuraima.core.awt.application.AppAwt;
import thibault.kuraima.core.awt.application.Singleton.AppContext;
import thibault.kuraima.core.utils.Command.*;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class AppListener implements Listener{

    private App _app;

    public AppListener() {
        _app = AppContext.instance().app();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        _app.requestFocus();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_G && e.isControlDown()){
            GroupCommand c = new GroupCommand();
            c.setPanel(((AppAwt) _app).drawingPanel);
            _app.execute(c);
        } else if (e.getKeyCode() == KeyEvent.VK_Z && e.isControlDown()){
            try {
                _app.undo();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_Y && e.isControlDown()) {
            try {
                _app.redo();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_S && e.isControlDown()) {
            try {
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
                    _app.backup(filePath, "Panel");
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_O && e.isControlDown()) {
            try {
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
                    _app.restore(null, "Panel", filePath);
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_DELETE) {
            DeleteCommand c = new DeleteCommand();
            c.setPanel(((AppAwt) _app).drawingPanel);
            _app.execute(c);
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            DuplicateCommand c = new DuplicateCommand();
            c.setPanel(((AppAwt) _app).drawingPanel);
            _app.execute(c);
        } else if (e.getKeyCode() == KeyEvent.VK_C && e.isControlDown()) {
            CopyCommand c = new CopyCommand();
            c.setPanel(((AppAwt) _app).drawingPanel);
            c.execute();
        } else if (e.getKeyCode() == KeyEvent.VK_V && e.isControlDown()) {
            PasteCommand c = new PasteCommand();
            c.setPanel(((AppAwt) _app).drawingPanel);
            _app.execute(c);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
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
