package ifly.morefish.gui.menus;

import ifly.morefish.datastorage.StorageCreator;
import ifly.morefish.fishpack.FishController;
import ifly.morefish.gui.Menu;
import ifly.morefish.gui.PlayerMenuUtil;
import ifly.morefish.gui.helper.ItemCreator;
import net.kyori.adventure.text.Component;
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
        if (e.getSlot() == 13){
            FishController.packList.clear();
            FishController.packList.addAll(StorageCreator.getStorageIns().getPacks());
            e.getWhoClicked().sendMessage(Component.text("Pack reloaded"));

        }
    }

    @Override
    public void setMenuItems() {
        getInventory().setItem(11,ItemCreator.create(Material.CHEST, "Packs"));
        getInventory().setItem(13,ItemCreator.create(Material.COMMAND_BLOCK, "Reload packs"));
    }
}
