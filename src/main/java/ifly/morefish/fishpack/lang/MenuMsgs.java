package ifly.morefish.fishpack.lang;

import ifly.morefish.main;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MenuMsgs {

    private static MenuMsgs msg;
    public final MainMenuMsg MainMenu;
    public final PacksMenuMsg PacksMenuMsg;
    public final EditMenuMsg EditMenu;
    public final RewardsMenuMsg RewardsMenu;
    public final EditItemMenuMsg EditItemMenu;
    public final PutItemMenuMsg PutItemMenu;
    public final EntityMenuMsg EntityMenu;
    public final EditEntityMenuMsg EditEntityMenu;

    private MenuMsgs() {
        Plugin plg = main.getPlugin();
        File f = new File(plg.getDataFolder() + File.separator + "Menus.yml");
        boolean exists = f.exists();
        if (!exists) {
            plg.saveResource("Menus.yml", false);
        }
        YamlConfiguration clang = YamlConfiguration.loadConfiguration(f);
        if (exists) {
            InputStream reader = plg.getResource("Menus.yml");
            if (reader != null) {
                InputStreamReader sreader = new InputStreamReader(reader);
                double ver = YamlConfiguration.loadConfiguration(sreader).getDouble("ver");
                if (clang.getBoolean("update") && clang.getDouble("ver") != ver) {
                    long unixtime = System.currentTimeMillis() / 1000;
                    try {
                        String path = plg.getDataFolder().getPath();
                        Files.move(Paths.get(path + File.separator + "Menus.yml"), Paths.get(path + File.separator + "Menus_backup-" + unixtime + ".yml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    plg.getLogger().info("Updating Menus.yml");
                    plg.saveResource("Menus.yml", true);
                    clang = YamlConfiguration.loadConfiguration(f);
                }
            }
        }

        MainMenu = new MainMenuMsg(clang.getConfigurationSection("menus.main-menu"));
        PacksMenuMsg = new PacksMenuMsg(clang.getConfigurationSection("menus.packs-menu"));
        EditMenu = new EditMenuMsg(clang.getConfigurationSection("menus.edit-pack-menu"));
        RewardsMenu = new RewardsMenuMsg(clang.getConfigurationSection("menus.rewards-menu"));
        EditItemMenu = new EditItemMenuMsg(clang.getConfigurationSection("menus.edit-item-menu"));
        PutItemMenu = new PutItemMenuMsg(clang.getConfigurationSection("menus.put-item-menu"));
        EntityMenu = new EntityMenuMsg(clang.getConfigurationSection("menus.entity-menu"));
        EditEntityMenu = new EditEntityMenuMsg(clang.getConfigurationSection("menus.edit-entity-menu"));
    }

    public static MenuMsgs get() {
        if (msg == null) {
            msg = new MenuMsgs();
        }
        return msg;
    }
}