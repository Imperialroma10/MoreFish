package ifly.morefish.gui.menus.admin.rewardcreator;

import ifly.imperial.gui.Gui;
import ifly.imperial.gui.MenuSlot;
import ifly.imperial.gui.buttons.BackButton;
import ifly.morefish.fishpack.lang.MenuMsgs;
import ifly.morefish.fishpack.lang.PutItemMenuMsg;
import ifly.morefish.fishpack.pack.Pack;
import ifly.morefish.fishpack.pack.reward.RewardItem;
import ifly.morefish.gui.helper.ItemCreator;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemReward extends Gui {

    Pack pack;
    public ItemReward(Gui gui) {
        super("Put items to reward ", 5, gui);

    }

    @Override
    public void setInventoryItems() {
        for (int i =0; i < 3*9; i++){
            addSlot(i, new MenuSlot(null, null));
        }

        for (int i = 3*9; i < 4*9; i++){
            addSlot(i, new MenuSlot(ItemCreator.create(Material.RED_STAINED_GLASS_PANE,"§6Put items in the boxes above"), e->{
                e.setCancelled(true);
            }));
        }
        addSlot(40, new MenuSlot(ItemCreator.create(Material.COMMAND_BLOCK, "§6Save items"), e->{
            for (int i =0; i < 3*9; i++){
                if (getInventory().getItem(i) != null){
                    pack.addReward(new RewardItem(getInventory().getItem(i), 50));
                    this.getInventory().setItem(i, new ItemStack(Material.AIR));
                }
            }
            openBack();
            e.setCancelled(true);
        }));
        addSlot(getSlots()-9, new BackButton(ItemCreator.create(Material.BARRIER, "Back"), getBackGui()));
    }

    public void setPack(Pack pack) {
        this.pack = pack;
    }
}
