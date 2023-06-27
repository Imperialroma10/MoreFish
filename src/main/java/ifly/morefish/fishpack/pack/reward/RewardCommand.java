package ifly.morefish.fishpack.pack.reward;

import org.bukkit.Bukkit;
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
}
