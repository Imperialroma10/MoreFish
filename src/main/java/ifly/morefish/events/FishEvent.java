package ifly.morefish.events;

import ifly.morefish.fishpack.Config;
import ifly.morefish.fishpack.FishController;
import ifly.morefish.fishpack.pack.Pack;
import ifly.morefish.gui.menus.admin.GuiController;
import ifly.morefish.main;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class FishEvent implements Listener, CommandExecutor, TabCompleter {

    FishController fishMain;

    public FishEvent(FishController fishMain) {
        this.fishMain = fishMain;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

    }

    @EventHandler
    public void onfishEvent(PlayerFishEvent e) {
        if (e.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
            this.fishMain.init(e.getPlayer(), e.getHook().getLocation());
        }
    }

    @EventHandler
    public void placeChest(BlockPlaceEvent e) {
        if (e.getBlockPlaced().getType() == Material.CHEST) {
            if (fishMain.getPack(e.getItemInHand()) != null) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void interact(PlayerInteractEvent e) {
        if (e.getHand() != EquipmentSlot.HAND) {
            return;
        }
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack item = e.getItem();
            if (item != null) {
                Pack pack = fishMain.getPack(item);
                if (pack != null) {
                    if (!pack.enoughSpace(e.getPlayer())) {
                        e.getPlayer().sendMessage(Config.getMessage(Config.getConfig().notenoughspace));
                        return;
                    }
                    fishMain.getPlayerStatistic().addOpenPacks();
                    pack.giveReward(e.getPlayer());
                }
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("fishrewards")) {

            if (!sender.hasPermission("fishrewarads.admin")) {
                sender.sendMessage(Config.getMessage("You have no permissions"));
                return true;
            }

            if (args.length == 0) {
                sender.sendMessage("§e-----------------Available commands-------------------");
                sender.sendMessage("§e/fishrewards admin §b- open admin panel");
                sender.sendMessage("§e/fishrewards reload-pack §b- reload packs");
                sender.sendMessage("§e/fishrewards examplepack §b- creates example packs in packs folder");
                sender.sendMessage("§e----------------------------------------------------");
                return true;
            }
            if (sender.hasPermission("fishrewarads.admin")) {
                if (args[0].equalsIgnoreCase("admin")) {
                    Player p = (Player) sender;
                    GuiController.getMainMenu(p).open(p);
                }
                if (args[0].equalsIgnoreCase("reload-pack")) {
                    if (args.length == 2) {
                        boolean reloaded = fishMain.Reload(args[1]);
                        sender.sendMessage(reloaded ? "§2Successful reloaded" : "§cFile not found");
                    }
                }
                if (args[0].equalsIgnoreCase("examplepack")) {
                    main.mainPlugin.saveResource("packs/ExampleDonatePack.yml", false);
                    main.mainPlugin.saveResource("packs/ExampleEntityPack.yml", false);
                    main.mainPlugin.saveResource("packs/ExampleItemsPack.yml", false);
                    sender.sendMessage(Config.getMessage("You have successfully created standard packages."));
                }

            }
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String s, String[] args) {
        List<String> completions = new ArrayList<>();


        if (cmd.getName().equalsIgnoreCase("fishrewards")) {
            if (args.length == 1) {
                completions.add("admin");
                //  completions.add("event");
            }
//            if (args.length == 2 && args[0].equalsIgnoreCase("event")) {
//                completions.add("start");
//                completions.add("stop");
//            }
//            if (args.length == 3 && args[1].equalsIgnoreCase("start")){
//                for (Pack pack : fishMain.getPackList()){
//                    completions.add(pack.getName());
//                }
//            }
//            if (args.length == 4 && fishMain.getPackList().contains(fishMain.getPack(args[1]))){
//                completions.add("seconds");
//            }
        }
        return completions;
    }
}
