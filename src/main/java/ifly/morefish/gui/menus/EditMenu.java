package ifly.morefish.gui.menus;

import ifly.morefish.datastorage.StorageCreator;
import ifly.morefish.fishpack.pack.Pack;
import ifly.morefish.gui.Menu;
import ifly.morefish.gui.PlayerMenuUtil;
import ifly.morefish.gui.helper.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class EditMenu extends Menu {
    Pack pack;
    public EditMenu(PlayerMenuUtil playerMenuUtil) {
        super(playerMenuUtil);
    }

    @Override
    public String getMenuName() {
        return "Edit menu";
    }

    @Override
    public int getSlots() {
        return 3;
    }

    @Override
    public void handleInventoryClick(InventoryClickEvent e) {

        if (e.getSlot() == 11-9){
            pack.setDropChance(pack.getDropChance()+5);
            getInventory().setItem(11, ItemCreator.replace(getInventory().getItem(11), "Chance "+ pack.getDropChance()));
        }
        if (e.getSlot() == 11+9){
            pack.setDropChance(pack.getDropChance()-5);
            getInventory().setItem(11, ItemCreator.replace(getInventory().getItem(11), "Chance "+ pack.getDropChance()));
        }
        if (e.getSlot() == 12){
            PackRewardsMenu packRewardsMenu = new PackRewardsMenu(getPlayerMenuUtil());
            packRewardsMenu.setPack(pack);
            packRewardsMenu.open();
        }
        if (e.getSlot() == 3*9-1){
            StorageCreator.getStorageIns().addReward(pack);
        }
        if (e.getSlot() == 3*9-9){
            new PackListMenu(getPlayerMenuUtil()).open();
        }
        if (e.getSlot() == 10){
            Inventory inventory1 = Bukkit.createInventory(null, InventoryType.ANVIL);
            getPlayerMenuUtil().getOwner().openInventory(inventory1);

        }
        e.setCancelled(true);
    }
    public EditMenu setPack(Pack pack){
        this.pack = pack;
        return this;
    }
    @Override
    public void setMenuItems() {
        getInventory().setItem(11-9, ItemCreator.create(Material.GREEN_WOOL, "Chance+"));
        getInventory().setItem(11, ItemCreator.create(Material.PISTON, "Chance "+ pack.getDropChance()));
        getInventory().setItem(11+9, ItemCreator.create(Material.RED_WOOL, "Chance-"));
        getInventory().setItem(12, ItemCreator.create(Material.CHEST, "Pack rewards"));
        getInventory().setItem(10, ItemCreator.create(Material.PAPER, "Edit packname"));
        getInventory().setItem(3*9-1, ItemCreator.create(Material.PISTON, "Save"));
        getInventory().setItem(3*9-9, ItemCreator.create(Material.BARRIER, "Back"));
    }
}
