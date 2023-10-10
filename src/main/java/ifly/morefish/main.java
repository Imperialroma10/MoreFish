package ifly.morefish;

import ifly.morefish.datastorage.FileStorage;
import ifly.morefish.datastorage.StorageCreator;
import ifly.morefish.events.FishEvent;
import ifly.morefish.fishpack.Config;
import ifly.morefish.fishpack.FishController;
import ifly.morefish.fishpack.lang.MenuMsgs;
import ifly.morefish.gui.MenuListener;
import ifly.morefish.gui.PlayerMenuUtil;
import ifly.morefish.utils.VersionChecker;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.Bukkit;
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
        Metrics metrics = new Metrics(this, 19862);
        VersionChecker checker = new VersionChecker(this, 111966);

        if (checker.isUpgrade()) {

            Bukkit.getLogger().info("[" + this.getDescription().getName() + "] A new plugin update is available. Download link: https://www.spigotmc.org/resources/111966/");

        }

        Config.getConfig();
        storage = new StorageCreator();
        FileStorage storageStorage = (FileStorage) storage.getStorage();
        storageStorage.copy();
        MenuMsgs.get();

        controller = new FishController(storage);
        FishEvent fishEvents = new FishEvent(controller);
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
        getServer().getPluginManager().registerEvents(fishEvents, this);
        getCommand("fishrewards").setExecutor(fishEvents);

        metrics.addCustomChart(new SimplePie("packs_count", () -> {
            return String.valueOf(controller.getPackList().size());
        }));
        metrics.addCustomChart(new SimplePie("caughtpacks", () -> {
            return String.valueOf(controller.getPlayerStatistic().getCaughtPacks());
        }));

    }

    @Override
    public void onDisable() {
        controller.saveALlPacks();
    }
}
