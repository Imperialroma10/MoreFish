package ifly.morefish.gui;

import org.bukkit.event.inventory.InventoryClickEvent;

public class AnvilMenu extends Menu{
    public AnvilMenu(PlayerMenuUtil playerMenuUtil) {
        super(playerMenuUtil);
    }

    @Override
    public String getMenuName() {
        return null;
    }

    @Override
    public int getSlots() {
        return 0;
    }

    @Override
    public void handleInventoryClick(InventoryClickEvent e) {

    }

    @Override
    public void setMenuItems() {

    }
}
