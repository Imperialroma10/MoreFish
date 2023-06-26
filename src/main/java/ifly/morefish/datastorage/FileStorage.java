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
import java.util.logging.Level;

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
    public void getPacks() {
//
//       ConfigurationSection packs = configuration.getConfigurationSection("packs");
//       for (String sections : packs.getKeys(false)){
//
//          String packName = packs.getString(sections+".packname");
//          int dropChance = packs.getInt(sections+".dropchance");
//
//          if (packs.get(sections+".items") != null){
//
//              ConfigurationSection items = configuration.getConfigurationSection(sections+".items");
//              for (String itemsKey: items.getKeys(false)){
//
//                  String key = itemsKey;
//                  int chance = items.getInt(items+"."+key+".chance");
//                  int count = items.getInt(items+"."+key+".count");
//                  Bukkit.broadcast(Component.text("------------------------"));
//                  Bukkit.broadcast(Component.text(chance));
//                  Bukkit.broadcast(Component.text(count));
//                  Bukkit.broadcast(Component.text("------------------------"));
//              }
//          }else{
//              Bukkit.broadcast(Component.text(packs.getCurrentPath()));
//          }
//
//       }


    }

    @Override
    public void savePacks(List<Pack> packs) {
//        for (Pack p : packs) {
//            String key = "packs."+p.getCustomModelData();
//            ConfigurationSection packsection = configuration.createSection(key);
//            packsection.set(".packname", p.getName());
//            packsection.set(".dropchance", p.getDropChance());
//
//
//            for (Map.Entry<EntityType, Integer> entity : p.getEntities().entrySet()){
//                ConfigurationSection entitySection = configuration.createSection(key+".entity."+entity.getKey().name());
//                entitySection.set("chance",entity.getValue() );
//            }
//
//
//            for (Map.Entry<ItemStack, Integer> item : p.getItemStackList().entrySet()){
//                ConfigurationSection itemsec = configuration.createSection(key+".items."+ item.getKey().getType().name());
//                itemsec.set("chance", item.getValue());
//                itemsec.set("count", item.getKey().getAmount());
//            }
//        }
//        try {
//            configuration.save(f);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

}
