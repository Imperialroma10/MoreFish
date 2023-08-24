package ifly.morefish.gui.anvil;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

public abstract class Action {

    String result;
    InventoryView invview;
    Player player;
    public Action(Player player){
        invview = player.openAnvil(player.getLocation(), true);

        Bukkit.broadcast(Component.text("Object created"));
    }
    public void closeInventory(InventoryCloseEvent e){

        AnvilController.anvils.remove(e.getPlayer());
        Bukkit.broadcast(Component.text("Object removed"));
    }
    public void PrepareEvent(PrepareAnvilEvent e){

    }
    public abstract void addAction();

    public InventoryView getInvview() {
        return invview;
    }
}
