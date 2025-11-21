package com.modencore.chatAction;

import com.liba.utils.chat.Action;
import com.liba.utils.player.PlayerUtils;
import com.modencore.datastorage.StorageCreator;
import com.modencore.fishpack.pack.Pack;
import com.modencore.gui.menus.admin.EditMenu;
import com.modencore.main;
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
        PlayerUtils.sendMessage(player.getUniqueId(),"§eNew pack displayname: " + pack.getDisplayname());
        StorageCreator.getStorageIns().Save(pack);
        Bukkit.getScheduler().runTaskLater(main.getPlugin(), () -> {
            editMenu.setPack(pack);
            editMenu.open(player);
        }, 10);
    }
}
