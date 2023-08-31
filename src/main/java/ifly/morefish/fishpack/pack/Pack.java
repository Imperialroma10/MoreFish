package ifly.morefish.fishpack.pack;


import ifly.morefish.fishpack.Config;
import ifly.morefish.fishpack.pack.reward.RewardAbstract;
import ifly.morefish.fishpack.pack.reward.RewardItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Pack {

    String Displayname;
    List<RewardAbstract> rewards;
    public String Name;
    int dropChance;

    int customModelData;
    ItemStack chest = new ItemStack(Material.CHEST);
    public Pack(String name, String displayname, int customModelData){
        this.Displayname = displayname;
        this.customModelData = customModelData;
        Name = name;
        rewards = new ArrayList<>();
        setMetaChest();
    }
    public Pack(String name, String displayname, int customModelData, List<RewardAbstract> rwds){
        this(name, displayname, customModelData);
        rewards = rwds;
        setMetaChest();
    }

    public void setMetaChest(){
        ItemMeta meta = chest.getItemMeta();
        meta.setCustomModelData(getCustomModelData());
        meta.displayName(Component.text(getDisplayname()));
        chest.setItemMeta(meta);
    }

    int freeCountOfItem(PlayerInventory inv, ItemStack is) {
        ItemStack[] contents = inv.getStorageContents();
        int c = 0;
        for (ItemStack stack : contents) {
            if (stack == null) {
                c += is.getMaxStackSize();
                continue;
            }
            if (stack.isSimilar(is)) {
                c += stack.getMaxStackSize() - stack.getAmount();
            }
        }
        return c;
    }

    public boolean enoughSpace(Player p) {
        for (RewardAbstract reward : rewards) {
            if (reward instanceof RewardItem) {
                RewardItem rewardItem = (RewardItem) reward;
                int amount = freeCountOfItem(p.getInventory(), rewardItem.getItem());
                if (amount < rewardItem.getItem().getAmount()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void addReward(RewardAbstract rewardAbstract){
        this.rewards.add(rewardAbstract);
    }
    public String getDisplayname() {
        return Displayname.replace("$","§");
    }
    public void setDropChance(int dropChance) {
        this.dropChance = dropChance;
    }
    public int getDropChance() {
        return dropChance;
    }
    public int getCustomModelData() {
        return customModelData;
    }
    public ItemStack getChest() {
        return chest;
    }
    public void giveReward(Player player){
        Random a = new Random();
        for (RewardAbstract reward : rewards){
            int random = a.nextInt(100);
            if (reward.getChance() == 0 || reward.getChance() == 100){
                reward.giveReward(player);
            }else{
                if (reward.checkChance(random)){
                    reward.giveReward(player);
                }
            }
        }
        player.sendMessage(Component.text(Config.getMessage(Config.getConfig().openpackmessage.replace("[pack]", this.Displayname))));
        player.getInventory().getItemInMainHand().subtract();
    }

    public List<RewardAbstract> getRewards() {
        return rewards;
    }

    public void setRewards(List<RewardAbstract> rewards) {
        this.rewards = rewards;
    }

    public void setDisplayname(String displayname) {
        Displayname = displayname;
    }

    public String getName() {
        return Name;
    }
}
