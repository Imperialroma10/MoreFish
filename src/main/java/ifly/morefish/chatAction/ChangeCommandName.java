package ifly.morefish.chatAction;

import com.liba.gui.Gui;
import com.liba.utils.chat.Action;
import ifly.morefish.fishpack.pack.Pack;
import ifly.morefish.fishpack.pack.reward.RewardCommand;
import ifly.morefish.gui.menus.admin.EditMenu;
import ifly.morefish.gui.menus.admin.editrewards.EditCommand;
import ifly.morefish.main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

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
        player.sendMessage("§eYou changed the command to: §b" + s);
        command.setCommand(s);
        Bukkit.getScheduler().runTaskLater(main.mainPlugin, () -> {
            editMenu.open(player, pack, command);
        },10);
    }
}
