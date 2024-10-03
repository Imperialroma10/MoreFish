package ifly.morefish.fishpack;

import com.liba.utils.Debug;
import ifly.morefish.fishpack.pack.Pack;
import ifly.morefish.main;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

public class FishTask {

    Player player;
    Pack pack;
    Location fishLocation;
    ArmorStand stand;

    BukkitScheduler scheduler = Bukkit.getScheduler();

    Location endlocation;
    int taskid;


    public FishTask(Player player, Pack pack, Location location) {
        this.player = player;
        this.pack = pack;
        this.fishLocation = location;
        fishLocation.setY(fishLocation.getY() - 2);
        endlocation = location.clone();
        endlocation.setY(location.getY() + 4);
        stand = (ArmorStand) fishLocation.getWorld().spawnEntity(fishLocation, EntityType.ARMOR_STAND);
        stand.getEquipment().setHelmet(pack.getChest());
        stand.setInvisible(true);
        stand.setGravity(false);
        stand.setCustomNameVisible(true);
        stand.setCustomName(pack.getDisplayname());

        taskid = scheduler.scheduleSyncRepeatingTask(main.mainPlugin, this::Run, 0, 1);
    }

    public void Run() {
        if (stand.getLocation().getY() <= endlocation.getY()) {
            Location teleport = stand.getLocation().clone();
            teleport.setY(teleport.getY() + 0.05);
            stand.teleport(teleport);
            stand.setHeadPose(stand.getHeadPose().add(0, 0.1, 0));
        } else {
            scheduler.cancelTask(taskid);
            Location fireworkLocation = stand.getLocation().clone();
            fireworkLocation.setY(stand.getLocation().getY() + 2);
            String fireworkSTR = "FIREWORK";
            if (main.mainPlugin.getServer().getVersion().contentEquals("1.20.6") || main.mainPlugin.getServer().getVersion().contentEquals("1.20.5")) {
                fireworkSTR = "FIREWORK_ROCKET";
            }

            Firework firework = (Firework) fireworkLocation.getWorld().spawnEntity(fireworkLocation, EntityType.valueOf(fireworkSTR));
            FireworkMeta meta = firework.getFireworkMeta();

            if (pack.getFireworkEffect() != null){
                meta.addEffect(pack.getFireworkEffect());
            }else{
                FireworkEffect effect = FireworkEffect.builder()
                        .withColor(Color.YELLOW, Color.BLUE)
                        .with(FireworkEffect.Type.BALL)
                        .build();
                meta.addEffect(effect);
            }


            firework.setFireworkMeta(meta);
            firework.detonate();

            Location loc1 = fireworkLocation.clone();

            Location loc2 = player.getLocation().clone();
            loc2.setY(loc2.getY() + 1.1);

            Vector direction = loc2.toVector().subtract(loc1.toVector()).normalize();

            double distance = loc1.distance(loc2);

            for (double distancepased = 0; distancepased <= distance; distancepased += 0.05f) {
                Location particleLocation = loc1.clone().add(direction.clone().multiply(distancepased));
                particleLocation.getWorld().spawnParticle(Particle.COMPOSTER, particleLocation, 1, 0, 0, 0);
            }
            player.sendMessage(Config.getMessage(Config.getConfig().caughtfish.replace("[pack]", pack.getDisplayname())));
            player.getInventory().addItem(pack.getChest());
            stand.remove();

        }
    }
}
