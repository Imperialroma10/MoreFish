package ifly.morefish.fishpack.pack.reward.fun;

import ifly.morefish.fishpack.pack.reward.RewardFun;
import ifly.morefish.gui.helper.ItemCreator;
import ifly.morefish.main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;


public class SpawnEnvil extends RewardFun {

    public SpawnEnvil() {
        setChance(100);
    }


    @Override
    public String getRewardMessage() {
        return "";
    }

    @Override
    public ItemStack getItem() {
        return ItemCreator.create(Material.ANVIL, "SpawnEnvil");
    }

    @Override
    public void action(Player player) {
        new SpawnEnvilTask(player.getLocation());
    }


    class SpawnEnvilTask {
        FallingBlock block;
        BukkitScheduler scheduler = Bukkit.getScheduler();
        int taskid;

        public SpawnEnvilTask(Location loc) {
            taskid = scheduler.scheduleSyncRepeatingTask(main.getPlugin(), this::checkEnvil, 0, 10);
            Location location = loc.clone();
            location.setY(location.getY() + 25);
            block = location.getWorld().spawnFallingBlock(location, Bukkit.createBlockData(Material.ANVIL));
            block.setDropItem(false);
            //block.setDamagePerBlock(0.5f);
        }

        public void checkEnvil() {
            if (block.isOnGround()) {
                block.getLocation().getBlock().setType(Material.AIR);
                block.remove();
                scheduler.cancelTask(taskid);
            }
        }
    }
}
