package ifly.morefish.gui.helper;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemCreator {
    public static ItemStack create(Material material, String title, String... lore) {
        List<String> lorelist = new ArrayList<>(lore.length);
        ItemStack itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();
        Collections.addAll(lorelist, lore);
        meta.setLore(lorelist);
        if (title != null)
            meta.setDisplayName(title);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static ItemStack create(Material material, String title, List<String> lore) {

        ItemStack itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();

        meta.setLore(lore);
        if (title != null)
            meta.setDisplayName(title);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static ItemStack replace(ItemStack itemStack, String title, String... lore) {
        List<String> lorelist = new ArrayList<>();

        ItemMeta meta = itemStack.getItemMeta();
        Collections.addAll(lorelist, lore);
        meta.setDisplayName(title);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static ItemStack replace(ItemStack itemStack, String title, int amount, String... lore) {
        List<String> lorelist = new ArrayList<>();

        ItemMeta meta = itemStack.getItemMeta();
        Collections.addAll(lorelist, lore);
        meta.setLore(lorelist);
        meta.setDisplayName(title);
        itemStack.setItemMeta(meta);
        itemStack.setAmount(amount);
        return itemStack;
    }

    public static ItemStack setLore(ItemStack itemStack, String... lore) {
        List<String> lorelist = new ArrayList<>();

        ItemMeta meta = itemStack.getItemMeta();
        Collections.addAll(lorelist, lore);
        meta.setLore(lorelist);
        itemStack.setItemMeta(meta);

        return itemStack;
    }
}
