package ifly.morefish.events;

import ifly.morefish.fishpack.Config;
import ifly.morefish.fishpack.FishController;
import ifly.morefish.fishpack.pack.Pack;
import ifly.morefish.gui.menus.MainMenu;
import ifly.morefish.main;
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
       if (e.getState() == PlayerFishEvent.State.CAUGHT_FISH){
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
                    if(!pack.enoughSpace(e.getPlayer()))
                    {
                        e.getPlayer().sendMessage(Config.getMessage(Config.getConfig().notenoughspace));
                        return;
                    }
                    pack.giveReward(e.getPlayer());
                }
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (cmd.getName().equalsIgnoreCase("fishrewards")){
            if (args.length > 0){
                if (sender.hasPermission("fishrewarads.admin")){
                    if (args[0].equalsIgnoreCase("admin")){
                        Player p = (Player) sender;
                        new MainMenu(main.getPlayerUtils(p)).open();
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
        }

        return true;
    }
}
