package ifly.morefish.fishpack.lang;

import ifly.morefish.gui.helper.ItemCreator;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class RewardsMenuMsg {

    public final String title;
    public final ItemStack save_item;
    public final ItemStack back_item;

    public RewardsMenuMsg(ConfigurationSection section)
    {
        title = section.getString("title");
        String title1 = section.getString("save-item.title");
        String[] list1 = section.getStringList("save-item.description").toArray(new String[0]);

        String title2 = section.getString("back-item.title");
        String[] list2 = section.getStringList("back-item.description").toArray(new String[0]);

        save_item = ItemCreator.create(Material.COMMAND_BLOCK, title1, list1);
        back_item = ItemCreator.create(Material.BARRIER, title2, list2);
    }
}
