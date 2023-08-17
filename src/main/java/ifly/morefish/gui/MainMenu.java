package ifly.morefish.gui;

import ifly.morefish.fishpack.FishController;
import ifly.morefish.gui.helper.ItemCreator;
import ifly.morefish.main;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MainMenu implements Listener {
    static public Inventory inventory = Bukkit.createInventory(null, 3*9, "Main menu");

    PackListMenu packListMenu;

    FishController controller;
    public MainMenu(FishController controller){
        this.controller = controller;
        inventory.setItem( 11,ItemCreator.create(Material.CHEST, "Pack menu"));

        packListMenu = new PackListMenu(controller);

        main.mainPlugin.getServer().getPluginManager().registerEvents(packListMenu, main.mainPlugin);
    }

    @EventHandler
    public void clickInventory(InventoryClickEvent e){
        if (e.getInventory() == inventory){
            if (e.getRawSlot() == 11){
                e.getWhoClicked().openInventory(packListMenu.getPlayerInventory((Player) e.getWhoClicked()));
            }
            e.setCancelled(true);
        }
    }
}
