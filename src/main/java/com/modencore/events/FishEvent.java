package com.modencore.events;

import com.liba.utils.Debug;
import com.liba.utils.player.PlayerUtils;
import com.modencore.datastorage.StorageCreator;
import com.modencore.fishpack.FishController;
import com.modencore.fishpack.pack.Pack;
import com.modencore.gui.menus.admin.GuiController;
import com.modencore.gui.menus.player.PlayerPackRewards;
import com.modencore.main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;

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
    public void armourdamage(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof ArmorStand) {
            ArmorStand stand = (ArmorStand) e.getEntity();
            ItemStack helmet = stand.getEquipment().getHelmet();
            if (helmet != null) {
                if (helmet.hasItemMeta()) {
                    ItemMeta meta = helmet.getItemMeta();
                    for (Pack pack : FishController.packList) {
                        if (meta != null && meta.getPersistentDataContainer().has(pack.getKey())) {

                            e.setCancelled(true);
                            return;
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onfishEvent(PlayerFishEvent e) {
        if (e.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
            this.fishMain.init(e.getPlayer(), e.getHook().getLocation());
        }
    }

    @EventHandler
    public void placeChest(BlockPlaceEvent e) {
        ItemStack itemStack = e.getItemInHand();
        if (itemStack.getItemMeta() != null) {
            ItemMeta meta = itemStack.getItemMeta();
            Pack pack = fishMain.getPack(itemStack);

            if (pack != null && meta.getPersistentDataContainer().get(pack.getKey(), PersistentDataType.STRING) != null) {
                e.setCancelled(true);
            }
        }

    }

    @EventHandler
    public void interactEntity(PlayerInteractAtEntityEvent e) {
        if (e.getHand() == EquipmentSlot.HAND) {
            if (e.getRightClicked() instanceof ArmorStand stand) {
                ItemStack itemStack = stand.getEquipment().getHelmet();
                if (itemStack != null && itemStack.hasItemMeta()) {
                    ItemMeta meta = itemStack.getItemMeta();
                    for (Pack pack : FishController.packList) {
                        if (meta != null && meta.getPersistentDataContainer().has(pack.getKey())) {

                            e.setCancelled(true);
                            return;
                        }
                    }
                }
            }
        }

    }

    @EventHandler
    public void interact(PlayerInteractEvent e) {
        if (e.getHand() != EquipmentSlot.HAND) {
            return;
        }
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR && Debug.isDebug()) {
            ItemStack itemStack = e.getItem();
            if (itemStack != null) {
                ItemMeta meta = itemStack.getItemMeta();

                if (meta instanceof EnchantmentStorageMeta) {
                    EnchantmentStorageMeta mt = (EnchantmentStorageMeta) meta;
                    //Debug.LogChat(ench.toString());
                }
                if (meta instanceof PotionMeta) {
                    //Debug.LogChat(potionMeta.toString());
                }
            }
        }
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack item = e.getItem();
            if (item != null) {
                Pack pack = fishMain.getPack(item);
                if (pack != null) {
                    if (!pack.enoughSpace(e.getPlayer())) {
                        e.getPlayer().sendMessage(main.getPlugin().getChecker().getString("plugin-prefix").toString() + main.getPlugin().getChecker().getString("not-enough-space").toString());
                        return;
                    }
                    fishMain.getPlayerStatistic().addOpenPacks();

                    pack.giveReward(e.getPlayer());
                }
            }
        }

        if (e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            ItemStack itemStack = e.getItem();
            if (itemStack != null) {
                Pack pack = fishMain.getPack(itemStack);
                if (pack != null && pack.isSeerewards()) {
                    new PlayerPackRewards(pack).open(e.getPlayer());
                }
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("fishrewards")) {
            //Debug.LogChat("lenght: "+args.length + "args: " + args.toString());
            if (!sender.hasPermission("fishrewarads.admin")) {
                sender.sendMessage(main.getPlugin().getChecker().getString("plugin-prefix") + main.getPlugin().getChecker().getString("no-right"));
                return true;
            }
            if (args.length == 1){
                if (args[0].equalsIgnoreCase("test")){
                    PlayerUtils.sendMessage(((Player) sender).getUniqueId(),"tell d{player} #B8860Btest #800000test");
                }
            }
            if (args.length == 0) {
                sender.sendMessage("§e-----------------Available commands-------------------");
                sender.sendMessage("§e/fishrewards admin §b- open admin panel");
                sender.sendMessage("§e/fishrewards reload-pack §b- reload packs");
                sender.sendMessage("§e/fishrewards examplepack §b- creates example packs in packs folder");
                sender.sendMessage("§e----------------------------------------------------");
                return true;
            }
            if (sender.hasPermission("fishrewarads.admin") || sender.hasPermission("*")) {// || sender.hasPermission("*")) {
                if (args[0].equalsIgnoreCase("admin")) {
                    if (args.length == 1) {
                        Player p = (Player) sender;
                        GuiController.getMainMenu(p).open(p);
                    }
                    if (args.length == 2) {
                        if (args[1].equalsIgnoreCase("reload")) {
                            FishController.packList.clear();
                            FishController.packList.addAll(StorageCreator.getStorageIns().getPacks());
                            main.getPlugin().getChecker().checkStorage();
                            sender.sendMessage(main.getPlugin().getChecker().getString("plugin-prefix") + " §ePlugin reloaded");
                        }
                    }
                    if (args.length == 3) {
                        if (args[1].equalsIgnoreCase("reload-pack")) {
                            //boolean reloaded = fishMain.Reload(args[2]);
                            //sender.sendMessage(reloaded ? "§2Successful reloaded" : "§cFile not found");
                        }
                        if (args[1].equalsIgnoreCase("getpack")) {
                            Pack pack = fishMain.getPack(args[2]);
                            Player player = (Player) sender;
                            player.getInventory().addItem(pack.getChest());
                        }
                    }
                    if (args.length == 4) {
                        if (args[1].equalsIgnoreCase("givepack")) {
                            Pack pack = fishMain.getPack(args[2]);
                            if (pack != null) {
                                Player player = Bukkit.getPlayer(args[3]);
                                if (player != null && player.isOnline()) {
                                    player.getInventory().addItem(pack.getChest());
                                } else {
                                    sender.sendMessage(main.getPlugin().getChecker().getString("plugin-prefix") + "§4Null player or is offline");
                                }
                            } else {
                                sender.sendMessage(main.getPlugin().getChecker().getString("plugin-prefix") + "§4Pack not found");
                            }
                        }
                    }
                    if (args.length == 5) {
                        if (args[1].equalsIgnoreCase("givepack")) {
                            Pack pack = fishMain.getPack(args[2]);
                            if (pack != null) {
                                Player player = Bukkit.getPlayer(args[3]);
                                if (player != null && player.isOnline()) {
                                    ItemStack itemStack = pack.getChest().clone();

                                    itemStack.setAmount(Integer.parseInt(args[4]));
                                    player.getInventory().addItem(itemStack);
                                } else {
                                    sender.sendMessage(main.getPlugin().getChecker().getString("plugin-prefix") + "§4Null player or is offline");
                                }
                            } else {
                                sender.sendMessage(main.getPlugin().getChecker().getString("plugin-prefix") + "§4Pack not found ");
                            }
                        }
                    }
                }
            }

            if (args[0].equalsIgnoreCase("examplepack")) {
                main.getPlugin().saveResource("packs/commandpack.yml", false);
                main.getPlugin().saveResource("packs/entitypack.yml", false);
                main.getPlugin().saveResource("packs/itempack.yml", false);
                sender.sendMessage(main.getPlugin().getChecker().getString("plugin-prefix") + "You have successfully created standard packages.");
            }

        }


        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String s, String[] args) {
        List<String> completions = new ArrayList<>();


        if (cmd.getName().equalsIgnoreCase("fishrewards")) {
            if (args.length > 0) {
                if (args.length == 1) {
                    completions.add("admin");
                    //  completions.add("event");
                }
                if (args[0].equalsIgnoreCase("admin") && sender.hasPermission("*")) {

                    if (args.length == 2) {
                        if (args[0].equalsIgnoreCase("admin")) {
                            //completions.add("reload-pack");
                            completions.add("getpack");
                            completions.add("givepack");
                            completions.add("reload");
                        }
                    }
                    if (args.length == 3) {
                        if (args[1].equalsIgnoreCase("reload-pack")
                                || args[1].equalsIgnoreCase("getpack")
                                || args[1].equalsIgnoreCase("givepack")) {
                            for (Pack pack : fishMain.getPackList()) {
                                completions.add(pack.getName());
                            }
                        }

                    }
                    if (args.length == 4) {
                        if (args[1].equalsIgnoreCase("givepack")) {
                            Pack pack = fishMain.getPack(args[2]);
                            if (pack != null) {
                                for (Player player : Bukkit.getOnlinePlayers()) {
                                    completions.add(player.getName());
                                }
                            }
                        }

                    }
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
        }
        return completions;
    }
}
