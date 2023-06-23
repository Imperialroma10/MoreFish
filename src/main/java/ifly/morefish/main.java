package ifly.morefish;

import ifly.morefish.events.FishEvent;
import ifly.morefish.fishpack.FishMain;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class main extends JavaPlugin {

    public static Plugin mainPlugin;

    @Override
    public void onEnable() {
        mainPlugin = this;
        FishMain fishMain = new FishMain();
        getServer().getPluginManager().registerEvents(new FishEvent(fishMain), this);
    }

    @Override
    public void onDisable() {

    }
}
