package ifly.morefish.fishpack.pack;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class DefaultPack extends Pack{
    public DefaultPack(){
        super("Default",1);

        setDropChance(20);

        this.addItemstack(new ItemStack(Material.DIAMOND, 1), 100);
        this.addItemstack(new ItemStack(Material.DIAMOND, 64), 1);
        this.addEntity(EntityType.ZOMBIE, 10);
    }
}
