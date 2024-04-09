package thibault.kuraima;

import thibault.kuraima.core.applications.App;
import thibault.kuraima.core.awt.application.AppAwt;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        App app = new AppAwt();
        SwingUtilities.invokeLater(app::run);
    }
}