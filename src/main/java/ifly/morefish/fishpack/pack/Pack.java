package ifly.morefish.fishpack.pack;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.List;

public class Pack {

    String name;
    List<ItemStack> itemStackList = new ArrayList<>();
    List<Entity> entities = new ArrayList<>();

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

    public void addEntity(Entity entity){
        this.entities.add(entity);
    }
    public void addItemstack(ItemStack stack){
        this.itemStackList.add(stack);
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
}
