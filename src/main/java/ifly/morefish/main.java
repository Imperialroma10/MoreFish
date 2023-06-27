package ifly.morefish;

import ifly.morefish.datastorage.StorageCreator;
import ifly.morefish.events.FishEvent;
import ifly.morefish.fishpack.FishMain;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class main extends JavaPlugin {

    public static Plugin mainPlugin;
    StorageCreator storage;
    FishMain fishMain;
    @Override
    public void onEnable() {
        mainPlugin = this;
        storage = new StorageCreator();

       fishMain = new FishMain(storage);

        getServer().getPluginManager().registerEvents(new FishEvent(fishMain), this);
    }

    @Override
    public void onDisable() {

    }
}
