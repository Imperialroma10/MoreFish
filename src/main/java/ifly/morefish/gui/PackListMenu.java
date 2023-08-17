package ifly.morefish.gui;

import ifly.morefish.datastorage.FileStorage;
import ifly.morefish.fishpack.FishController;
import ifly.morefish.fishpack.pack.Pack;
import ifly.morefish.gui.helper.ItemCreator;
import ifly.morefish.main;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class PackListMenu implements Listener {
    FishController controller;
    public HashMap<UUID, Inventory> inventorys = new HashMap<>();

    AddNewPackMenu addNewPackMenu;
    int page = 0;

    int size = 5*9;

    public PackListMenu(FishController controller) {
        this.controller = controller;
        addNewPackMenu = new AddNewPackMenu(controller);
        main.mainPlugin.getServer().getPluginManager().registerEvents(addNewPackMenu, main.mainPlugin);
    }
    public Inventory getPlayerInventory(Player player){
        UUID uuid = player.getUniqueId();
        if (inventorys.get(uuid) == null){
            Inventory inventory = Bukkit.createInventory(null, 6*9);
            inventory.setItem(49, ItemCreator.create(Material.COMMAND_BLOCK, "Add new pack"));
            inventorys.put(uuid, inventory);
        }
        Inventory inv = inventorys.get(uuid);


        int pages = controller.getPackList().size() / size;
        int start = page * size;
        int end = start + size;

        for (int i = start; i < end; i++){
            if (i < controller.getPackList().size()){
                Pack pack = controller.getPackList().get(i);
                inv.setItem(i, ItemCreator.create(Material.CHEST, pack.getDisplayname()));
            }
        }

//        for (Pack pack : controller.getPackList()){
//            inv.addItem(pack.getChest());
//        }
        return inventorys.get(uuid);
    }
    @EventHandler
    public void clickInventory(InventoryClickEvent e){
        for (Inventory inventory : this.inventorys.values()){
            if (e.getInventory() == inventory){
                if (0 <= e.getRawSlot() && size >= e.getRawSlot()){
                    int slot = (size * page) + e.getRawSlot();
                    if (controller.getPackList().get(slot) != null){
                        e.getWhoClicked().openInventory(addNewPackMenu.getInventory(e.getWhoClicked().getUniqueId(), slot));
                    }
                }else{
                    if (e.getRawSlot() == 49){
                        Pack pack = new Pack("new pack", "Display name", 1);
                        controller.getPackList().add(pack);
                        int id = controller.getPackList().indexOf(pack);
                        e.getWhoClicked().openInventory(addNewPackMenu.getInventory(e.getWhoClicked().getUniqueId(), id));
                    }
                }

                e.setCancelled(true);
            }
        }
    }

}
