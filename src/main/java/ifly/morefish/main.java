package ifly.morefish;

import ifly.morefish.datastorage.StorageCreator;
import ifly.morefish.events.FishEvent;
import ifly.morefish.fishpack.FishController;
import ifly.morefish.fishpack.lang.Lang;
import ifly.morefish.gui.MainMenu;
import ifly.morefish.gui.PackListMenu;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class main extends JavaPlugin {

    public static Plugin mainPlugin;
    StorageCreator storage;
    FishController controller;
    @Override
    public void onEnable() {
       mainPlugin = this;
       Lang.getLang();
       storage = new StorageCreator();
       controller = new FishController(storage);
       FishEvent fishEvents = new FishEvent(controller);
       getServer().getPluginManager().registerEvents(new MainMenu(controller), this);
       getServer().getPluginManager().registerEvents(fishEvents, this);
       getCommand("fishrewards").setExecutor(fishEvents);
    }

    @Override
    public void onDisable() {

    }
}
