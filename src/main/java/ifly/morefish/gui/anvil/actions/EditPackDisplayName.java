package ifly.morefish.gui.anvil.actions;

import ifly.morefish.fishpack.pack.Pack;
import ifly.morefish.gui.anvil.Action;
import ifly.morefish.gui.menus.EditMenu;
import ifly.morefish.main;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EditPackDisplayName extends Action {

    Pack pack;
    public EditPackDisplayName(Player player, Pack pack) {
        super(player);
        this.pack = pack;
        firstItemEditTitle();
    }

    @Override
    public void addAction() {
        pack.setDisplayname(getResult());
        new EditMenu(main.getPlayerUtils(getPlayer())).setPack(pack).open();
    }

    public void firstItemEditTitle(){
        ItemStack itemStack = getInventory().getItem(0);
        if (itemStack != null){
            ItemMeta meta = itemStack.getItemMeta();
            meta.displayName(Component.text(pack.getDisplayname()));
            itemStack.setItemMeta(meta);

        }

    }
}
