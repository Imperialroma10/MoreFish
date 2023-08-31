package ifly.morefish.fishpack.lang;

import ifly.morefish.main;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class MenuMsgs {

    private static MenuMsgs msg;
    public final MainMenuMsg MainMenu;
    public final PacksMenuMsg PacksMenuMsg;
    public final EditMenuMsg EditMenu;
    public final RewardsMenuMsg RewardsMenu;

    public static MenuMsgs get()
    {
        if(msg == null)
        {
            msg = new MenuMsgs();
        }
        return msg;
    }

    private MenuMsgs()
    {
        Plugin plg = main.mainPlugin;
        File f = new File(plg.getDataFolder() + File.separator + "Menus.yml");
        YamlConfiguration conf = YamlConfiguration.loadConfiguration(f);

        MainMenu = new MainMenuMsg(conf.getConfigurationSection("menus.main-menu"));
        PacksMenuMsg = new PacksMenuMsg(conf.getConfigurationSection("menus.packs-menu"));
        EditMenu = new EditMenuMsg(conf.getConfigurationSection("menus.edit-pack-menu"));
        RewardsMenu = new RewardsMenuMsg(conf.getConfigurationSection("menus.rewards-menu"));
    }
}