package ifly.morefish.fishpack.pack.reward;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class RewardCommand extends RewardAbstract {

    String command;
    public RewardCommand(String cmd)
    {
        command = cmd;
    }

    @Override
    public void giveReward(Player p) {
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), command.replace("{player}", p.getName()));
    }

    @Override
    public void Save(YamlConfiguration conf) {
        int num = confSize(conf);
        num++;

        conf.set("Pack.rewards."+num+".type", "command");
        conf.set("Pack.rewards."+num+".command", command);
        conf.set("Pack.rewards."+num+".chance", chance);
    }
}
