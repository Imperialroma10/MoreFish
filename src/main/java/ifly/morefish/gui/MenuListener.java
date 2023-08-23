package ifly.morefish.gui;

import ifly.morefish.main;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.InventoryHolder;

public class MenuListener implements Listener {

    @EventHandler
    public void playerClickEvent(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();
        InventoryHolder holder = e.getInventory().getHolder();
        if (holder instanceof Menu){
            Menu menu = (Menu) holder;
            menu.handleInventoryClick(e);
        }
    }

}
