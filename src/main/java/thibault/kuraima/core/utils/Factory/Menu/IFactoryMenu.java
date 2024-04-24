package thibault.kuraima.core.utils.Factory.Menu;

import thibault.kuraima.core.awt.components.menus.Menu;

public interface IFactoryMenu {

    public Menu createMenuGroup();

    public Menu createMenuPolygone();

    public Menu createMenuRectangle();
}
