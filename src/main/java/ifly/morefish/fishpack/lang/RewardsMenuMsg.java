package ifly.morefish.fishpack.lang;

import ifly.morefish.gui.helper.ItemCreator;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RewardsMenuMsg {

    public final String title;
    public final ItemStack save_item;
    public final ItemStack back_item;
    public final ItemStack additem;
    public final ItemStack addentity;
    public final ItemStack addcommand;
    public final String[] lore;
    private final List<String> components;

    public RewardsMenuMsg(ConfigurationSection section) {
        title = section.getString("title");
        String title1 = section.getString("save-item.title");
        String[] list1 = section.getStringList("save-item.description").toArray(new String[0]);

        String title2 = section.getString("back-item.title");
        String[] list2 = section.getStringList("back-item.description").toArray(new String[0]);

        String title3 = section.getString("additem-item.title");
        String[] list3 = section.getStringList("additem-item.description").toArray(new String[0]);

        String title4 = section.getString("addentity-item.title");
        String[] list4 = section.getStringList("addentity-item.description").toArray(new String[0]);

        String title5 = section.getString("addcommand-item.title");
        String[] list5 = section.getStringList("addcommand-item.description").toArray(new String[0]);

        lore = section.getStringList("list-template-items").toArray(new String[0]);
        components = new ArrayList<>(3);
        Collections.addAll(components, lore);

        save_item = ItemCreator.create(Material.COMMAND_BLOCK, title1, list1);
        back_item = ItemCreator.create(Material.BARRIER, title2, list2);

        additem = ItemCreator.create(Material.ITEM_FRAME, title3, list3);
        addentity = ItemCreator.create(Material.ZOMBIE_HEAD, title4, list4);
        addcommand = ItemCreator.create(Material.PAPER, title5, list5);
    }

    public void makeLore(ItemStack is, int chance) {
        List<String> list = new ArrayList<>(components);
        ItemMeta meta = is.getItemMeta();
        for (int i = 0; i < list.size(); i++) {
            list.set(i, list.get(i).replace("{1}", String.valueOf(chance)));
        }
        meta.setLore(list);
        is.setItemMeta(meta);
    }
}
