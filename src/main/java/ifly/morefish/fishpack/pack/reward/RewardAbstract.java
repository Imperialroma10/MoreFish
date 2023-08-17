package ifly.morefish.fishpack.pack.reward;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.Set;

abstract public class RewardAbstract {

    int chance;

    public int getChance() {
        return chance;
    }

    public void setChance(int chance) {
        this.chance = chance;
    }
    abstract public void giveReward(Player player);
     public boolean checkChance(int chance){
         if (chance == 100){
             return true;
         }else{
             if (this.chance < chance){
                 return true;
             }else{
                 return false;
             }
         }

     }

     protected int confSize(YamlConfiguration conf)
     {
         ConfigurationSection sect = conf.getConfigurationSection("Pack.rewards");
         int num = 0;
         if(sect != null)
         {
             Set<String> keys = sect.getKeys(false);
             String[] arr = keys.toArray(new String[0]);
             num = arr.length > 0?Integer.parseInt(arr[arr.length-1]):0;
         }
         return num;
     }

     public abstract void Save(YamlConfiguration conf);
}
