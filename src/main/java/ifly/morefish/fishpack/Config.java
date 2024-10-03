package ifly.morefish.fishpack;

import ifly.morefish.main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class Config {

    private static Config config;

    public final String notenoughspace;
    public final Boolean enablechatprefix;
    public final String pluginchatprefix;
    public final String caughtfish;
    public final String openpackmessage;

    public final String itemrewardmessage;
    public final String entityrewardmessage;
    public final String commandrewardmessage;

    private Config() {
        Plugin plg = main.mainPlugin;
        plg.saveDefaultConfig();
        FileConfiguration conf = plg.getConfig();

        enablechatprefix = conf.getBoolean("enable-plugin-prefix");
        pluginchatprefix = conf.getString("plugin-prefix");
        caughtfish = conf.getString("caught-fish-message");
        openpackmessage = conf.getString("open-pack-message");
        notenoughspace = conf.getString("not-enough-space");

        itemrewardmessage = conf.getString("item-reward-message");
        entityrewardmessage = conf.getString("entity-reward-message");
        commandrewardmessage = conf.getString("command-reward-message");

    }

    public static Config getConfig() {
        if (config == null) {
            config = new Config();
        }
        return config;
    }

    public static String getMessage(String message) {
        return config.enablechatprefix ? config.pluginchatprefix + message : message;
    }


}
