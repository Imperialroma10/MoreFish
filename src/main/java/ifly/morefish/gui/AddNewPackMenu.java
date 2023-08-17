package ifly.morefish.gui;

import ifly.morefish.fishpack.FishController;
import ifly.morefish.fishpack.pack.Pack;
import ifly.morefish.gui.helper.ItemCreator;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class AddNewPackMenu implements Listener {

    HashMap<UUID, AddNewPackInventoryMenu> inventory = new HashMap<>();

    FishController controller;

    public AddNewPackMenu(FishController controller){
        this.controller = controller;
    }

    public Inventory getInventory(UUID uuid, int packid){
        Pack pack = controller.getPackList().get(packid);

        if (inventory.get(uuid) == null) {
            inventory.put(uuid, new AddNewPackInventoryMenu());
        }

        AddNewPackInventoryMenu menu = inventory.get(uuid);
        menu.setPack(pack);
        Inventory inv = menu.getInventory();
        inv.setItem(10, ItemCreator.create(Material.PAPER, pack.getDisplayname()));
        inv.setItem(11-9, ItemCreator.create(Material.GREEN_WOOL, "+ 10 chance"));
        inv.setItem(11, ItemCreator.create(Material.DISPENSER, pack.getDropChance()+"%"));
        inv.setItem(11+9, ItemCreator.create(Material.RED_WOOL, "- 10 chance"));

        inv.setItem(12, ItemCreator.create(Material.CHEST, "Items on chest"));

        ItemStack item = pack.getStatus() ? ItemCreator.create(Material.GREEN_WOOL, "Enable") : ItemCreator.create(Material.RED_WOOL, "Disabled") ;

        inv.setItem(24, item);

        inv.setItem(26, ItemCreator.create(Material.NETHER_STAR, "Save pack"));

        return inventory.get(uuid).getInventory();
    }
    @EventHandler
    public void clickInventory(InventoryClickEvent e){
        for (Map.Entry<UUID, AddNewPackInventoryMenu> entry : inventory.entrySet()){
            if (entry.getValue().getInventory() == e.getInventory()){
                Pack pack = entry.getValue().getPack();
                Inventory inventory1 = entry.getValue().getInventory();
                if (e.getRawSlot() == 11-9){
                    pack.setDropChance(pack.getDropChance()+10);
                    inventory1.setItem(11, ItemCreator.replace(inventory1.getItem(11), pack.getDropChance()+""));
                }
                if (e.getRawSlot() == 11+9){
                    pack.setDropChance(pack.getDropChance()-10);
                    inventory1.setItem(11, ItemCreator.replace(inventory1.getItem(11), pack.getDropChance()+""));
                }
                if (e.getRawSlot() == 24){
                    pack.changeStatus();
                    ItemStack item = pack.getStatus() ? ItemCreator.create(Material.GREEN_WOOL, "Enable") : ItemCreator.create(Material.RED_WOOL, "Disabled") ;
                    inventory1.setItem(24, item);
                }
                if (e.getRawSlot() == 12){
                    Inventory inv = entry.getValue().getPackdrop();
                    e.getWhoClicked().openInventory(inv);
                }
                if (e.getRawSlot() == 26){
                    controller.getStorage().Save(pack);
                }
                e.setCancelled(true);
            }
        }
    }


}
