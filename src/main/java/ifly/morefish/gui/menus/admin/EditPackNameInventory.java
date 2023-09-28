package ifly.morefish.gui.menus.admin;

import ifly.morefish.fishpack.pack.Pack;
import ifly.morefish.gui.Menu;
import ifly.morefish.gui.PlayerMenuUtil;
import ifly.morefish.gui.helper.ItemCreator;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EditPackNameInventory extends Menu {
    Pack pack;

    public EditPackNameInventory(PlayerMenuUtil playerMenuUtil) {
        super(playerMenuUtil);
    }

    @Override
    public String getMenuName() {
        return null;
    }

    public EditPackNameInventory setPack(Pack pack) {
        this.pack = pack;
        return this;
    }

    @Override
    public int getSlots() {
        return 0;
    }

    @Override
    public void handleInventoryClick(InventoryClickEvent e) {
        if (e.getSlotType() == InventoryType.SlotType.RESULT) {

            ItemStack itemStack = e.getView().getItem(2);
            if (itemStack == null) return;
            ItemMeta meta = itemStack.getItemMeta();
        }


    }

    @Override
    public void setMenuItems() {
        getInventory().setItem(0, ItemCreator.create(Material.PAPER, pack.getDisplayname()));
    }

}
