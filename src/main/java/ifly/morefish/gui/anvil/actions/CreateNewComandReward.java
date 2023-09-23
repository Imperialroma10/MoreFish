package ifly.morefish.gui.anvil.actions;

import ifly.morefish.fishpack.pack.Pack;
import ifly.morefish.fishpack.pack.reward.RewardCommand;
import ifly.morefish.gui.anvil.Action;
import ifly.morefish.gui.menus.PackRewardsMenu;
import ifly.morefish.main;
import org.bukkit.entity.Player;

public class CreateNewComandReward extends Action {
    Pack pack;
    public CreateNewComandReward(Player player, Pack pack) {
        super(player);
        this.pack = pack;
    }

    @Override
    public void addAction() {
        pack.getRewards().add(new RewardCommand(getResult()));
        new PackRewardsMenu(main.getPlayerUtils(getPlayer()), pack).open();
    }

}
