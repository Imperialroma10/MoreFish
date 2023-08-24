package ifly.morefish.gui.anvil.actions;

import ifly.morefish.fishpack.pack.Pack;
import ifly.morefish.gui.anvil.Action;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EditPack extends Action {

    Pack pack;
    public EditPack(Player player, Pack pack) {
        super(player);
        this.pack = pack;
    }

    @Override
    public void addAction() {

    }

}
