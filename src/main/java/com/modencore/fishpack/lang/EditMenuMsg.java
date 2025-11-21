package com.modencore.fishpack.lang;

import com.modencore.gui.helper.ItemCreator;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class EditMenuMsg {

    public final String title;
    public final String chance_status;
    public final ItemStack add_chance;
    public final ItemStack sub_chance;
    public final ItemStack change_pack_name;
    public final ItemStack chance_status_item;
    public final ItemStack rewards_item;
    public final ItemStack remove_pack;
    public final ItemStack reload_pack;
    public final ItemStack save_item;
    public final ItemStack back_item;
    public final ItemStack getpack_item;


    public final String title6;
    public final String[] list6;

    public EditMenuMsg(ConfigurationSection section) {
        title = section.getString("title");
        String title1 = section.getString("add-chance-item.title");
        String[] list1 = section.getStringList("add-chance-item.description").toArray(new String[0]);

        String title2 = section.getString("subruct-chance-item.title");
        String[] list2 = section.getStringList("subruct-chance-item.description").toArray(new String[0]);

        chance_status = section.getString("chance-status-item.title");
        String[] list3 = section.getStringList("chance-status-item.description").toArray(new String[0]);

        String title4 = section.getString("change-pack-name-item.title");
        String[] list4 = section.getStringList("change-pack-name-item.description").toArray(new String[0]);

        String title5 = section.getString("rewards-item.title");
        String[] list5 = section.getStringList("rewards-item.description").toArray(new String[0]);

        title6 = section.getString("remove-pack-item.title");
        list6 = section.getStringList("remove-pack-item.description").toArray(new String[0]);

        String title7 = section.getString("reload-pack-item.title");
        String[] list7 = section.getStringList("reload-pack-item.description").toArray(new String[0]);

        String title8 = section.getString("save-pack-item.title");
        String[] list8 = section.getStringList("save-pack-item.description").toArray(new String[0]);

        String title9 = section.getString("back-item.title");
        String[] list9 = section.getStringList("back-item.description").toArray(new String[0]);

        String title10 = section.getString("get-pack-item.title");
        String[] list10 = section.getStringList("get-pack-item.description").toArray(new String[0]);

        add_chance = ItemCreator.create(Material.GREEN_WOOL, title1, list1);
        sub_chance = ItemCreator.create(Material.ENDER_EYE, title2, list2);
        chance_status_item = ItemCreator.create(Material.CRAFTING_TABLE, chance_status, list3);
        change_pack_name = ItemCreator.create(Material.PAPER, title4, list4);
        rewards_item = ItemCreator.create(Material.CHEST, title5, list5);
        remove_pack = ItemCreator.create(Material.HOPPER, title6, list6);
        reload_pack = ItemCreator.create(Material.COMMAND_BLOCK, title7, list7);
        save_item = ItemCreator.create(Material.PISTON, title8, list8);
        back_item = ItemCreator.create(Material.BARRIER, title9, list9);
        getpack_item = ItemCreator.create(Material.GRAY_DYE, title10, list10);
    }

}
