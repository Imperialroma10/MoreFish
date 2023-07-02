package ifly.morefish;

import ifly.morefish.datastorage.StorageCreator;
import ifly.morefish.events.FishEvent;
import ifly.morefish.fishpack.FishMain;
import ifly.morefish.fishpack.lang.Lang;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class main extends JavaPlugin {

    public static Plugin mainPlugin;
    StorageCreator storage;
    FishMain fishMain;
    @Override
    public void onEnable() {
       mainPlugin = this;
       Lang.getLang();
       storage = new StorageCreator();
       fishMain = new FishMain(storage);
       FishEvent fishEvents = new FishEvent(fishMain);
       getServer().getPluginManager().registerEvents(fishEvents, this);
       getCommand("reload-pack").setExecutor(fishEvents);
    }

    @Override
    public void onDisable() {

    }
}
