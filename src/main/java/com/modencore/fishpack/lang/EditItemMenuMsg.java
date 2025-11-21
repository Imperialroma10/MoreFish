package com.modencore.fishpack.lang;

import com.modencore.gui.helper.ItemCreator;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class EditItemMenuMsg {
    public final String title;
    public final ItemStack back_item;
    public final ItemStack addtopack_item;
    public final ItemStack editname_item;
    public final ItemStack addamount_item;
    public final ItemStack subtructamount_item;
    public final String dropchance;
    public final String[] dropchancelist;

    public EditItemMenuMsg(ConfigurationSection section) {
        title = section.getString("title");
        String title1 = section.getString("back-item.title");
        String[] list1 = section.getStringList("back-item.description").toArray(new String[0]);

        String title2 = section.getString("add-to-pack-item.title");
        String[] list2 = section.getStringList("add-to-pack-item.description").toArray(new String[0]);

        String title3 = section.getString("edit-name-item.title");
        String[] list3 = section.getStringList("edit-name-item.description").toArray(new String[0]);

        dropchance = section.getString("edit-drop-chance-item.title");
        dropchancelist = section.getStringList("edit-drop-chance-item.description").toArray(new String[0]);

        String title4 = section.getString("add-amount-item.title");
        String[] list4 = section.getStringList("add-amount-item.description").toArray(new String[0]);

        String title5 = section.getString("subtruct-amount-item.title");
        String[] list5 = section.getStringList("subtruct-amount-item.description").toArray(new String[0]);

        back_item = ItemCreator.create(Material.BARRIER, title1, list1);
        addtopack_item = ItemCreator.create(Material.BARRIER, title2, list2);
        editname_item = ItemCreator.create(Material.PAPER, title3, list3);
        addamount_item = ItemCreator.create(Material.GREEN_WOOL, title4, list4);
        subtructamount_item = ItemCreator.create(Material.RED_WOOL, title5, list5);
    }
}
