package ifly.morefish.chatAction;

import com.liba.utils.chat.Action;
import com.liba.utils.player.PlayerUtils;
import ifly.morefish.datastorage.StorageCreator;
import ifly.morefish.fishpack.pack.Pack;
import ifly.morefish.fishpack.pack.reward.RewardCommand;
import ifly.morefish.gui.menus.admin.PackRewards;
import ifly.morefish.main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class CreateCommandAction implements Action {
    Pack pack;
    PackRewards packRewards;

    public CreateCommandAction(Pack pack, PackRewards packRewards) {
        this.pack = pack;
        this.packRewards = packRewards;
    }

    @Override
    public void action(String command, Player player) {
        List<String> stringList = List.of(command.split(","));
        RewardCommand cmd = new RewardCommand(stringList, 20);
        pack.getRewards().add(cmd);
        stringList.forEach(f->{
            PlayerUtils.sendMessage(player.getUniqueId(),"§eYou have successfully added the command \"§a/" + f + "§e\" ");
        });

        StorageCreator.getStorageIns().Save(pack);
        Bukkit.getScheduler().runTaskLater(main.getPlugin(), () -> {
            packRewards.open(player, pack);
        }, 10);

    }


}
