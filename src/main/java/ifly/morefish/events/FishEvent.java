package ifly.morefish.events;

import ifly.morefish.fishpack.FishMain;
import ifly.morefish.fishpack.pack.Pack;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class FishEvent implements Listener {

    FishMain fishMain;
    public FishEvent(FishMain fishMain){
        this.fishMain = fishMain;
    }

    @EventHandler
    public void onfishEvent(PlayerFishEvent e){
       if (e.getCaught() == null){
            this.fishMain.init(e.getPlayer(), e.getHook().getLocation());
       }
    }
    @EventHandler
    public void interact(PlayerInteractEvent e){
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
            ItemStack itemonhand = e.getItem();
            if (itemonhand != null) {
                Pack pack = fishMain.getPack(itemonhand);
                if (pack != null){

                }
            }
        }
    }

}
