package com.modencore.chatAction;

import com.liba.utils.chat.Action;
import com.liba.utils.player.PlayerUtils;
import com.modencore.fishpack.pack.Pack;
import com.modencore.fishpack.pack.reward.RewardCommand;
import com.modencore.gui.menus.admin.editrewards.EditCommand;
import com.modencore.main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class ChangeCommandName implements Action {
    RewardCommand command;
    Pack pack;
    EditCommand editMenu;

    public ChangeCommandName(RewardCommand command, Pack pack, EditCommand editMenu) {
        this.command = command;
        this.pack = pack;
        this.editMenu = editMenu;
    }

    @Override
    public void action(String s, Player player) {

        List<String> stringList = List.of(s.split(","));
        command.setCommand(stringList);
        stringList.forEach(f->{
            PlayerUtils.sendMessage(player.getUniqueId(),"§eCommand: §b"+f);
        });

        Bukkit.getScheduler().runTaskLater(main.getPlugin(), () -> {
            editMenu.open(player, pack, command);
        }, 10);
    }
}
