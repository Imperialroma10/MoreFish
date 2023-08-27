package ifly.morefish.fishpack.lang;

import ifly.morefish.main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Lang {

    String filename = "lang.yml";
    private static Lang lang;

    public final String notenoughspace;
    public final Boolean enablechatprefix;
    public final String pluginchatprefix;
    public final String caughtfish;
    public final String openpackmessage;
    public static Lang getLang(){
        if (lang == null){
            lang = new Lang();
        }
        return lang;
    }

    public Lang(){
        File f = new File(main.mainPlugin.getDataFolder()+File.separator+filename);
        if (!f.exists()){
            main.mainPlugin.saveResource(filename, false);
        }
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(f);

        enablechatprefix = configuration.getBoolean("enable-plugin-prefix");
        pluginchatprefix = configuration.getString("plugin-prefix");
        caughtfish = configuration.getString("caught-fish-message");
        openpackmessage = configuration.getString("open-pack-message");
        notenoughspace = configuration.getString("not-enough-space");
    }

    public static String getMessage(String message){
        return lang.enablechatprefix ? lang.pluginchatprefix+message : message;
    }


}
