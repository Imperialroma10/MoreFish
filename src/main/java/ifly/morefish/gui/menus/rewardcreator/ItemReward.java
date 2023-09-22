package ifly.morefish.gui.menus.rewardcreator;

import ifly.morefish.fishpack.lang.MenuMsgs;
import ifly.morefish.fishpack.lang.PutItemMenuMsg;
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
    PutItemMenuMsg menu;
    public ItemReward(PlayerMenuUtil playerMenuUtil, Pack pack) {
        super(playerMenuUtil);
        this.pack = pack;
        menu = MenuMsgs.get().PutItemMenu;
    }

    @Override
    public String getMenuName() {
        return menu.title;
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
        getInventory().setItem(getSlots()*9-9, menu.back_item);
        getInventory().setItem(getSlots()*9-5, menu.put_item);
    }
}
