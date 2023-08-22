package ifly.morefish.gui.menus;

import ifly.morefish.gui.Menu;
import ifly.morefish.gui.PlayerMenuUtil;
import ifly.morefish.gui.helper.ItemCreator;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class MainMenu extends Menu {

    public MainMenu(PlayerMenuUtil playerMenuUtil) {
        super(playerMenuUtil);
    }
    PackListMenu packListMenu = new PackListMenu(getPlayerMenuUtil());
    @Override
    public String getMenuName() {
        return "Main menu";
    }

    @Override
    public int getSlots() {
        return 3;
    }

    @Override
    public void handleInventoryClick(InventoryClickEvent e) {
        e.setCancelled(true);
        if (e.getSlot() == 11){
            packListMenu.open();
        }
    }

    @Override
    public void setMenuItems() {
        getInventory().setItem(11,ItemCreator.create(Material.CHEST, "Packs"));

    }
}
