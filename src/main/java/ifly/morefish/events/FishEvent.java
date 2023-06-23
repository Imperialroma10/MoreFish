package ifly.morefish.events;

import ifly.morefish.fishpack.FishMain;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

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

}
