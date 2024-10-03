package ifly.morefish.gui.menus.admin;

import com.liba.gui.Gui;
import com.liba.gui.MenuSlot;
import com.liba.gui.buttons.BackButton;
import com.liba.utils.ItemUtil;
import ifly.morefish.gui.helper.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ColorableArmorMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class AboutPlugin extends Gui {
    public AboutPlugin(Gui gui) {
        super("About the plugin", 3, gui);
    }

    @Override
    public void setInventoryItems() {

        addSlot(10, new MenuSlot(ItemCreator.create(Material.PAPER, "§b Plugin info",
                "  §c§lHere's how it works:" ,
                        "§aYou cast your fishing rod and wait for a bite. When you reel it in," ,
                        "§athere's a chance you'll receive a chest containing a reward. " ,
                        "§aAll of this comes with a beautiful animation."), e->{

        e.setCancelled(true);
        }));



        addSlot(12, new MenuSlot(ItemCreator.create(Material.OAK_SIGN, "§c§lIf you liked the plugin, please write a review §f(§a§lClick to get the link§f)",
                "§c§lYour feedback is very important to us",
                "§b§lLeft click to see links"), e->{

            e.getWhoClicked().sendMessage("§cLink 1 (spigotmc.org) ",
                    "§ahttps://www.spigotmc.org/resources/111966/ §f( §bClick url to open §f) ","",
                    "§cLink 2 (bukkit.org)",
                    "§ahttps://legacy.curseforge.com/minecraft/bukkit-plugins/fishrewards §f( §bClick url to open §f)");
            e.getWhoClicked().closeInventory();
            e.setCancelled(true);
        }));
        ItemStack itemStack = new ItemStack(Material.LEATHER_HELMET);
        ColorableArmorMeta meta = (ColorableArmorMeta) itemStack.getItemMeta();
        if (meta != null) {
            meta.setColor(Color.PURPLE);
            itemStack.setItemMeta(meta);
        }

        addSlot(14, new MenuSlot(ItemUtil.create(itemStack, "§bOur discord", "§cYou should go there if",
                "§aProblems setting up the plugin",
                "§aYou found a bug",
                "§aThere is a suggestion for plugins",
                "§aAnd just sit)",
                "§cClick to see the link"), e->{
            e.getWhoClicked().sendMessage("§bhttp://discord.gg/fsEZEnE58g §f( §bclick url to open §f)");
            e.getWhoClicked().closeInventory();
            e.setCancelled(true);
        }));
        addSlot(16, new MenuSlot(ItemUtil.create(new ItemStack(Material.DIAMOND), "§bSupport me",
                "§aIf you find my work helpful and would like to buy me a coffee",
                "§ayour support would mean a lot.",
                "§aThanks so much!",
                "§apatreon.com/PluginDEV",
                "§cClick to see the link"
        ), e->{
            e.getWhoClicked().sendMessage("§bhttps://patreon.com/PluginDEV §f( §bclick url to open §f)");
            e.getWhoClicked().closeInventory();
            e.setCancelled(true);
        }));
        addSlot(26, new MenuSlot(ItemCreator.create(Material.DIAMOND, "§bPlugin author §aImperialroma10 §f(§aRoman43435§f)"), e->{
            e.setCancelled(true);
        }));

        addSlot(18, new BackButton(new ItemStack(Material.BARRIER),getBackGui(),"Back"));
    }
}
