package ifly.morefish;


import com.liba.Liba;
import com.liba.version.VersionChecker;
import ifly.morefish.datastorage.FileStorage;
import ifly.morefish.datastorage.StorageCreator;
import ifly.morefish.events.FishEvent;
import ifly.morefish.fishpack.ConfigChecker;
import ifly.morefish.fishpack.FishController;
import ifly.morefish.fishpack.lang.MenuMsgs;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.plugin.java.JavaPlugin;

public final class main extends JavaPlugin {

    private static main plugin;

    public FishController controller;
    StorageCreator storage;
    ConfigChecker checker;

    @Override
    public void onEnable() {

        plugin = this;
        checker = new ConfigChecker();
        Liba liba = new Liba(plugin, checker);

        storage = new StorageCreator();
        FileStorage storageStorage = (FileStorage) storage.getStorage();
        storageStorage.copy();
        MenuMsgs.get();
        controller = new FishController(storage);


        Metrics metrics = liba.registerMetrica(19862);

        VersionChecker versionChecker = liba.registerVersionChecker(6, "https://www.curseforge.com/minecraft/bukkit-plugins/fishrewards");
        //versionChecker.setMessage("§9§l[§aFishRewards§9] §b " + versionChecker.getMessage());

        metrics.addCustomChart(new SimplePie("packs_count", () -> {
            return String.valueOf(controller.getPackList().size());
        }));
        metrics.addCustomChart(new SimplePie("caughtpacks", () -> {
            return String.valueOf(controller.getPlayerStatistic().getCaughtPacks());
        }));


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

    public ConfigChecker getChecker() {
        return checker;
    }

    public static main getPlugin() {
        return plugin;
    }
}
