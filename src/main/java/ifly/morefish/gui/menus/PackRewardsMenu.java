package ifly.morefish.gui.menus;

import ifly.morefish.fishpack.lang.MenuMsgs;
import ifly.morefish.fishpack.lang.RewardsMenuMsg;
import ifly.morefish.fishpack.pack.Pack;
import ifly.morefish.fishpack.pack.reward.RewardAbstract;
import ifly.morefish.fishpack.pack.reward.RewardItem;
import ifly.morefish.gui.Menu;
import ifly.morefish.gui.PlayerMenuUtil;
import ifly.morefish.gui.anvil.AnvilController;
import ifly.morefish.gui.helper.ItemCreator;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PackRewardsMenu extends Menu {
    private final RewardsMenuMsg menu;

    public PackRewardsMenu(PlayerMenuUtil playerMenuUtil) {
        super(playerMenuUtil);
        menu = MenuMsgs.get().RewardsMenu;
    }
    Pack pack;
    @Override
    public String getMenuName() {
        return menu.title.replace("{packname}", pack.getDisplayname());
    }

    public void setPack(Pack pack) {
        this.pack = pack;
    }

    @Override
    public int getSlots() {
        return 4;
    }

    @Override
    public void handleInventoryClick(InventoryClickEvent e) {

        if (e.getSlot() >= getSlots()*9-9 && e.getSlot() <= getSlots()*9){

            if (e.getSlot() == getSlots()*9-5){
                List<RewardAbstract> rewards = new ArrayList<>();
                for (int i = 0; i < pack.getRewards().size(); i++){
                    if (!(pack.getRewards().get(i) instanceof RewardItem)){
                        rewards.add(pack.getRewards().get(i));
                    }
                }
                pack.getRewards().clear();
                int x = 0;
                for (ItemStack itemStack : getInventory().getContents()){
                    if (x < getSlots()*9-9){
                        if (itemStack != null && itemStack.getType() != Material.AIR){
                            rewards.add(new RewardItem(itemStack, 100));
                        }
                    }
                    x ++;
                }
                pack.setRewards(rewards);
            }
            if (e.getSlot() == getSlots()*9-9){
                EditMenu menu = new EditMenu(getPlayerMenuUtil());
                menu.setPack(pack);
                menu.open();
            }

            e.setCancelled(true);
        }
    }

    @Override
    public void setMenuItems() {
        int i = 0;
        for (RewardAbstract reward : pack.getRewards()) {
            if (reward instanceof RewardItem){
                if (i < getSlots()*9-9){
                    getInventory().setItem(i, ((RewardItem) reward).getItem());
                }
                i++;
            }
        }
        getInventory().setItem(getSlots()*9-5, menu.save_item);
        getInventory().setItem(getSlots()*9-9, menu.back_item);
    }
}
