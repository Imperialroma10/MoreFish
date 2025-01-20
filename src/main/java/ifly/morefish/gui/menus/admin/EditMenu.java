package ifly.morefish.gui.menus.admin;


import com.liba.gui.Gui;
import com.liba.gui.MenuSlot;
import com.liba.gui.buttons.BackButton;
import com.liba.utils.chat.ChatAwait;
import ifly.morefish.chatAction.ChangePackName;
import ifly.morefish.datastorage.StorageCreator;
import ifly.morefish.fishpack.FishController;
import ifly.morefish.fishpack.lang.EditMenuMsg;
import ifly.morefish.fishpack.lang.MenuMsgs;
import ifly.morefish.fishpack.pack.Pack;
import ifly.morefish.gui.helper.ItemCreator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EditMenu extends Gui {
    private final EditMenuMsg menu;
    Pack pack;

    PackRewards packRewards;

    public EditMenu(String title, int rows, Gui gui) {
        super(title, rows, gui);
        menu = MenuMsgs.get().EditMenu;
        packRewards = new PackRewards(this);
    }

    public EditMenu(String title, int rows, Pack pack, Gui gui) {
        this(title, rows, gui);

        this.pack = pack;
    }

    @Override
    public void setInventoryItems() {
        addSlot(10, new MenuSlot(ItemCreator.create(Material.PAPER, "§eEdit pack name",
                "§6Left click to set new pack displayname "), e -> {
            e.getWhoClicked().closeInventory();
            ChatAwait.getInstance().registerAction((Player) e.getWhoClicked(), new ChangePackName(pack, this));

            e.getWhoClicked().sendMessage("§eOld name of the pack: " + pack.getName());
            e.getWhoClicked().sendMessage("§eEnter the new pack name into the chat");
            e.setCancelled(true);
        }));

        addSlot(12, new MenuSlot(ItemCreator.create(Material.ENDER_EYE, "§e" + menu.chance_status.replace("{chance}", pack.getDropChance() + ""),
                "§6Left click to add §b5%",
                "§6Right click to remove §b5%"), e -> {
            int percent = pack.getDropChance();
            if (e.isLeftClick()) {
                if (percent + 5 <= 100) {
                    pack.setDropChance(percent + 5);
                }
            }
            if (e.isRightClick()) {
                if (percent - 5 >= 0) {
                    pack.setDropChance(percent - 5);
                }
            }

            getInventory().setItem(12, ItemCreator.replace(getInventory().getItem(12), "§e" + menu.chance_status.replace("{chance}", String.valueOf(pack.getDropChance()))));
            e.getWhoClicked().sendMessage("§bPack chance set to: §a" + pack.getDropChance() + "%");
            e.setCancelled(true);
        }));

        addSlot(14, new MenuSlot(ItemCreator.create(Material.CHEST, "§ePack rewards"), e -> {

            packRewards.open(getOwner(), pack);
            e.setCancelled(true);
        }));

        addSlot(26, new MenuSlot(ItemCreator.create(Material.COMMAND_BLOCK, "§eSave pack"), e -> {
            StorageCreator.getStorageIns().Save(pack);

            e.setCancelled(true);
        }));
        addSlot(8, new MenuSlot(menu.getpack_item, e -> {
            e.getWhoClicked().getInventory().addItem(pack.getChest());
            e.setCancelled(true);
        }));

        addSlot(25, new MenuSlot(menu.remove_pack, e -> {

            StorageCreator.getStorageIns().removePack(pack);
            FishController.packList.remove(pack);

            GuiController.getMainMenu(getOwner()).getPackList().open((Player) e.getWhoClicked());
            e.setCancelled(true);
        }));

//        addSlot(24, new MenuSlot(menu.reload_pack, e -> {
//            Pack newPack = StorageCreator.getStorageIns().laodFromFile(pack);
//            if (newPack != null) {
//                FishController.packList.set(FishController.packList.indexOf(pack), newPack);
//
//                this.pack = newPack;
//                setTitle("Edit " + pack.getDisplayname());
//                open(getOwner());
//            }
//        }));

        addSlot(18, new BackButton(new ItemStack(Material.BARRIER), getBackGui(), "back"));


        addSlot(16, new MenuSlot(getItemFromPerm(), e -> {
            if (pack.isEnablepermission()) {
                pack.setEnablepermission(null);
            } else {
                pack.setEnablepermission("fishrewards.user");
            }
            getMenuSlot(16).setGuiItem(getItemFromPerm());
            updateSlot(16);
            e.setCancelled(true);
        }));

    }

    public ItemStack getItemFromPerm() {
        return pack.isEnablepermission() ? ItemCreator.create(Material.GREEN_WOOL, "§aEnable permission", "§aYou need a permit to get one: §b" + pack.getEnablepermission(), "§aYou can change the rights in the pack file") :
                ItemCreator.create(Material.RED_WOOL, "§4Disable permission", "§aAny player can get one.");
    }

    public void setPack(Pack pack) {
        this.pack = pack;
    }

    public PackRewards getPackRewards() {
        return packRewards;
    }

}
