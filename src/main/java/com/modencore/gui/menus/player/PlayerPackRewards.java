package com.modencore.gui.menus.player;

import com.liba.gui.ListedGui;
import com.liba.gui.MenuSlot;
import com.liba.utils.ItemUtil;
import com.modencore.fishpack.lang.MenuMsgs;
import com.modencore.fishpack.pack.Pack;
import com.modencore.fishpack.pack.reward.RewardAbstract;
import org.bukkit.inventory.ItemStack;

public class PlayerPackRewards extends ListedGui {
    Pack pack;

    public PlayerPackRewards(Pack pack) {
//        super(MenuMsgs.get().openMenuMsg.titlename.replace("{packname}", pack.getDisplayname()), 4, pack.getRewards(), 3*9));
        super(pack.getDisplayname() + " rewards", 4, pack.getRewards(), 3 * 9);
        this.pack = pack;
        setGlobalcancel(true);
    }

    @Override
    public void setInventoryItems() {
        getInventory().clear();
        menuSlot.clear();
        for (int i = 0; i < getDataPerPage(); i++) {
            int id = getDataPerPage() * getPage() + i;
            if (id < getData().size()) {
                RewardAbstract rewardAbstract = pack.getRewards().get(id);
                addSlot(i, new MenuSlot(ItemUtil.create(new ItemStack(rewardAbstract.getItem()), rewardAbstract.getItem().getItemMeta().getDisplayName(),
                        "§bItem drop chance §a" + rewardAbstract.getChance() + "%"
                ), e -> {

                    e.setCancelled(true);
                }));
            }
        }
        super.setInventoryItems();

    }
}
