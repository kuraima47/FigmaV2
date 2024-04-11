package thibault.kuraima.core.awt.components.buttons;

import thibault.kuraima.core.awt.application.AppAwt;
import thibault.kuraima.core.awt.components.shapes.ShapeAwt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShapeButton extends Button{

    public ShapeAwt shape;

    public ShapeButton(String text, ShapeAwt awtShape, AppAwt appAwt) {
        super(text);
        this.shape = awtShape;
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                appAwt.drawingPanel.addShape(appAwt._factory.createShape(shape));
                appAwt.execute();
            }
        });
    }
}
