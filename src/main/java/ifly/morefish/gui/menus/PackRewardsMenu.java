package ifly.morefish.gui.menus;

import ifly.morefish.datastorage.FileStorage;
import ifly.morefish.datastorage.IStorage;
import ifly.morefish.datastorage.StorageCreator;
import ifly.morefish.fishpack.FishController;
import ifly.morefish.fishpack.pack.Pack;
import ifly.morefish.fishpack.pack.reward.RewardAbstract;
import ifly.morefish.fishpack.pack.reward.RewardItem;
import ifly.morefish.gui.Menu;
import ifly.morefish.gui.PlayerMenuUtil;
import ifly.morefish.gui.helper.ItemCreator;
import ifly.morefish.main;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PackRewardsMenu extends Menu {
    public PackRewardsMenu(PlayerMenuUtil playerMenuUtil) {
        super(playerMenuUtil);
    }
    Pack pack;
    @Override
    public String getMenuName() {
        return "Pack rewards";
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

                for (int i = 0; i < pack.getRewards().size(); i++){
                    if (pack.getRewards().get(i) instanceof RewardItem){
                        pack.getRewards().remove(i);
                    }
                }
//                for (RewardAbstract rewardAbstract: pack.getRewards()){
//                    if (rewardAbstract instanceof RewardItem){
//                        pack.getRewards().remove(rewardAbstract);
//                    }
//                }
                int x = 0;
                for (ItemStack itemStack : getInventory().getContents()){
                    if (x < getSlots()*9-9){
                        if (itemStack != null && itemStack.getType() != Material.AIR){
                            pack.addReward(new RewardItem(itemStack, 100));
                        }
                    }

                    x ++;
                }
            }
            if (e.getSlot() == getSlots()*9-9){
                EditMenu menu = new EditMenu(getPlayerMenuUtil());
                menu.setPack(pack);
                menu.open();
            }

            e.setCancelled(true);
        }
        Bukkit.broadcast(Component.text(pack.getRewards().size()));
    }

    @Override
    public void setMenuItems() {
        int i = 0;
        Bukkit.broadcast(Component.text(pack.getRewards().size()));
        for (RewardAbstract reward : pack.getRewards()) {
            if (reward instanceof RewardItem){
                if (i < getSlots()*9-9){
                    getInventory().setItem(i, ((RewardItem) reward).getItem());
                }
                i++;
            }
        }
        getInventory().setItem(getSlots()*9-9, ItemCreator.create(Material.BARRIER, "Back"));
        getInventory().setItem(getSlots()*9-5, ItemCreator.create(Material.COMMAND_BLOCK, "Save rewards"));
    }
}
