package ifly.morefish.gui.menus.rewardcreator;

import ifly.morefish.fishpack.pack.Pack;
import ifly.morefish.fishpack.pack.reward.RewardItem;
import ifly.morefish.gui.Menu;
import ifly.morefish.gui.PlayerMenuUtil;
import ifly.morefish.gui.helper.ItemCreator;
import ifly.morefish.gui.menus.EditMenu;
import ifly.morefish.gui.menus.PackRewardsMenu;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ItemReward extends Menu {
    Pack pack;
    public ItemReward(PlayerMenuUtil playerMenuUtil, Pack pack) {
        super(playerMenuUtil);
        this.pack = pack;
    }

    @Override
    public String getMenuName() {
        return "Put the item";
    }

    @Override
    public int getSlots() {
        return 4;
    }

    @Override
    public void handleInventoryClick(InventoryClickEvent e) {
        if (e.getSlot() >= ((getSlots()-1)*9)-9){


            if (e.getSlot() == getSlots()*9-9){
                new PackRewardsMenu(getPlayerMenuUtil(), pack).open();
            }
            if (e.getSlot() == getSlots()*9-5){
                int x = 0;
                for (ItemStack itemStack: getInventory().getContents()){
                    if (x < (getSlots()-2)*9 ){
                        if (itemStack != null){
                            RewardItem item = new RewardItem(itemStack, 100);
                            pack.getRewards().add(item);
                        }
                    }
                    x++;
                }
                new PackRewardsMenu(getPlayerMenuUtil(), pack).open();
            }
            e.setCancelled(true);
        }
    }

    @Override
    public void setMenuItems() {
        for (int i = 3*9-9; i < 3*9; i++){
            getInventory().setItem(i, ItemCreator.create(Material.RED_STAINED_GLASS, ""));
        }
        getInventory().setItem(getSlots()*9-9, ItemCreator.create(Material.BARRIER, "Back"));
        getInventory().setItem(getSlots()*9-5, ItemCreator.create(Material.COMMAND_BLOCK, "Add this items to pack"));
    }
}
