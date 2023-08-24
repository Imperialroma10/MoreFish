package ifly.morefish.gui;

import ifly.morefish.gui.menus.Action;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.HashMap;
import java.util.Map;

public abstract class Menu implements InventoryHolder {


    public static final HashMap<Menu, Action> actions = new HashMap<>();

    public static Map.Entry<Menu, Action> getByPlayer(Player p)
    {
        for(Map.Entry<Menu, Action> el: actions.entrySet())
        {
            if(el.getKey().getPlayerMenuUtil().getOwner() == p)
            {
                return el;
            }
        }
        return null;
    }

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
            this.inventory = Bukkit.createInventory(this, getSlots()*9, getMenuName());

            setMenuItems();

            playerMenuUtil.getOwner().openInventory(inventory);
        }else{
            Bukkit.broadcast(Component.text("No permission"));
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
