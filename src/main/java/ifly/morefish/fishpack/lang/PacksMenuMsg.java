package ifly.morefish.fishpack.lang;

import ifly.morefish.gui.helper.ItemCreator;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class PacksMenuMsg {

    public final String title;
    public final ItemStack back;
    private final String[] lore;
    public final ItemStack create_new;

    public PacksMenuMsg(ConfigurationSection section)
    {
        title = section.getString("title", "");
        lore = section.getStringList("list-template-items.list").toArray(new String[0]);

        String title1 = section.getString("back-item.title", "");
        String[] list1 = section.getStringList("back-item.description").toArray(new String[0]);

        String title2 = section.getString("creation-pack.title", "");
        String[] list2 = section.getStringList("creation-pack.description").toArray(new String[0]);

        back = ItemCreator.create(Material.BARRIER, title1, list1);
        create_new = ItemCreator.create(Material.COMMAND_BLOCK, title2, list2);
    }

    public ItemStack item(String name, int dropchance, String filename)
    {
        for(int i = 0; i < lore.length; i++)
        {
            if(lore[i].contains("{chance}"))
            {
                lore[i] = lore[i].replace("{chance}", String.valueOf(dropchance));
            }

            if(lore[i].contains("{filename}"))
            {
                lore[i] = lore[i].replace("{filename}", String.valueOf(filename));
            }
        }

        return ItemCreator.create(Material.CHEST, name, lore);
    }
}
