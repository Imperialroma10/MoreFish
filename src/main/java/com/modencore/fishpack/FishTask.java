package com.modencore.fishpack;

import com.liba.utils.Debug;
import com.liba.utils.coreversion.VersionValidator;
import com.liba.utils.player.PlayerUtils;
import com.liba.version.VersionChecker;
import com.modencore.fishpack.pack.Pack;
import com.modencore.main;
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

        taskid = scheduler.scheduleSyncRepeatingTask(main.getPlugin(), this::Run, 0, 1);
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
            if (VersionValidator.check("1.20.5", VersionValidator.EventTime.AFTER, main.getPlugin())) {
                fireworkSTR = "FIREWORK_ROCKET";
            }

            Firework firework = (Firework) fireworkLocation.getWorld().spawnEntity(fireworkLocation, EntityType.valueOf(fireworkSTR));
            FireworkMeta meta = firework.getFireworkMeta();


            if (pack.getFireworkEffect() != null) {
                meta.addEffect(pack.getFireworkEffect());
            } else {
                Debug.logChat("2");
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

            if (loc1.getWorld().equals(loc2.getWorld())) {
                Vector direction = loc2.toVector().subtract(loc1.toVector()).normalize();
                double distance = loc1.distance(loc2);

                for (double distancePassed = 0; distancePassed <= distance; distancePassed += 0.05f) {
                    Location particleLocation = loc1.clone().add(direction.clone().multiply(distancePassed));
                    particleLocation.getWorld().spawnParticle(Particle.COMPOSTER, particleLocation, 1, 0, 0, 0);
                }
            }

            PlayerUtils.sendMessage(player.getUniqueId(),main.getPlugin().getChecker().getString("plugin-prefix") + main.getPlugin().getChecker().getString("caught-fish-message").replace("[pack]", pack.getDisplayname()));
            player.getInventory().addItem(pack.getChest());
            stand.remove();

        }
    }
}
