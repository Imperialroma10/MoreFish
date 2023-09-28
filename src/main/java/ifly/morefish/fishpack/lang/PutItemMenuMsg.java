package ifly.morefish.fishpack.lang;

import ifly.morefish.gui.helper.ItemCreator;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class PutItemMenuMsg {
    public final String title;
    public final ItemStack back_item;
    public final ItemStack put_item;

    public PutItemMenuMsg(ConfigurationSection section) {
        title = section.getString("title");

        String title1 = section.getString("back-item.title");
        String[] list = section.getStringList("back-item.title").toArray(new String[0]);

        String title2 = section.getString("add-to-pack-item.title");
        String[] list2 = section.getStringList("add-to-pack-item.title").toArray(new String[0]);

        back_item = ItemCreator.create(Material.BARRIER, title1, list);
        put_item = ItemCreator.create(Material.COMMAND_BLOCK, title2, list2);

    }
}
