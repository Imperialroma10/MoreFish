package ifly.morefish.fishpack.lang;

import ifly.morefish.fishpack.pack.Pack;
import ifly.morefish.gui.helper.ItemCreator;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class PacksMenuMsg {

    public final String title;
    public final ItemStack back;
    public final ItemStack create_new;
    private final String[] lore;


    public String title2;
    public String[] list2;
    public PacksMenuMsg(ConfigurationSection section) {
        title = section.getString("title", "");
        lore = section.getStringList("list-template-items.list").toArray(new String[0]);

        String title1 = section.getString("back-item.title", "");
        String[] list1 = section.getStringList("back-item.description").toArray(new String[0]);

        title2 = section.getString("creation-pack.title", "");
        list2 = section.getStringList("creation-pack.description").toArray(new String[0]);

        back = ItemCreator.create(Material.BARRIER, title1, list1);
        create_new = ItemCreator.create(Material.COMMAND_BLOCK, title2, list2);
    }

    public ItemStack item(String name, Pack pack) {
        String[] list = lore.clone();
        ArrayList<String> lore = new ArrayList<>();
        for (int i = 0; i < list.length; i++) {
            if (list[i].contains("{chance}")) {
                lore.add(list[i].replace("{chance}", String.valueOf(pack.getDropChance())));
                continue;
            }
            if (list[i].contains("{filename}")) {
                lore.add(list[i].replace("{filename}", String.valueOf(pack.getName())));
                continue;
            }
            lore.add(list[i]);
        }
        if (pack.isEnablepermission()) {
            lore.add("§aYou need a permit to get one : §b" + pack.getEnablepermission());
        } else {
            lore.add("§aAny player can get one.");
        }
        return ItemCreator.create(Material.CHEST, name, lore);
    }
}
