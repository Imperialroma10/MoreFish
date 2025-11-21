package com.modencore.fishpack.lang;

import com.modencore.gui.helper.ItemCreator;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class MainMenuMsg {
    public final String title;
    public final ItemStack packs_item;
    public final ItemStack packs_reload;

    public MainMenuMsg(ConfigurationSection section) {
        title = section.getString("title", "");
        String title = section.getString("packs-item.title");
        String[] list = section.getStringList("packs-item.description").toArray(new String[0]);
        String title2 = section.getString("reload-pack-item.title");
        String[] list2 = section.getStringList("reload-pack-item.description").toArray(new String[0]);

        packs_item = ItemCreator.create(Material.CHEST, title, list);
        packs_reload = ItemCreator.create(Material.REDSTONE, title2, list2);
    }
}
