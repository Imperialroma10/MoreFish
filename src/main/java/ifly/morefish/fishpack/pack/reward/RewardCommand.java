package ifly.morefish.fishpack.pack.reward;

import ifly.morefish.gui.helper.ItemCreator;
import ifly.morefish.main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RewardCommand extends RewardAbstract {

    String command;

    public RewardCommand(String cmd, int chance) {
        command = cmd;
        this.item = ItemCreator.create(Material.PAPER, cmd);
        this.chance = chance;

    }

    @Override
    public String getRewardMessage() {
        return main.mainPlugin.getChecker().getParam("plugin-prefix").toString() + main.mainPlugin.getChecker().getParam("command-reward-message").toString();
    }

    @Override
    public void giveReward(Player p) {
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), command.replace("{player}", p.getName()));
    }

    @Override
    public ItemStack getItem() {
        return item;
    }

    @Override
    public void Save(ConfigurationSection section) {
        int num = confSize(section);
        num++;

        section.set(num + ".type", "command");
        section.set(num + ".command", command);
        section.set(num + ".chance", chance);
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.item = ItemCreator.create(Material.PAPER, command);
        this.command = command;

    }
}
