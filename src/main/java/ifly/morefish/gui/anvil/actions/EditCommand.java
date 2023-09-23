package ifly.morefish.gui.anvil.actions;

import ifly.morefish.fishpack.pack.Pack;
import ifly.morefish.fishpack.pack.reward.RewardCommand;
import ifly.morefish.gui.anvil.Action;
import ifly.morefish.gui.menus.PackRewardsMenu;
import ifly.morefish.main;
import org.bukkit.entity.Player;

public class EditCommand extends Action {
    Pack pack;
    RewardCommand command;
    public EditCommand(Player player, Pack pack, RewardCommand command) {
        super(player);
        this.pack = pack;
        this.command = command;
        firstItemEditTitle(command.getCommand());
    }
    @Override
    public void addAction() {
        command.setCommand(getResult());
        PackRewardsMenu menu = new PackRewardsMenu(main.getPlayerUtils(getPlayer()), pack);

        menu.open();
    }

}
