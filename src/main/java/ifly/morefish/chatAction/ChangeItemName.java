package ifly.morefish.chatAction;

import com.liba.utils.ItemUtil;
import com.liba.utils.chat.Action;
import ifly.morefish.datastorage.StorageCreator;
import ifly.morefish.fishpack.pack.Pack;
import ifly.morefish.fishpack.pack.reward.RewardItem;
import ifly.morefish.gui.menus.admin.editrewards.EditItem;
import ifly.morefish.main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ChangeItemName implements Action {
    RewardItem reward;
    Pack pack;
    EditItem editItem;

    public ChangeItemName(RewardItem reward, Pack pack, EditItem item) {
        this.pack = pack;
        this.reward = reward;
        this.editItem = item;
    }

    @Override
    public void action(String s, Player player) {
        player.sendMessage("§eYou changed the item name to: §b" + s);
        ItemUtil.rename(reward.getItem(), s);
        StorageCreator.getStorageIns().Save(pack);
        Bukkit.getScheduler().runTaskLater(main.getPlugin(), () -> {
            editItem.open(player, pack);
        }, 10);

    }
}
