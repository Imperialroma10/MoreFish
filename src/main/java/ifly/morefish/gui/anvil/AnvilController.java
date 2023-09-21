package ifly.morefish.gui.anvil;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class AnvilController {
    public static HashMap<Player, Action> anvils = new HashMap<>();

    public static void createAnvil(Player player, Action action){
        anvils.put(player, action);
    }

}
