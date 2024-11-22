package ifly.morefish.gui.menus.admin.rewardcreator;

import com.liba.gui.Gui;
import com.liba.gui.ListedGui;
import com.liba.gui.MenuSlot;
import com.liba.gui.buttons.BackButton;
import ifly.morefish.fishpack.pack.Pack;
import ifly.morefish.fishpack.pack.reward.RewardEntity;
import ifly.morefish.gui.helper.ItemCreator;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class EntityReward extends ListedGui {
    Pack pack;

    public <T> EntityReward(List<T> data, Gui gui) {
        super("§6Select entity for reward", 5, data, 4, gui);

    }

    @Override
    public void setInventoryItems() {

        for (int i = 0; i < getDataPerPage(); i++) {
            int id = getDataPerPage() * getPage() + i;
            if (id < getData().size()) {
                Material material = Material.getMaterial(getData().get(id) + "_SPAWN_EGG");
                addSlot(i, new MenuSlot(ItemCreator.create(material, material.name()), e -> {
                    pack.addReward(new RewardEntity((EntityType) getData().get(id), 1, 50));
                    openBack();
                    e.setCancelled(true);
                }));
            }
        }

        addSlot(getSlots() - 9, new BackButton(new ItemStack(Material.BARRIER), getBackGui(), "back"));

        super.setInventoryItems();
    }

    public void setPack(Pack pack) {
        this.pack = pack;
    }
}
