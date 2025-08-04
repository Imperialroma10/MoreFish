package ifly.morefish.fishpack;


import com.liba.utils.file.FileChecker;
import ifly.morefish.main;

import java.io.File;


public class ConfigChecker extends FileChecker {
    public ConfigChecker() {
        super(main.getPlugin().getDataFolder() + File.separator + "config.yml", "#------------------------------------------------------------------------------------------\n" +
                "#                 author: Imperialroma10\n" +
                "#     our project: https://www.curseforge.com/members/imperialroma10/projects\n" +
                "#------------------------------------------------------------------------------------------\n" +
                "#      In discord we can help with customization of plugins, as well as if you found a bug write us\n" +
                "#              Discord : http://discord.gg/fsEZEnE58g\n" +
                "#------------------------------------------------------------------------------------------\n" +
                "#              If you like our plugin support us with a cup of coffee\n" +
                "#             Donate:   https://www.patreon.com/c/PluginDEV/membership\n\n" +
                "#        Please leave a review of the plugin https://www.spigotmc.org/resources/111966/\n" +
                "#------------------------------------------------------------------------------------------");
    }

    @Override
    public void needle() {

        addParam("enable-plugin-prefix", true);
        addParam("plugin-prefix", "&9&l[&aFishRewards&9] &b");
        addParam("caught-fish-message", "You caught [pack] ");
        addParam("open-pack-message", "You opened [pack]");
        addParam("not-enough-space", "You have not enough space!");

        addParam("enable-pack-message", true);

        addParam("item-reward-message", "&bYou received a &a{itemname} &bamount: &a{count}");
        addParam("entity-reward-message", "&4There are entities hovering nearby.");
        addParam("command-reward-message", "&bYou received the reward, the command was executed.");
        addParam("no-right", "&bYou don't have permission");

    }
}
