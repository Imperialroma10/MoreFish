package ifly.morefish.fishpack.pack;

import com.liba.utils.Checker;
import ifly.morefish.main;

import java.io.File;
import java.util.HashMap;

public class ConfigChecker extends Checker {
    public ConfigChecker() {
        super(main.mainPlugin.getDataFolder()+ File.separator + "config2.yml");
    }

    @Override
    public HashMap<String, Object> needle() {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("enable-plugin-prefix", true);
        hashMap.put("plugin-prefix", "&9&l[&aFishRewards&9] &b");
        hashMap.put("caught-fish-message", "You caught [pack] ");
        hashMap.put("open-pack-message", "You opened [pack]");
        hashMap.put("not-enough-space", "You have not enough space!");

        hashMap.put("item-reward-message", "&bYou received a &a{itemname} &bamount: &a{count}");
        hashMap.put("entity-reward-message", "&4There are entities hovering nearby.");
        hashMap.put("command-reward-message", "&bYou received the reward, the command was executed.");
        return hashMap;
    }
}
