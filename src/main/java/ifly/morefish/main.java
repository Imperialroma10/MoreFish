package ifly.morefish;


import ifly.imperial.Liba;
import ifly.morefish.datastorage.FileStorage;
import ifly.morefish.datastorage.StorageCreator;
import ifly.morefish.events.FishEvent;
import ifly.morefish.fishpack.Config;
import ifly.morefish.fishpack.FishController;
import ifly.morefish.fishpack.lang.MenuMsgs;
import ifly.morefish.gui.MenuListener;
import ifly.morefish.gui.PlayerMenuUtil;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class main extends JavaPlugin {

    public static Plugin mainPlugin;
    public static HashMap<Player, PlayerMenuUtil> menuUtilHashMap = new HashMap<>();
    public FishController controller;
    StorageCreator storage;

    public static PlayerMenuUtil getPlayerUtils(Player player) {
        PlayerMenuUtil playerMenuUtil;
        if (menuUtilHashMap.containsKey(player)) {
            playerMenuUtil = menuUtilHashMap.get(player);
        } else {
            menuUtilHashMap.put(player, new PlayerMenuUtil(player));
            playerMenuUtil = menuUtilHashMap.get(player);
        }
        return playerMenuUtil;
    }

    @Override
    public void onEnable() {
        mainPlugin = this;
        Config.getConfig();

        storage = new StorageCreator();
        FileStorage storageStorage = (FileStorage) storage.getStorage();
        storageStorage.copy();
        MenuMsgs.get();

        controller = new FishController(storage);

        Liba liba = new Liba(mainPlugin, 111966, 19862);
        if (liba.getChecker() != null){
            liba.getChecker().setMessage(Config.getMessage(liba.getChecker().getMessage()));
        }

        Metrics metrics = liba.getMetrics();
        if (metrics != null){
            metrics.addCustomChart(new SimplePie("packs_count", () -> {
                return String.valueOf(controller.getPackList().size());
            }));
            metrics.addCustomChart(new SimplePie("caughtpacks", () -> {
                return String.valueOf(controller.getPlayerStatistic().getCaughtPacks());
            }));
        }



        FishEvent fishEvents = new FishEvent(controller);
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
        getServer().getPluginManager().registerEvents(fishEvents, this);

        getCommand("fishrewards").setExecutor(fishEvents);



    }

    @Override
    public void onDisable() {
        if (controller != null){
            controller.saveALlPacks();
        }

    }
}
