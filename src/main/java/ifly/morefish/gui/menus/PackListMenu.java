package ifly.morefish.gui.menus;

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

    public PackListMenu(PlayerMenuUtil playerMenuUtil) {
        super(playerMenuUtil);
        menumsg = MenuMsgs.get().PacksMenuMsg;
    }
    EditMenu menu = new EditMenu(getPlayerMenuUtil());
    List<Pack> packList = FishController.packList;
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
        if ( 0 <= e.getSlot() && e.getSlot() <= getSlots()*9-9){ // Block chests
            if (e.getSlot() < packList.size()){
                int slot = e.getSlot();
                if (slot < packList.size()){
                    menu.setPack(packList.get(slot));
                    menu.open();
                }
            }
        }
        if (e.getSlot() == 49){
            Pack pack = new Pack("Pack_name", "New pack", 0);
            new EditMenu(getPlayerMenuUtil(), true).setPack(pack).open();
        }
        if (e.getSlot() == getSlots()*9-9){
            new MainMenu(getPlayerMenuUtil()).open();
        }
        e.setCancelled(true);
    }
    int page = 0;
    @Override
    public void setMenuItems() {
        int count = packList.size();
        int maxPacks = getSlots()*9 -9;
        int pages = count / maxPacks;
        int start = page * maxPacks;
        int end = start + maxPacks;

        for (int i = start; i < end; i++){
            if (i < count){
                Pack pack = packList.get(i);
                ItemStack item = menumsg.item(pack.getDisplayname(), pack.getDropChance(), pack.getName());
                inventory.setItem(i, item);
            }
        }

        getInventory().setItem(49, menumsg.create_new);
        getInventory().setItem(getSlots()*9-9, menumsg.back);
    }
    public void nextPage(){
        page++;
        this.open();
    }
    public void previousPage(){
        page--;
        this.open();
    }
}
