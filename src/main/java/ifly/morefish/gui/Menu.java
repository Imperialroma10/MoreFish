package ifly.morefish.gui;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public abstract class Menu implements InventoryHolder {



    protected Inventory inventory;

    PlayerMenuUtil playerMenuUtil;

    public Menu(PlayerMenuUtil playerMenuUtil){
        this.playerMenuUtil = playerMenuUtil;
    }
    public abstract String getMenuName();

    public abstract int getSlots();

    public abstract void handleInventoryClick(InventoryClickEvent e);

    public abstract void setMenuItems();


    public void open(){
        if (playerMenuUtil.getOwner().hasPermission("fishrewarads.admin")) {
            this.inventory = Bukkit.createInventory(this, getSlots()*9, "§4"+getMenuName());

            setMenuItems();

            playerMenuUtil.getOwner().openInventory(inventory);
        }else{
            playerMenuUtil.getOwner().sendMessage("No permission");

        }

    }


    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public PlayerMenuUtil getPlayerMenuUtil() {
        return playerMenuUtil;
    }
}
