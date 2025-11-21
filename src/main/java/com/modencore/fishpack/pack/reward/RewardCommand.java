package com.modencore.fishpack.pack.reward;

import com.liba.utils.ItemUtil;
import com.modencore.gui.helper.ItemCreator;
import com.modencore.main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class RewardCommand extends RewardAbstract {

    List<String> command;
    String description;

    public RewardCommand(List<String> cmd, int chance) {
        command = cmd;
        this.item = ItemCreator.create(Material.PAPER, "Command");
        List<String> str = new ArrayList<>();
        str.addAll(command);
        ItemUtil.addLore(item, str);
        this.chance = chance;
    }

    @Override
    public String getRewardMessage() {
        if (getDescription() != null) {
            return main.getPlugin().getChecker().getString("plugin-prefix").toString() + getDescription();
        }
        return main.getPlugin().getChecker().getString("plugin-prefix").toString() + main.getPlugin().getChecker().getString("command-reward-message").toString();
    }

    @Override
    public void giveReward(Player p) {
        command.stream().map(f -> f.replace("{player}", p.getName())).forEach(f->Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), f));

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

    public List<String> getCommand() {
        return command;
    }

    public void setCommand(List<String> command) {
        this.item = ItemCreator.create(Material.PAPER, "commands");
        List<String> str = new ArrayList<>();
        str.addAll(command);
        ItemUtil.addLore(item, str);
        this.command = command;

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
