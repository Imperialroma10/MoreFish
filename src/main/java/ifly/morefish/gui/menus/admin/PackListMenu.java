package ifly.morefish.gui.menus.admin;

import ifly.morefish.datastorage.StorageCreator;
import ifly.morefish.fishpack.FishController;
import ifly.morefish.fishpack.lang.MenuMsgs;
import ifly.morefish.fishpack.lang.PacksMenuMsg;
import ifly.morefish.fishpack.pack.Pack;
import ifly.morefish.gui.Menu;
import ifly.morefish.gui.PlayerMenuUtil;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PackListMenu extends Menu {
    private final PacksMenuMsg menumsg;
    List<Pack> packList = FishController.packList;
    int page = 0;

    public PackListMenu(PlayerMenuUtil playerMenuUtil) {
        super(playerMenuUtil);
        menumsg = MenuMsgs.get().PacksMenuMsg;
    }

    @Override
    public String getMenuName() {
        return menumsg.title;
    }

    @Override
    public int getSlots() {
        return 6;
    }

    @Override
    public void handleInventoryClick(InventoryClickEvent e) {
        if (0 <= e.getSlot() && e.getSlot() <= getSlots() * 9 - 9) { // Block chests
            if (e.getSlot() < packList.size()) {
                int slot = e.getSlot();
                if (slot < packList.size()) {
                    EditMenu menu = new EditMenu(getPlayerMenuUtil(), packList.get(slot));
                    menu.open();
                }
            }
        }
        if (e.getSlot() == 49) {
            Pack pack = new Pack("Pack_name", "New pack", 0);
            StorageCreator.getStorageIns().Save(pack, true);
            FishController.packList.add(pack);
            new EditMenu(getPlayerMenuUtil(), pack).open();
        }
        if (e.getSlot() == getSlots() * 9 - 9) {
            new MainMenu(getPlayerMenuUtil()).open();
        }
        e.setCancelled(true);
    }

    @Override
    public void setMenuItems() {
        int count = packList.size();
        int maxPacks = getSlots() * 9 - 9;
        int pages = count / maxPacks;
        int start = page * maxPacks;
        int end = start + maxPacks;

        for (int i = start; i < end; i++) {
            if (i < count) {
                Pack pack = packList.get(i);
                ItemStack item = menumsg.item(pack.getDisplayname(), pack);
                inventory.setItem(i, item);
            }
        }

        getInventory().setItem(49, menumsg.create_new);
        getInventory().setItem(getSlots() * 9 - 9, menumsg.back);
    }

    public void nextPage() {
        page++;
        this.open();
    }

    public void previousPage() {
        page--;
        this.open();
    }
}
