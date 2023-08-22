package ifly.morefish.gui.menus;

import ifly.morefish.gui.Menu;
import ifly.morefish.gui.PlayerMenuUtil;
import org.bukkit.event.inventory.InventoryClickEvent;

public class EditPackNameInventory extends Menu {
    public EditPackNameInventory(PlayerMenuUtil playerMenuUtil) {
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
