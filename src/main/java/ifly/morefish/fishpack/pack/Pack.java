package ifly.morefish.fishpack.pack;


import ifly.morefish.fishpack.lang.Lang;
import ifly.morefish.fishpack.pack.reward.RewardAbstract;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class Pack {

    String Displayname;
    List<RewardAbstract> rewards;
    public final String Name;
    int dropChance;

    int customModelData;

    boolean status = false;
    ItemStack chest = new ItemStack(Material.CHEST);
    public Pack(String name, String displayname, int customModelData, List<RewardAbstract> rwds){
        this.Displayname = displayname;
        this.customModelData = customModelData;
        Name = name;
        rewards = rwds;
        setMetaChest();
    }
    public Pack(String name, String displayname, int customModelData){
        this.Displayname = displayname;
        this.customModelData = customModelData;
        Name = name;
        setMetaChest();
    }

    public void setMetaChest(){
        ItemMeta meta = chest.getItemMeta();
        meta.setCustomModelData(getCustomModelData());
        meta.displayName(Component.text(getDisplayname()));
        chest.setItemMeta(meta);
    }
    public String getDisplayname() {
        return Displayname;
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
        player.sendMessage(Component.text(Lang.getMessage(Lang.getLang().openpackmessage.replace("[pack]", this.Displayname))));
        player.getInventory().getItemInMainHand().subtract();
    }
    public void changeStatus(){
        status = !status;
    }
    public boolean getStatus(){
        return this.status;
    }
}
