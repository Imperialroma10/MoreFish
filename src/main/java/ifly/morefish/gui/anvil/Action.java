package ifly.morefish.gui.anvil;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class Action {

    String result;
    Inventory inventory;
    Player player;
    public Action(Player player){
        this.player = player;
        inventory = player.openAnvil(player.getLocation(), true).getTopInventory();
        setInventoryItems();
    }

    public void closeInventory(InventoryCloseEvent e){
        AnvilController.anvils.remove((Player) e.getPlayer());
    }
    public void inventoryClickEvent(InventoryClickEvent e){
        if (e.getSlot() == 2){
            ItemStack itemStack = getInventory().getItem(2);
            if (itemStack != null && itemStack.hasItemMeta()){
                ItemMeta meta = itemStack.getItemMeta();
                setResult(((TextComponent)meta.displayName()).content());
                e.getWhoClicked().closeInventory();
                AnvilController.anvils.remove((Player) e.getWhoClicked());
                addAction();
            }
        }
        e.setCancelled(true);
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public abstract void addAction();

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventoryItems(){
        getInventory().setItem(0, new ItemStack(Material.PAPER));
    }

    public Player getPlayer() {
        return player;
    }
}
