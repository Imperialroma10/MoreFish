package ifly.morefish.gui.menus.player;

import com.liba.gui.Gui;
import com.liba.utils.ItemUtil;
import ifly.morefish.fishpack.pack.Pack;
import ifly.morefish.fishpack.pack.reward.RewardAbstract;

public class PlayerPackRewards extends Gui {
    Pack pack;
    public PlayerPackRewards( Pack pack) {
        super(pack.getDisplayname() + " rewards", 3);
        this.pack = pack;
    }

    @Override
    public void setInventoryItems() {
        int i =0;
        for (RewardAbstract items : pack.getRewards()){
            getInventory().setItem(i++, ItemUtil.addLore(items.getItem(), "§bDrop chance §a" + items.getChance()+"%"));
        }
    }
}
