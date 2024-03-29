package ifly.morefish.chatAction;

import com.liba.utils.chat.Action;
import ifly.morefish.datastorage.StorageCreator;
import ifly.morefish.fishpack.pack.Pack;
import ifly.morefish.fishpack.pack.reward.RewardCommand;
import org.bukkit.entity.Player;

public class CreateCommandAction implements Action {
    Pack pack;
    public CreateCommandAction(Pack pack){
        this.pack = pack;
    }
    @Override
    public void action(String command, Player player) {
        RewardCommand cmd = new RewardCommand(command,20);
        pack.getRewards().add(cmd);
        player.sendMessage("§eYou have successfully added the command \"§a/"+command+"§e\" ");
        StorageCreator.getStorageIns().Save(pack);
    }


}
