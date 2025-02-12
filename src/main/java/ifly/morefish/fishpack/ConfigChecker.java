package ifly.morefish.fishpack;


import com.liba.utils.file.FileChecker;
import ifly.morefish.main;

import java.io.File;


public class ConfigChecker extends FileChecker {
    public ConfigChecker() {
        super(main.mainPlugin.getDataFolder() + File.separator + "config.yml");
    }

    @Override
    public void needle() {

        addParam("enable-plugin-prefix", true);
        addParam("plugin-prefix", "&9&l[&aFishRewards&9] &b");
        addParam("caught-fish-message", "You caught [pack] ");
        addParam("open-pack-message", "You opened [pack]");
        addParam("not-enough-space", "You have not enough space!");

        addParam("item-reward-message", "&bYou received a &a{itemname} &bamount: &a{count}");
        addParam("entity-reward-message", "&4There are entities hovering nearby.");
        addParam("command-reward-message", "&bYou received the reward, the command was executed.");
        addParam("no-right", "&bYou don't have permission");

    }
}
