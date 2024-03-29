package ifly.morefish.chatAction;

import com.liba.utils.ItemUtil;
import com.liba.utils.chat.Action;
import ifly.morefish.datastorage.StorageCreator;
import ifly.morefish.fishpack.pack.Pack;
import ifly.morefish.fishpack.pack.reward.RewardItem;
import ifly.morefish.gui.menus.admin.rewardcreator.ItemReward;
import org.bukkit.entity.Player;

public class ChangeItemName implements Action {
    RewardItem reward;
    Pack pack;
    public ChangeItemName(RewardItem reward, Pack pack) {
        this.pack = pack;
        this.reward = reward;
    }

    @Override
    public void action(String s, Player player) {
        player.sendMessage("§eYou changed the item name to: §b" + s);
        ItemUtil.rename(reward.getItem(), s);
        StorageCreator.getStorageIns().Save(pack);
    }
}
