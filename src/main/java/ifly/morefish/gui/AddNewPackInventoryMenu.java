package ifly.morefish.gui;

import ifly.morefish.fishpack.pack.Pack;
import ifly.morefish.fishpack.pack.reward.RewardAbstract;
import ifly.morefish.fishpack.pack.reward.RewardItem;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class AddNewPackInventoryMenu {
    Pack pack;
    Inventory inventory;

    Inventory packdrop = Bukkit.createInventory(null, 3*9);

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

    public Inventory getPackdrop() {
        if (this.pack != null){
            for (RewardAbstract asb : this.pack.getRewards()){
                if (asb instanceof RewardItem){
                    packdrop.addItem(((RewardItem) asb).getItem());
                }
            }
        }
        return packdrop;
    }
}
