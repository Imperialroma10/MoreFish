package ifly.morefish.gui.menus.admin;

import ifly.morefish.datastorage.StorageCreator;
import ifly.morefish.fishpack.Config;
import ifly.morefish.fishpack.FishController;
import ifly.morefish.fishpack.lang.MainMenuMsg;
import ifly.morefish.fishpack.lang.MenuMsgs;
import ifly.morefish.gui.Menu;
import ifly.morefish.gui.PlayerMenuUtil;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MainMenu extends Menu {

    private final MainMenuMsg menu;
    PackListMenu packListMenu = new PackListMenu(getPlayerMenuUtil());

    public MainMenu(PlayerMenuUtil playerMenuUtil) {
        super(playerMenuUtil);
        menu = MenuMsgs.get().MainMenu;
    }

    @Override
    public String getMenuName() {
        return menu.title;
    }

    @Override
    public int getSlots() {
        return 3;
    }

    @Override
    public void handleInventoryClick(InventoryClickEvent e) {
        e.setCancelled(true);
        if (e.getSlot() == 11) {
            packListMenu.open();
        }
        if (e.getSlot() == 13) {
            FishController.packList.clear();
            FishController.packList.addAll(StorageCreator.getStorageIns().getPacks());
            e.getWhoClicked().sendMessage(Config.getMessage("Pack reloaded"));

        }
    }

    @Override
    public void setMenuItems() {

        getInventory().setItem(11, menu.packs_item);
        getInventory().setItem(13, menu.packs_reload);
    }
}
