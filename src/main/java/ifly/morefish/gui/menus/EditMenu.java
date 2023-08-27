package ifly.morefish.gui.menus;

import ifly.morefish.datastorage.FileStorage;
import ifly.morefish.datastorage.StorageCreator;
import ifly.morefish.fishpack.FishController;
import ifly.morefish.fishpack.pack.Pack;
import ifly.morefish.gui.Menu;
import ifly.morefish.gui.PlayerMenuUtil;
import ifly.morefish.gui.anvil.AnvilController;
import ifly.morefish.gui.anvil.actions.EditPackDisplayName;
import ifly.morefish.gui.helper.ItemCreator;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class EditMenu extends Menu {
    Pack pack;
    boolean isnewpack;
    public EditMenu(PlayerMenuUtil playerMenuUtil, boolean editnewpack) {
        super(playerMenuUtil);
        isnewpack = editnewpack;
    }
    public EditMenu(PlayerMenuUtil playerMenuUtil) {
        super(playerMenuUtil);
    }

    @Override
    public String getMenuName() {
        return  "§4Edit "+pack.getDisplayname()+" pack";
    }

    @Override
    public int getSlots() {
        return 3;
    }

    @Override
    public void handleInventoryClick(InventoryClickEvent e) {

        if (e.getSlot() == 12-9){
            pack.setDropChance(pack.getDropChance()+5);
            getInventory().setItem(12, ItemCreator.replace(getInventory().getItem(12), "§bChance §a"+ pack.getDropChance()+"%"));
            getPlayerMenuUtil().getOwner().sendMessage(Component.text("§bPack chance set to: §a"+pack.getDropChance()+"%"));
        }
        if (e.getSlot() == 12+9){
            pack.setDropChance(pack.getDropChance()-5);
            getInventory().setItem(12, ItemCreator.replace(getInventory().getItem(12), "§bChance §a"+ pack.getDropChance()+"%"));
            getPlayerMenuUtil().getOwner().sendMessage(Component.text("§bPack chance set to: §a"+pack.getDropChance()+"%"));
        }
        if (e.getSlot() == 14){
            PackRewardsMenu packRewardsMenu = new PackRewardsMenu(getPlayerMenuUtil());
            packRewardsMenu.setPack(pack);
            packRewardsMenu.open();
        }
        if (e.getSlot() == 3*9-1){
            if(isnewpack) {
                FishController.packList.add(pack);
            }
            StorageCreator.getStorageIns().Save(pack, isnewpack);
            isnewpack = false;
        }
        if (e.getSlot() == 3*9-9){
            new PackListMenu(getPlayerMenuUtil()).open();
        }
        if (e.getSlot() == 10){
            AnvilController.createAnvil((Player) e.getWhoClicked(), new EditPackDisplayName((Player) e.getWhoClicked(), pack, isnewpack));
        }
        if (e.getSlot() == 3*9-2){
            Pack newPack = StorageCreator.getStorageIns().laodFromFile(pack);
            if (newPack != null){
                FishController.packList.remove(pack);
                FishController.packList.add(newPack);
                setPack(newPack);
                open();
            }

        }
        if (e.getSlot() == 3*9-3){
            FishController.packList.remove(pack);
            new PackListMenu(getPlayerMenuUtil()).open();
            StorageCreator.getStorageIns().removePack(pack);
        }
        e.setCancelled(true);
    }


    public Pack getPack()
    {
        return pack;
    }

    public EditMenu setPack(Pack pack){
        this.pack = pack;

        return this;
    }
    @Override
    public void setMenuItems() {
        getInventory().setItem(12-9, ItemCreator.create(Material.GREEN_WOOL, "§bAdd §a5% §bchance"));
        getInventory().setItem(12, ItemCreator.create(Material.CRAFTING_TABLE, "§bChance §a"+ pack.getDropChance() +"%"));
        getInventory().setItem(12+9, ItemCreator.create(Material.RED_WOOL, "§bRemove §45% §bchance"));
        getInventory().setItem(14, ItemCreator.create(Material.CHEST, "§bRewards"));
        getInventory().setItem(10, ItemCreator.create(Material.PAPER, "§bChange pack name"));
        getInventory().setItem(3*9-1, ItemCreator.create(Material.PISTON, "§bSave"));
        getInventory().setItem(3*9-3, ItemCreator.create(Material.HOPPER, "§bRemove pack"));
        getInventory().setItem(3*9-2, ItemCreator.create(Material.COMMAND_BLOCK, "§bLoad from file",
                "§5Use only if you changed the configuration through a file"));
        getInventory().setItem(3*9-9, ItemCreator.create(Material.BARRIER, "Back"));
    }
}
