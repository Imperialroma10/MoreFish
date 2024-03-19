package ifly.morefish.gui.menus.admin;


import com.liba.gui.Gui;
import com.liba.gui.MenuSlot;
import com.liba.gui.buttons.BackButton;
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
        addSlot(10, new MenuSlot(ItemCreator.create(Material.PAPER, "Edit pack name",
                "§6To edit the name you need to open the pack configuration file",
                "§6and change the '§bdisplayname§6' parameter",
                "§6File to edit §b" + pack.getName() + ".yml"), e -> {


            e.setCancelled(true);
        }));

        addSlot(12, new MenuSlot(ItemCreator.create(Material.ENDER_EYE, menu.chance_status.replace("{chance}", pack.getDropChance() + "%"),
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

            getInventory().setItem(12, ItemCreator.replace(getInventory().getItem(12), menu.chance_status.replace("{chance}", String.valueOf(pack.getDropChance()))));
            e.getWhoClicked().sendMessage("§bPack chance set to: §a" + pack.getDropChance() + "%");
            e.setCancelled(true);
        }));

        addSlot(14, new MenuSlot(ItemCreator.create(Material.CHEST, "§6Pack rewards"), e -> {

            packRewards.open(getOwner(), pack);
            e.setCancelled(true);
        }));

        addSlot(26, new MenuSlot(ItemCreator.create(Material.COMMAND_BLOCK, "§6Save pack"), e -> {
            StorageCreator.getStorageIns().Save(pack);

            e.setCancelled(true);
        }));
        addSlot(8, new MenuSlot(menu.getpack_item, e -> {
            e.getWhoClicked().getInventory().addItem(pack.getChest());
            e.setCancelled(true);
        }));

        addSlot(25, new MenuSlot(menu.remove_pack, e -> {
            FishController.packList.remove(pack);

            StorageCreator.getStorageIns().removePack(pack);
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

        addSlot(18, new BackButton(new ItemStack(Material.BARRIER),getBackGui(), "back"));


        addSlot(16, new MenuSlot(getItemFromPerm(), e -> {
            pack.setEnablepermission(!pack.isEnablepermission());
            getMenuSlot(16).setGuiItem(getItemFromPerm());
            updateSlot(16);
            e.setCancelled(true);
        }));

    }

    public ItemStack getItemFromPerm() {
        return pack.isEnablepermission() ? ItemCreator.create(Material.GREEN_WOOL, "Enable permission", "§aYou need a permit to get one: §b" + pack.getPermissionsToOpen()) :
                ItemCreator.create(Material.RED_WOOL, "Disable permission", "§aAny player can get one.");
    }

    public void setPack(Pack pack) {
        this.pack = pack;
    }

    public PackRewards getPackRewards() {
        return packRewards;
    }

}
