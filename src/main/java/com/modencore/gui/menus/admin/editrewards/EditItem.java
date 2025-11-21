package com.modencore.gui.menus.admin.editrewards;


import com.liba.gui.Gui;
import com.liba.gui.MenuSlot;
import com.liba.gui.buttons.BackButton;
import com.liba.utils.ItemUtil;
import com.liba.utils.chat.ChatAwait;
import com.modencore.chatAction.ChangeItemName;
import com.modencore.fishpack.lang.EditItemMenuMsg;
import com.modencore.fishpack.lang.MenuMsgs;
import com.modencore.fishpack.pack.Pack;
import com.modencore.fishpack.pack.reward.RewardAbstract;
import com.modencore.fishpack.pack.reward.RewardItem;
import com.modencore.gui.helper.ItemCreator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public class EditItem extends Gui {


    private final EditItemMenuMsg menu;
    Pack pack;
    private RewardAbstract item;

    public EditItem(Gui gui) {
        super("Edit item config", 1, gui);
        menu = MenuMsgs.get().EditItemMenu;
        setGlobalcancel(true);
    }

    @Override
    public void setInventoryItems() {

        String title = "§aItem preview";
        String dropChance = "§aChance of receiving an item: §b§l{chance}%";

        addSlot(2, new MenuSlot(ItemCreator.create(Material.PAPER, "§aEdit item name",
                "§b Left click to edit item name"), e -> {
            e.getWhoClicked().sendMessage("§eEnter a new item name");
            e.getWhoClicked().closeInventory();
            ChatAwait.getInstance().registerAction((Player) e.getWhoClicked(), new ChangeItemName((RewardItem) item, pack, this));
            e.setCancelled(true);
        }));

        addSlot(6, new MenuSlot(ItemUtil.create(menu.addamount_item, "§aItems count", "§6Left click to add §b§l1 §6unit",
                "§6Right-click to remove §b§l1 §6unit"), e -> {
            ItemStack itemStack = item.getItem();
            if (e.isLeftClick()) {
                if (itemStack.getAmount() + 1 <= 64) {
                    item.getItem().setAmount(itemStack.getAmount() + 1);
                }
            }
            if (e.isRightClick()) {
                if (itemStack.getAmount() - 1 >= 1) {
                    item.getItem().setAmount(itemStack.getAmount() - 1);
                }
            }
            render();
            e.setCancelled(true);
        }));

        addSlot(8, new MenuSlot(ItemCreator.create(Material.ENDER_EYE, dropChance.replace("{chance}", item.getChance() + ""),
                "§6Left click to add §b§l5§b%",
                "§6Right click to remove §b§l5§b%"), e -> {
            int percent = item.getChance();
            if (e.isLeftClick()) {
                if (percent + 5 <= 100) {
                    item.setChance(percent + 5);
                }
            }
            if (e.isRightClick()) {
                if (percent - 5 >= 0) {
                    item.setChance(percent - 5);
                }
            }
            render();
            e.setCancelled(true);
        }));

        addSlot(4, new MenuSlot(ItemUtil.create(item.getItem().clone(), title, dropChance.replace("{chance}", item.getChance() + "")), e -> {

            e.setCancelled(true);
        }));
        addSlot(getSlots() - 9, new BackButton(new ItemStack(Material.BARRIER), getBackGui(), "back"));


    }

    public void setItem(RewardAbstract item) {
        this.item = item;
    }

    public synchronized void open(Player player, Pack pack) {
        this.pack = pack;
        super.open(player);
    }
}
