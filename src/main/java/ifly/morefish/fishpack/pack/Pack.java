package ifly.morefish.fishpack.pack;


import ifly.morefish.fishpack.pack.reward.RewardAbstract;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class Pack {

    String name;
    List<RewardAbstract> rewards;

    int dropChance;

    int customModelData;

    ItemStack chest = new ItemStack(Material.CHEST);
    public Pack(String name, int customModelData, List<RewardAbstract> rwds){
        this.name = name;
        this.customModelData = customModelData;
        rewards = rwds;
        setMetaChest();
    }

    public void setMetaChest(){
        ItemMeta meta = chest.getItemMeta();
        meta.setCustomModelData(getCustomModelData());
        meta.displayName(Component.text(getName()));
        chest.setItemMeta(meta);
    }

    public String getName() {
        return name;
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
            if (reward.getChance() == 0){
                reward.giveReward(player);
            }else{
                if (reward.checkChance(random)){
                    reward.giveReward(player);
                }
            }

        }

        player.getInventory().getItemInMainHand().subtract();
    }
}
