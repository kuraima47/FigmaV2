package thibault.kuraima.core.utils.Factory.Menu;

import thibault.kuraima.core.awt.components.menus.Menu;
import thibault.kuraima.core.awt.components.menus.MenuGroup;
import thibault.kuraima.core.awt.components.menus.MenuPolygone;
import thibault.kuraima.core.awt.components.menus.MenuRectangle;

public class FactoryMenu implements IFactoryMenu{

    @Override
    public Menu createMenuGroup() {
        return new MenuGroup();
    }

    @Override
    public Menu createMenuPolygone() {
        return new MenuPolygone();
    }

    @Override
    public Menu createMenuRectangle() {
        return new MenuRectangle();
    }
}
