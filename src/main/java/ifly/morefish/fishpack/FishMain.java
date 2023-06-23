package ifly.morefish.fishpack;

import ifly.morefish.fishpack.pack.CustomPack;
import ifly.morefish.fishpack.pack.DefaultPack;
import ifly.morefish.fishpack.pack.Pack;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class FishMain {

    List<Pack> packList = new ArrayList<>();
    List<FishTask> fishTasks = new ArrayList<>();
    public FishMain(){
        packList.add(new DefaultPack());
        packList.add(new CustomPack());

    }

    public Pack getPack(ItemStack itemStack){
        return packList.stream().filter(pack -> pack.getChest() == itemStack).findFirst().get();
    }
    public Pack getPack(int customModelData){
        return packList.stream().filter(pack -> pack.getCustomModelData() == customModelData).findFirst().get();
    }

    public void init(Player p, Location location){

        Random random = new Random();
        int x = random.nextInt(100);
        packList.sort(Comparator.comparingInt(Pack::getDropChance));

        for (Pack pack: packList){
            if (x <= pack.getDropChance()){
                fishTasks.add(new FishTask(p, pack, location, this));
                return;
            }
        }
    }
}
