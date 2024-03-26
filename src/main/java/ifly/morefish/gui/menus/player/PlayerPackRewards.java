package ifly.morefish.gui.menus.player;

import com.liba.gui.Gui;
import com.liba.gui.ListedGui;
import com.liba.gui.MenuSlot;
import com.liba.utils.ItemUtil;
import ifly.morefish.fishpack.pack.Pack;
import ifly.morefish.fishpack.pack.reward.RewardAbstract;
import ifly.morefish.gui.menus.admin.EditMenu;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerPackRewards extends ListedGui {
    Pack pack;
    public PlayerPackRewards( Pack pack) {
        super(pack.getDisplayname() + " rewards", 4, pack.getRewards(), 3);
        this.pack = pack;
    }

    @Override
    public void setInventoryItems() {

        for (int i = 0; i < getDataBlockSize(); i++) {
            int id = getDataBlockSize() * getPage() + i;
            if (id < getData().size()) {
                RewardAbstract rewardAbstract = pack.getRewards().get(id);
                addSlot(i, new MenuSlot(ItemUtil.create(new ItemStack(rewardAbstract.getItem()), rewardAbstract.getItem().getItemMeta().getDisplayName(),
                        "Item drop chance " + rewardAbstract.getChance()
                ), e -> {

                    e.setCancelled(true);
                }));
            }
        }
        super.setInventoryItems();

    }
}
