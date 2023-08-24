package ifly.morefish.gui;

import ifly.morefish.gui.menus.Action;
import ifly.morefish.gui.menus.EditMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.Map;

public class MenuListener implements Listener {

    @EventHandler
    public void playerClickEvent(InventoryClickEvent e){
        InventoryHolder holder = e.getInventory().getHolder();
        if (holder instanceof Menu){
            Menu menu = (Menu) holder;
            menu.handleInventoryClick(e);
        }
        if(e.getInventory() instanceof AnvilInventory)
        {
            AnvilInventory anvinv = (AnvilInventory)e.getInventory();
            Player p = (Player)e.getWhoClicked();
            Map.Entry<Menu, Action> menu = Menu.getByPlayer(p);
            if(menu != null && menu.getValue() == Action.RenamePack)
            {
                EditMenu edit = (EditMenu)menu.getKey();
                e.setCancelled(true);
                if(e.getSlot() == 2 && anvinv.getResult() != null)
                {
                    edit.getPack().setDisplayname(anvinv.getRenameText());
                    edit.open();
                    Menu.actions.remove(menu.getKey());
                }
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e)
    {
        Map.Entry<Menu, Action> element = Menu.getByPlayer(e.getPlayer());
        if(element != null)
        {
            Menu.actions.remove(element.getKey());
        }
    }

}
