package ifly.morefish.gui.menus;

import ifly.morefish.fishpack.FishController;
import ifly.morefish.fishpack.pack.Pack;
import ifly.morefish.gui.Menu;
import ifly.morefish.gui.PlayerMenuUtil;
import ifly.morefish.gui.helper.ItemCreator;
import ifly.morefish.main;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PackListMenu extends Menu {
    public PackListMenu(PlayerMenuUtil playerMenuUtil) {
        super(playerMenuUtil);
    }
    EditMenu menu = new EditMenu(getPlayerMenuUtil());
    List<Pack> packList = FishController.packList;
    @Override
    public String getMenuName() {
        return "Pack list";
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
            FishController.packList.add(pack);
            new EditMenu(getPlayerMenuUtil(), true).setPack(pack).open();
            Bukkit.broadcast(Component.text("dfgdfg"));
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
                getInventory().setItem(i, ItemCreator.create(Material.CHEST, packList.get(i).getDisplayname()));
            }
        }

        getInventory().setItem(49, ItemCreator.create(Material.COMMAND_BLOCK, "Create new pack"));
        getInventory().setItem(getSlots()*9-9, ItemCreator.create(Material.BARRIER,"Back"));
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
