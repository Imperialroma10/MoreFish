package ifly.morefish.fishpack.pack;

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
    HashMap<ItemStack, Integer> itemStackList = new HashMap<>();
    HashMap<EntityType, Integer> entities = new HashMap<>();

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

    public HashMap<EntityType, Integer> getEntities() {
        return entities;
    }

    public HashMap<ItemStack, Integer> getItemStackList() {
        return itemStackList;
    }

    public void setEntities(HashMap<EntityType, Integer> entities) {
        this.entities = entities;
    }

    public void setItemStackList(HashMap<ItemStack, Integer> itemStackList) {
        this.itemStackList = itemStackList;
    }

    public void addItemstack(ItemStack stack, int chance){
        this.itemStackList.put(stack, chance);
    }
    public void addEntity(EntityType entity, int chance){
        this.entities.put(entity, chance);
    }

    public void setName(String name) {
        this.name = name;
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

    public void setCustomModelData(int customModelData) {
        this.customModelData = customModelData;
    }

    public ItemStack getChest() {
        return chest;
    }

    public void getReward(Player player){
        Random a = new Random();
        int random;
        if (entities.size() > 0){
            for (Map.Entry<EntityType, Integer> entity : entities.entrySet()){
                random = a.nextInt(100);
                if (random <= entity.getValue()){
                    player.getWorld().spawnEntity(player.getLocation(), entity.getKey());
                }
            }
        }
        if (itemStackList.size() > 0){
            for (Map.Entry<ItemStack, Integer> item : itemStackList.entrySet()){
                random = a.nextInt(100);
                if (random <= item.getValue()){
                    player.getInventory().addItem(item.getKey());
                }
            }
        }

        Arrays.stream(player.getInventory().getContents()).filter(item -> item.isSimilar(getChest())).findFirst().get().subtract();
    }
}
