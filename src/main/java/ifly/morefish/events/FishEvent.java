package ifly.morefish.events;

import ifly.morefish.fishpack.FishController;
import ifly.morefish.fishpack.pack.Pack;
import ifly.morefish.gui.MainMenu;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class FishEvent implements Listener, CommandExecutor {

    FishController fishMain;
    public FishEvent(FishController fishMain){
        this.fishMain = fishMain;
    }

    @EventHandler
    public void onfishEvent(PlayerFishEvent e){
       if (e.getCaught() != null){
            this.fishMain.init(e.getPlayer(), e.getHook().getLocation());

       }
    }
    @EventHandler
    public void interact(PlayerInteractEvent e){
        if(e.getHand() != EquipmentSlot.HAND) { return; }
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
            ItemStack item = e.getItem();
            if (item != null && item.getType() == Material.CHEST) {
                Pack pack = fishMain.getPack(item);
                if (pack != null){
                    pack.giveReward(e.getPlayer());

                }
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {

        if (cmd.getName().equalsIgnoreCase("fishrewards")){
            Bukkit.broadcast(Component.text("asd"));
            if (args.length > 0){
                if (args[0].equalsIgnoreCase("admin")){
                    Player p = (Player) sender;
                    p.openInventory(MainMenu.inventory);
                }
                if(args[0].equalsIgnoreCase("reload-pack"))
                {
                    if(args.length == 2)
                    {
                        boolean reloaded = fishMain.Reload(args[1]);
                        sender.sendMessage(reloaded? "§2Successful reloaded": "§cFile not found");
                    }
                }
            }
        }

        return true;
    }
}
