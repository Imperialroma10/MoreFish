package ifly.morefish.gui.anvil;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Map;

public class ActionListener implements Listener {

    @EventHandler
    public void closeInventory(InventoryCloseEvent e){
        for (Map.Entry<Player, Action> actionEntry : AnvilController.anvils.entrySet()){
            if (actionEntry.getKey() == e.getPlayer()){
                actionEntry.getValue().closeInventory(e);
            }
        }
    }
    @EventHandler
    public void inventoryClick(InventoryClickEvent e){
        Player p = (Player)e.getWhoClicked();
        Action action = AnvilController.anvils.getOrDefault(p, null);
        if (action != null){
            if (action.getInventory() == e.getInventory()){
                action.inventoryClickEvent(e);
            }
        }
    }
    @EventHandler
    public void leavePlayer(PlayerQuitEvent e){
        AnvilController.anvils.remove(e.getPlayer());
    }

}
