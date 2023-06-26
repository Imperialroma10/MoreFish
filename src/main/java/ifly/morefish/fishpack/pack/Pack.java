package ifly.morefish.fishpack.pack;


import ifly.morefish.fishpack.pack.reward.RewardAbstract;
import ifly.morefish.fishpack.pack.reward.RewardCommand;
import ifly.morefish.fishpack.pack.reward.RewardItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.swing.text.html.parser.Entity;
import java.util.*;

public class Pack {

    String name;
    List<RewardAbstract> rewards = new ArrayList<>();

    int dropChance;

    int customModelData;

    ItemStack chest = new ItemStack(Material.CHEST);
    public Pack(String name, int customModelData){
        this.name = name;
        this.customModelData = customModelData;
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

    public void getReward(Player player){
        Random a = new Random();


        for (RewardAbstract reward : rewards){
            int random = a.nextInt(100);
            if (reward.getChance() == 0){
                reward.getReward(player);
            }else{
                if (reward.checkChance(random)){
                    reward.getReward(player);
                }
            }

        }

        player.getInventory().getItemInMainHand().subtract();
    }
}
