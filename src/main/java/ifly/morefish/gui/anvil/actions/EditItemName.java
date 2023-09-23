package ifly.morefish.gui.anvil.actions;

import ifly.morefish.fishpack.pack.Pack;
import ifly.morefish.fishpack.pack.reward.RewardAbstract;
import ifly.morefish.fishpack.pack.reward.RewardItem;
import ifly.morefish.gui.anvil.Action;
import ifly.morefish.gui.helper.ItemCreator;
import ifly.morefish.gui.menus.editrewards.EditItem;
import ifly.morefish.main;

import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

public class EditItemName extends Action {
    RewardAbstract rewardAbstract;
    Pack pack;
    public EditItemName(Player player, RewardAbstract rewardAbstract, Pack pack) {
        super(player);
        this.rewardAbstract = rewardAbstract;
        this.pack = pack;
        ItemMeta meta = rewardAbstract.getItem().getItemMeta();
        firstItemEditTitle(meta.getDisplayName().replace("[", "").replace("]",""));
    }


    @Override
    public void addAction() {
        ItemCreator.replace(this.rewardAbstract.getItem(), getResult());
        new EditItem(main.getPlayerUtils(getPlayer()), (RewardItem) rewardAbstract, pack).open();
    }
}
