package ifly.morefish.gui;

import ifly.morefish.fishpack.pack.Pack;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class AddNewPackInventoryMenu {
    Pack pack;
    Inventory inventory;

    public AddNewPackInventoryMenu(){
        this.inventory = Bukkit.createInventory(null,3*9, "edit pack");
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Pack getPack() {
        return pack;
    }

    public void setPack(Pack pack) {
        this.pack = pack;
    }
}
