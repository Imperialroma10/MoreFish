package ifly.morefish.chatAction;

import com.liba.utils.chat.Action;
import ifly.morefish.fishpack.pack.Pack;
import org.bukkit.entity.Player;

public class ChangePackName implements Action {
    Pack pack;
    public ChangePackName(Pack pack){
        this.pack = pack;
    }
    @Override
    public void action(String s, Player player) {

        pack.setDisplayname(s);
        player.sendMessage("§eNew pack displayname: " + pack.getDisplayname());

    }
}
