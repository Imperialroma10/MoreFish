package ifly.morefish.chatAction;

import com.liba.utils.chat.Action;
import ifly.morefish.fishpack.pack.reward.RewardCommand;
import org.bukkit.entity.Player;

public class ChangeCommandName implements Action {
    RewardCommand command;

    public ChangeCommandName(RewardCommand command) {
        this.command = command;
    }

    @Override
    public void action(String s, Player player) {
        player.sendMessage("§eYou changed the command to: §b" + s);
        command.setCommand(s);
    }
}
