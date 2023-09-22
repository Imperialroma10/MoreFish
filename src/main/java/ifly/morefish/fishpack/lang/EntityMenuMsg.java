package ifly.morefish.fishpack.lang;

import ifly.morefish.gui.helper.ItemCreator;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class EntityMenuMsg
{
    public final String title;
    public final ItemStack back_item;
    public final String[] desc;

    public EntityMenuMsg(ConfigurationSection section)
    {
        title = section.getString("title");
        String title1 = section.getString("back-item.title");
        String[] list1 = section.getStringList("back-item.description").toArray(new String[0]);

        desc = section.getStringList("entity-items.description").toArray(new String[0]);

        back_item = ItemCreator.create(Material.BARRIER, title1, list1);
    }
}
