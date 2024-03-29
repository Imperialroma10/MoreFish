package ifly.morefish.chatAction;

import com.liba.utils.chat.Action;
import ifly.morefish.fishpack.pack.Pack;
import ifly.morefish.fishpack.pack.reward.RewardCommand;
import org.bukkit.entity.Player;

public class ChangeCommandName implements Action {
    RewardCommand command;
    Pack pack;
    public ChangeCommandName(RewardCommand command, Pack pack) {
        this.command = command;
        this.pack = pack;
    }

    @Override
    public void action(String s, Player player) {
        player.sendMessage("§eYou changed the command to: §b" + s);
        command.setCommand(s);
    }
}
