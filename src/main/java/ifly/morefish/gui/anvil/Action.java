package ifly.morefish.gui.anvil;

import ifly.morefish.gui.helper.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class Action {

    String result;
    Inventory inventory;
    Player player;
    public Action(Player player){
        this.player = player;


        inventory =  Bukkit.createInventory(player, InventoryType.ANVIL);

//        inventory.setMaximumRepairCost(0);
        setInventoryItems();
        player.openInventory(inventory);
    }

    public void closeInventory(InventoryCloseEvent e){
        Action v = AnvilController.anvils.remove((Player) e.getPlayer());
        if(v != null)
        {
            e.getInventory().setItem(0, null);
        }
    }
    public void inventoryClickEvent(InventoryClickEvent e){
        if (e.getSlot() == 2){
            ItemStack itemStack = getInventory().getItem(2);
            if (itemStack != null && itemStack.hasItemMeta()){
                ItemMeta meta = itemStack.getItemMeta();
                setResult(meta.getDisplayName());
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
        getInventory().setItem(0, ItemCreator.create(Material.PAPER, "Command"));
    }

    public Player getPlayer() {
        return player;
    }
    public void firstItemEditTitle(String text){
        ItemStack itemStack = getInventory().getItem(0);
        if (itemStack != null){
            ItemMeta meta = itemStack.getItemMeta();
            meta.setDisplayName(text);
            itemStack.setItemMeta(meta);
        }
    }
}
