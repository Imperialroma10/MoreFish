package ifly.morefish.gui.menus.admin;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class GuiController {

    static HashMap<Player, MainMenu> mainMenu = new HashMap<>();


    public static MainMenu getMainMenu(Player player) {
        if (mainMenu.containsKey(player)) {
            return mainMenu.get(player);
        }
        mainMenu.put(player, new MainMenu());

        return mainMenu.get(player);
    }


}
