package ifly.morefish;


import com.liba.Liba;
import com.liba.utils.Debug;
import ifly.morefish.datastorage.FileStorage;
import ifly.morefish.datastorage.StorageCreator;
import ifly.morefish.events.FishEvent;
import ifly.morefish.fishpack.Config;
import ifly.morefish.fishpack.FishController;
import ifly.morefish.fishpack.lang.MenuMsgs;

import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class main extends JavaPlugin {

    public static Plugin mainPlugin;

    public FishController controller;
    StorageCreator storage;


    @Override
    public void onEnable() {
        Debug.setDebug(false);
        mainPlugin = this;
        Config.getConfig();

        storage = new StorageCreator();
        FileStorage storageStorage = (FileStorage) storage.getStorage();
        storageStorage.copy();
        MenuMsgs.get();

        controller = new FishController(storage);

        Liba liba = new Liba(mainPlugin, 111966, new Metrics(this,19862));

        if (liba.getChecker() != null) {
            liba.getChecker().setMessage(Config.getMessage(liba.getChecker().getMessage()));
        }

        Metrics metrics = liba.getMetrics();

        if (metrics != null) {
            metrics.addCustomChart(new SimplePie("packs_count", () -> {
                return String.valueOf(controller.getPackList().size());
            }));
            metrics.addCustomChart(new SimplePie("caughtpacks", () -> {
                return String.valueOf(controller.getPlayerStatistic().getCaughtPacks());
            }));

        }


        FishEvent fishEvents = new FishEvent(controller);

        getServer().getPluginManager().registerEvents(fishEvents, this);

        getCommand("fishrewards").setExecutor(fishEvents);


    }

    @Override
    public void onDisable() {
        if (controller != null) {
            //controller.saveALlPacks();
        }

    }
}
