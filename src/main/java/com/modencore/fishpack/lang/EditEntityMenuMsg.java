package com.modencore.fishpack.lang;

import com.modencore.gui.helper.ItemCreator;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class EditEntityMenuMsg {
    public final String title;
    public final ItemStack back_item;
    public final String amount_item;
    public final String[] desc;
    public final String spawnchance_item;
    public final String[] spawnchance_desc;

    public EditEntityMenuMsg(ConfigurationSection section) {
        title = section.getString("title");
        String title1 = section.getString("back-item.title");
        String[] list1 = section.getStringList("back-item.description").toArray(new String[0]);

        amount_item = section.getString("edit-entity-amount.title");
        desc = section.getStringList("edit-entity-amount.description").toArray(new String[0]);

        spawnchance_item = section.getString("spawn-chance-item.title");
        spawnchance_desc = section.getStringList("spawn-chance-item.description").toArray(new String[0]);

        back_item = ItemCreator.create(Material.BARRIER, title1, list1);
    }
}
