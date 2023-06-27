package ifly.morefish.fishpack;

import ifly.morefish.fishpack.pack.Pack;
import ifly.morefish.main;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
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

    FishMain fishMain;
    public FishTask(Player player, Pack pack, Location location, FishMain fishMain) {
        this.player = player;
        this.pack = pack;
        this.fishLocation = location;
        fishLocation.setY(fishLocation.getY()-2);
        endlocation = location.clone();
        endlocation.setY(location.getY()+4);
         stand = (ArmorStand) fishLocation.getWorld().spawnEntity(fishLocation, EntityType.ARMOR_STAND);
         stand.getEquipment().setHelmet(new ItemStack(Material.CHEST));
         stand.setInvisible(true);
         stand.setGravity(false);
         stand.setCustomNameVisible(true);
         stand.customName(Component.text(pack.getDisplayname()));
        this.fishMain = fishMain;
        taskid = scheduler.scheduleSyncRepeatingTask(main.mainPlugin, this::Run, 0 ,1);
    }

    public void Run(){
        if (stand.getLocation().getY() <= endlocation.getY()){
            Location teleport = stand.getLocation().clone();
            teleport.setY(teleport.getY()+0.05);
            stand.teleport(teleport);
            stand.setHeadPose(stand.getHeadPose().add(0,0.1,0));
        }else{
            scheduler.cancelTask(taskid);
            Location fireworkLocation = stand.getLocation().clone();
            fireworkLocation.setY(stand.getLocation().getY()+2);
            Firework firework = (Firework) fireworkLocation.getWorld().spawnEntity(fireworkLocation, EntityType.FIREWORK);
            FireworkMeta meta = firework.getFireworkMeta();
            FireworkEffect effect = FireworkEffect.builder()
                    .withColor(Color.BLUE, Color.YELLOW)
                    .with(FireworkEffect.Type.BALL_LARGE)
                    .build();
            meta.addEffect(effect);
            firework.setFireworkMeta(meta);
            firework.detonate();

            Location loc1 = fireworkLocation.clone();

            Location loc2 = player.getLocation().clone();
            loc2.setY(loc2.getY()+1.5);

            Vector direction = loc2.toVector().subtract(loc1.toVector()).normalize();

            double distance = loc1.distance(loc2);

            for (double distancepased = 0; distancepased <= distance; distancepased+= 0.05f){
                Location particleLocation = loc1.clone().add(direction.clone().multiply(distancepased));
                particleLocation.getWorld().spawnParticle(Particle.SCRAPE, particleLocation, 1, 0,0,0);
            }

            player.getInventory().addItem(pack.getChest());
            stand.remove();
            fishMain.fishTasks.remove(this);
        }
    }
}
