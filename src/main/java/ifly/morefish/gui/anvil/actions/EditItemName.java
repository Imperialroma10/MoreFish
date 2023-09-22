package ifly.morefish.gui.anvil.actions;

import ifly.morefish.fishpack.pack.Pack;
import ifly.morefish.fishpack.pack.reward.RewardAbstract;
import ifly.morefish.fishpack.pack.reward.RewardItem;
import ifly.morefish.gui.anvil.Action;
import ifly.morefish.gui.helper.ItemCreator;
import ifly.morefish.gui.menus.PackRewardsMenu;
import ifly.morefish.gui.menus.editrewards.EditItem;
import ifly.morefish.main;
import jdk.tools.jmod.Main;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;

public class EditItemName extends Action {
    RewardAbstract rewardAbstract;
    Pack pack;
    public EditItemName(Player player, RewardAbstract rewardAbstract, Pack pack) {
        super(player);
        this.rewardAbstract = rewardAbstract;
        this.pack = pack;
        firstItemEditTitle(PlainTextComponentSerializer.plainText().serialize(rewardAbstract.getItem().displayName()).replace("[", "").replace("]",""));
    }


    @Override
    public void addAction() {
        ItemCreator.replace(this.rewardAbstract.getItem(), getResult());
        new EditItem(main.getPlayerUtils(getPlayer()), (RewardItem) rewardAbstract, pack).open();
    }
}
