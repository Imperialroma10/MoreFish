package ifly.morefish.gui.menus.admin;

import ifly.morefish.fishpack.pack.Pack;
import ifly.morefish.fishpack.pack.reward.RewardFun;
import ifly.morefish.fishpack.pack.reward.fun.SpawnEnvil;
import ifly.morefish.gui.Menu;
import ifly.morefish.gui.PlayerMenuUtil;
import ifly.morefish.gui.helper.ItemCreator;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.List;

public class SelectFunRewards extends Menu {
    Pack pack;
    public SelectFunRewards(PlayerMenuUtil playerMenuUtil, Pack pack) {
        super(playerMenuUtil);
        this.pack = pack;
        funList.add(new SpawnEnvil());
    }
    List<RewardFun> funList = new ArrayList<>();
    @Override
    public String getMenuName() {
        return "Select fun rewards";
    }

    @Override
    public int getSlots() {
        return 3;
    }

    @Override
    public void handleInventoryClick(InventoryClickEvent e) {

        if (e.getSlot() < funList.size()){
            RewardFun fun = funList.get(e.getSlot());

            pack.getRewards().add(fun);
            new PackRewardsMenu(getPlayerMenuUtil(), pack).open();
        }

        if (e.getSlot() == getSlots()*9-9){
            new PackRewardsMenu(getPlayerMenuUtil(), pack).open();
        }
    }

    @Override
    public void setMenuItems() {
        int i = 0;
        for (RewardFun rewardFun: funList){
            getInventory().setItem(i, rewardFun.getItem());
            i++;
        }

        getInventory().setItem(getSlots()*9-9, ItemCreator.create(Material.BARRIER, "Back"));
    }
}
