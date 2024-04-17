package thibault.kuraima.core.utils.Command;

import thibault.kuraima.core.awt.components.app.Toolbar;
import thibault.kuraima.core.awt.components.buttons.Button;

public class RemoveBtnCommand implements Command{

    private Toolbar toolbar;
    private Button btn;

    @Override
    public String getName() {
        return "RemoveBtnCommand";
    }

    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    @Override
    public void execute() {

    }
}
