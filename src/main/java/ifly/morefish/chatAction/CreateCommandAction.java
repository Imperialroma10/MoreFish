package ifly.morefish.chatAction;

import com.liba.utils.chat.Action;
import ifly.morefish.datastorage.StorageCreator;
import ifly.morefish.fishpack.pack.Pack;
import ifly.morefish.fishpack.pack.reward.RewardCommand;
import ifly.morefish.gui.menus.admin.PackRewards;
import ifly.morefish.main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CreateCommandAction implements Action {
    Pack pack;
    PackRewards packRewards;
    public CreateCommandAction(Pack pack, PackRewards packRewards){
        this.pack = pack;
        this.packRewards = packRewards;
    }
    @Override
    public void action(String command, Player player) {
        RewardCommand cmd = new RewardCommand(command,20);
        pack.getRewards().add(cmd);
        player.sendMessage("§eYou have successfully added the command \"§a/"+command+"§e\" ");
        StorageCreator.getStorageIns().Save(pack);
        Bukkit.getScheduler().runTaskLater(main.mainPlugin, () -> {
            packRewards.open(player, pack);
        },10);

    }


}
