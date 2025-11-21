package com.modencore.gui.menus.admin.editrewards;

import com.liba.gui.Gui;
import com.liba.gui.MenuSlot;
import com.liba.gui.buttons.BackButton;
import com.liba.utils.ItemUtil;
import com.liba.utils.chat.ChatAwait;
import com.modencore.chatAction.ChangeCommandName;
import com.modencore.fishpack.lang.EditItemMenuMsg;
import com.modencore.fishpack.lang.MenuMsgs;
import com.modencore.fishpack.pack.Pack;
import com.modencore.fishpack.pack.reward.RewardCommand;
import com.modencore.gui.helper.ItemCreator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EditCommand extends Gui {
    private final EditItemMenuMsg menu;
    RewardCommand command;
    Pack pack;

    public EditCommand(Gui backGui) {
        super("Edit command GUI", 1, backGui);
        menu = MenuMsgs.get().EditItemMenu;
        setGlobalcancel(true);
    }

    @Override
    public void setInventoryItems() {
        addSlot(2, new MenuSlot(ItemCreator.create(Material.PAPER, "§eEdit command",
                "§b Left click to edit command"), e -> {
            e.getWhoClicked().sendMessage("§e Enter a new command");
            e.getWhoClicked().closeInventory();
            ChatAwait.getInstance().registerAction((Player) e.getWhoClicked(), new ChangeCommandName(command, pack, this));
            e.setCancelled(true);
        }));


        addSlot(8, new MenuSlot(ItemCreator.create(Material.ENDER_EYE, "§bCommand execute chance: §b{chance}%".replace("{chance}", command.getChance() + ""),
                "§6Left click to add §b§l5§b%",
                "§6Right click to remove §b§l5§b%"), e -> {
            int percent = command.getChance();
            if (e.isLeftClick()) {
                if (percent + 5 <= 100) {
                    command.setChance(percent + 5);
                }
            }
            if (e.isRightClick()) {
                if (percent - 5 >= 0) {
                    command.setChance(percent - 5);
                }
            }
            render();
            e.setCancelled(true);
        }));


        addSlot(4, new MenuSlot(ItemUtil.create(command.getItem().clone(), "Command", "§bCommands execute chance: §b{chance}%".replace("{chance}", command.getChance() + "")), e -> {

            e.setCancelled(true);
        }));
        addSlot(getSlots() - 9, new BackButton(new ItemStack(Material.BARRIER), getBackGui(), "back"));
    }


    public void open(Player player, Pack pack, RewardCommand command) {
        this.pack = pack;
        this.command = command;
        super.open(player);
    }
}
