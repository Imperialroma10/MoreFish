package ifly.morefish;

import ifly.morefish.datastorage.StorageCreator;
import ifly.morefish.events.FishEvent;
import ifly.morefish.fishpack.FishController;
import ifly.morefish.fishpack.lang.Lang;
import ifly.morefish.gui.MenuListener;
import ifly.morefish.gui.PlayerMenuUtil;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class main extends JavaPlugin {

    public static Plugin mainPlugin;
    StorageCreator storage;
    public FishController controller;

   public static HashMap<Player, PlayerMenuUtil> menuUtilHashMap = new HashMap<>();
    @Override
    public void onEnable() {
       mainPlugin = this;
       Lang.getLang();
       storage = new StorageCreator();
       controller = new FishController(storage);
       FishEvent fishEvents = new FishEvent(controller);
       getServer().getPluginManager().registerEvents(new MenuListener(), this);
       getServer().getPluginManager().registerEvents(fishEvents, this);
       getCommand("fishrewards").setExecutor(fishEvents);
    }

    @Override
    public void onDisable() {

    }

    public static PlayerMenuUtil getPlayerUtils(Player player){
        PlayerMenuUtil playerMenuUtil;
        if (menuUtilHashMap.containsKey(player)){
            playerMenuUtil = menuUtilHashMap.get(player);
        }else{
            menuUtilHashMap.put(player, new PlayerMenuUtil(player));
            playerMenuUtil = menuUtilHashMap.get(player);
        }
        return playerMenuUtil;
    }
}
