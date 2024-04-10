package thibault.kuraima.core.awt.listeners;

import thibault.kuraima.core.applications.App;
import thibault.kuraima.core.awt.application.AppAwt;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class AppListener implements Listener{

    private App _app;

    public AppListener(App app) {
        _app = app;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        _app.requestFocus();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_G && e.isControlDown()){
            ((AppAwt)_app).drawingPanel.groupSelectedShapes();
            _app.execute();
        }
        if (e.getKeyCode() == KeyEvent.VK_Z && e.isControlDown()){
            _app.undo();
        } else if (e.getKeyCode() == KeyEvent.VK_Y && e.isControlDown()) {
            _app.redo();
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
