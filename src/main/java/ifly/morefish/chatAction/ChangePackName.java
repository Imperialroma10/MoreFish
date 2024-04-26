package ifly.morefish.chatAction;

import com.liba.utils.chat.Action;
import ifly.morefish.datastorage.StorageCreator;
import ifly.morefish.fishpack.pack.Pack;
import ifly.morefish.gui.menus.admin.EditMenu;
import ifly.morefish.main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ChangePackName implements Action {
    Pack pack;
    EditMenu editMenu;

    public ChangePackName(Pack pack, EditMenu menu) {
        this.pack = pack;
        this.editMenu = menu;
    }

    @Override
    public void action(String s, Player player) {

        pack.setDisplayname(s);
        player.sendMessage("§eNew pack displayname: " + pack.getDisplayname());
        StorageCreator.getStorageIns().Save(pack);
        Bukkit.getScheduler().runTaskLater(main.mainPlugin, () -> {
            editMenu.setPack(pack);
            editMenu.open(player);
        }, 10);
    }
}
