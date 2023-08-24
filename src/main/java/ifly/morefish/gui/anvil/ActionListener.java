package ifly.morefish.gui.anvil;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;

import java.util.Map;

public class ActionListener implements Listener {

    @EventHandler
    public void closeInventory(InventoryCloseEvent e){
        for (Map.Entry<Player, Action> actionEntry : AnvilController.anvils.entrySet()){
            if (actionEntry.getKey().equals(e.getPlayer())){
                actionEntry.getValue().closeInventory(e);
            }
        }
    }
    @EventHandler
    public void inventoryClick(InventoryClickEvent e){
        if (AnvilController.anvils.get((Player) e.getWhoClicked()) != null){
            ifly.morefish.gui.anvil.Action action = AnvilController.anvils.get((Player) e.getWhoClicked());
            if (action.getInventory() == e.getInventory()){
                action.inventoryClickEvent(e);
            }
        }
    }
    @EventHandler
    public void leavePlayer(PlayerQuitEvent e){
        if (AnvilController.anvils.get(e.getPlayer()) != null){
            AnvilController.anvils.remove(e.getPlayer());
        }
    }

}
