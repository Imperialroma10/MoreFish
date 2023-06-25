package ifly.morefish.datastorage;

import ifly.morefish.fishpack.pack.Pack;
import ifly.morefish.main;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class FileStorage implements IStorage{

    File f;
    YamlConfiguration configuration;

    String filename = "packs.yml";
    public FileStorage(){

        f = new File(main.mainPlugin.getDataFolder()+File.separator+filename);
        if (!f.exists()){
            main.mainPlugin.saveResource(filename, false);
        }
        configuration = YamlConfiguration.loadConfiguration(f);
    }
    @Override
    public List<Pack> getPacks() {
       ConfigurationSection section = configuration.getConfigurationSection("packs");
       List<Pack> packs = new ArrayList<>();

        if (section != null) {
            for (String key : section.getKeys(false)){
                HashMap<EntityType, Integer> entityhashmap = new HashMap<>();
                HashMap<ItemStack, Integer> itemshashmap = new HashMap<>();
                Pack pack = new Pack(section.getString(key+".packname"), Integer.parseInt(key));
                pack.setDropChance(section.getInt(key+".dropChance"));

                ConfigurationSection entity  = configuration.getConfigurationSection(key+".entity");

                if (entity != null) {
                    for (String entitykey : entity.getKeys(false)){
                        entityhashmap.put(EntityType.valueOf(entity.getString(entitykey)), entity.getInt(entitykey+".chance"));
                    }
                }
                pack.setEntities(entityhashmap);
                ConfigurationSection itemssection = configuration.getConfigurationSection(key+".items");
                if (itemssection != null) {
                    for (String itemkey : itemssection.getKeys(false)){
                        itemshashmap.put(new ItemStack(Material.valueOf(itemssection.getString(itemkey)), itemssection.getInt("count")), itemssection.getInt("chance"));
                    }
                }
                packs.add(pack);
            }
        }
        Bukkit.broadcast(Component.text("loaded : "+packs.size()));
        return packs;
    }

    @Override
    public void savePacks(List<Pack> packs) {
        for (Pack p : packs) {
            String key = "packs."+p.getCustomModelData();
            ConfigurationSection packsection = configuration.createSection(key);
            packsection.set(".packname", p.getName());
            packsection.set(".dropChance", p.getDropChance());


            for (Map.Entry<EntityType, Integer> entity : p.getEntities().entrySet()){
                ConfigurationSection entitySection = configuration.createSection(key+".entity."+entity.getKey().name());
                entitySection.set("chance",entity.getValue() );
            }


            for (Map.Entry<ItemStack, Integer> item : p.getItemStackList().entrySet()){
                ConfigurationSection itemsec = configuration.createSection(key+".items."+ item.getKey().getType().name());
                itemsec.set("chance", item.getValue());
                itemsec.set("count", item.getKey().getAmount());
            }
        }
        try {
            configuration.save(f);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
