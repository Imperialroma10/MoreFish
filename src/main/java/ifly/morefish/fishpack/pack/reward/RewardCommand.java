package ifly.morefish.fishpack.pack.reward;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class RewardCommand extends RewardAbstract {

    String command;

    public RewardCommand(String command, int chance){
        this.command = command;
        this.chance = chance;
    }

    @Override
    public void getReward(Player p) {
        command = command.replace("{username}", p.getName());
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
    }
}
