package ifly.morefish.gui.menus.editrewards;

import ifly.morefish.fishpack.pack.Pack;
import ifly.morefish.fishpack.pack.reward.RewardItem;
import ifly.morefish.gui.Menu;
import ifly.morefish.gui.PlayerMenuUtil;
import ifly.morefish.gui.anvil.AnvilController;
import ifly.morefish.gui.anvil.actions.EditItemName;
import ifly.morefish.gui.helper.ItemCreator;
import ifly.morefish.gui.menus.PackRewardsMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class EditItem extends Menu {
    RewardItem item;
    Pack pack;
    public EditItem(PlayerMenuUtil playerMenuUtil, RewardItem item, Pack pack) {
        super(playerMenuUtil);
        this.item = item;
        this.pack = pack;
    }

    @Override
    public String getMenuName() {
        return "Edit item";
    }

    @Override
    public int getSlots() {
        return 3;
    }

    @Override
    public void handleInventoryClick(InventoryClickEvent e) {
        if (e.getSlot() == 10){
            AnvilController.createAnvil((Player) e.getWhoClicked(), new EditItemName((Player) e.getWhoClicked(), item, pack));
        }
        if (e.getSlot() == getSlots()*9-9){
            new PackRewardsMenu(getPlayerMenuUtil(), pack).open();
        }

        if (e.getSlot() == 13-9){
            if (item.getItem().getAmount()+1 <= 64){
                item.getItem().setAmount(item.getItem().getAmount()+1);
                setMenuItems();
            }

        }
        if (e.getSlot() == 13+9){
            if (item.getItem().getAmount()-1 > 0){
                item.getItem().setAmount(item.getItem().getAmount()-1);
                setMenuItems();
            }
        }
        if (e.getSlot() == 16){
            if (e.isLeftClick()){
                if (item.getChance()+5 <= 100){
                    item.setChance(item.getChance() + 5);
                }else{
                    item.setChance(100);
                }

            }
            if (e.isRightClick()) {
                if (item.getChance()-5 >= 0){
                    item.setChance(item.getChance()-5);
                }else{
                    item.setChance(0);
                }

            }
            setMenuItems();
        }
        e.setCancelled(true);
    }

    @Override
    public void setMenuItems() {
        getInventory().setItem(10, ItemCreator.create(Material.PAPER, "Edit item name"));
        getInventory().setItem(13-9, ItemCreator.create(Material.GREEN_WOOL, "Add amount"));
        getInventory().setItem(13, item.getItem());
        getInventory().setItem(16, ItemCreator.create(Material.COMPASS, "Item drop chance "+item.getChance() +"%",
                "Left click add 5%", "Right click subtract 5%"));
        getInventory().setItem(13+9, ItemCreator.create(Material.RED_WOOL, "Subtract amount"));
        getInventory().setItem(getSlots()*9-9, ItemCreator.create(Material.BARRIER, "Back"));
    }
}
