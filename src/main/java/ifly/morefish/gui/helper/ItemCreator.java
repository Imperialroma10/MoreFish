package ifly.morefish.gui.helper;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemCreator {
    public static ItemStack create(Material material, String title, String... lore){
        List<Component> lorelist = new ArrayList<>(lore.length);
        ItemStack itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();
        for (String lors : lore){
            lorelist.add(Component.text(lors).decoration(TextDecoration.ITALIC, false));
        }
        meta.lore(lorelist);
        if(title != null)
            meta.displayName(Component.text(title).decoration(TextDecoration.ITALIC, false));
        itemStack.setItemMeta(meta);
        return itemStack;
    }
    public static ItemStack replace(ItemStack itemStack, String title, String... lore){
        List<Component> lorelist = new ArrayList<>();

        ItemMeta meta = itemStack.getItemMeta();
        for (String lors : lore){
            lorelist.add(Component.text(lors));
        }
        meta.displayName(Component.text(title));
        itemStack.setItemMeta(meta);
        return itemStack;
    }
    public static ItemStack replace(ItemStack itemStack, String title, int amount, String... lore){
        List<Component> lorelist = new ArrayList<>();

        ItemMeta meta = itemStack.getItemMeta();
        for (String lors : lore){
            lorelist.add(Component.text(lors));
        }
        meta.lore(lorelist);
        meta.displayName(Component.text(title));
        itemStack.setItemMeta(meta);
        itemStack.setAmount(amount);
        return itemStack;
    }
    public static ItemStack setLore(ItemStack itemStack, String... lore){
        List<Component> lorelist = new ArrayList<>();

        ItemMeta meta = itemStack.getItemMeta();
        for (String lors : lore){
            lorelist.add(Component.text(lors));
        }
        meta.lore(lorelist);
        itemStack.setItemMeta(meta);

        return itemStack;
    }
}
