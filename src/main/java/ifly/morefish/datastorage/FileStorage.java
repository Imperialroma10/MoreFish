package ifly.morefish.datastorage;

import ifly.morefish.fishpack.pack.Pack;
import ifly.morefish.fishpack.pack.reward.RewardAbstract;
import ifly.morefish.fishpack.pack.reward.RewardCommand;
import ifly.morefish.fishpack.pack.reward.RewardEntity;
import ifly.morefish.fishpack.pack.reward.RewardItem;
import ifly.morefish.main;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FileStorage implements IStorage {

    File f_packs;

    public FileStorage() {
        f_packs = new File(main.mainPlugin.getDataFolder().getPath() + File.separator + "packs");
    }

    public void copy() {
        //main.mainPlugin.saveResource("Menus.yml", false);
        if (!f_packs.exists()) {
            f_packs.mkdirs();
            main.mainPlugin.saveResource("packs/ExampleDonatePack.yml", false);
            main.mainPlugin.saveResource("packs/ExampleEntityPack.yml", false);
            main.mainPlugin.saveResource("packs/ExampleItemsPack.yml", false);
        }
    }

    public List<Pack> getPacks() {

        File[] files = f_packs.listFiles();
        List<Pack> list = new ArrayList<>(files.length);

        for (File file : files) {
            if (!file.isFile()) {
                continue;
            }
            Pack pack;
            YamlConfiguration conf = YamlConfiguration.loadConfiguration(file);
            String pack_displayname = conf.getString("Pack.displayname", "").replace('&', '§');
            //String pack_name = conf.getString("Pack.name", "");
            int pack_custommodeldata = conf.getInt("Pack.custommodeldata");
            int dropchance = conf.getInt("Pack.chance");
            boolean permissions = conf.getBoolean("Pack.permissions");
            ConfigurationSection sec_rewards = conf.getConfigurationSection("Pack.rewards");
            if (sec_rewards != null) {
                Set<String> keys_rewards = sec_rewards.getKeys(false);

                List<RewardAbstract> rewards = new ArrayList<>(keys_rewards.size());
                for (String key_reward : keys_rewards) {
                    String type = sec_rewards.getString(key_reward + ".type");
                    if (type.equals("item")) {
                        String material = sec_rewards.getString(key_reward + ".material").toUpperCase();
                        int count = sec_rewards.getInt(key_reward + ".amount");
                        int custommodeldata = sec_rewards.getInt(key_reward + ".custommodeldata", -1);
                        String displayname = sec_rewards.getString(key_reward + ".displayname", "").replace('&', '§');

                        int chance = sec_rewards.getInt(key_reward + ".chance");

                        Material m = Material.getMaterial(material);
                        ItemStack is = new ItemStack(m, count);
                        ItemMeta meta = is.getItemMeta();
                        if (displayname.length() > 0)
                            meta.setDisplayName(displayname);
                        if (custommodeldata > -1)
                            meta.setCustomModelData(custommodeldata);
                        is.setItemMeta(meta);
                        RewardItem rewardItem = new RewardItem(is, chance);

                        ConfigurationSection enchantSection = sec_rewards.getConfigurationSection(key_reward + ".enchants");

                        if (enchantSection != null) {
                            for (String enchant : enchantSection.getKeys(false)) {
                                if (m == Material.ENCHANTED_BOOK){
                                    rewardItem.addBookEnchantments(Enchantment.getByKey(NamespacedKey.fromString(enchant.toLowerCase())), enchantSection.getInt(enchant + ".level"));
                                }else{
                                    rewardItem.addEnchantments(Enchantment.getByKey(NamespacedKey.fromString(enchant.toLowerCase())), enchantSection.getInt(enchant + ".level"));
                                }

                            }
                        }
                        rewards.add(rewardItem);

                    }
                    if (type.equals("mob")) {
                        String mobtype = sec_rewards.getString(key_reward + ".entitytype");
                        EntityType etype = EntityType.valueOf(mobtype);
                        int amount = sec_rewards.getInt(key_reward + ".amount");
                        int chance = sec_rewards.getInt(key_reward + ".chance");
                        RewardEntity rewardEntity = new RewardEntity(etype, amount, chance);

                        ConfigurationSection equipsection = sec_rewards.getConfigurationSection(key_reward + ".equipment");
                        if (equipsection != null) {
                            for (String type1 : equipsection.getKeys(false)) {
                                Material m = Material.getMaterial(type1);
                                rewardEntity.setArmor(type1, new ItemStack(m));

                                ConfigurationSection enchantSection = equipsection.getConfigurationSection(type1 + ".enchants");
                                if (enchantSection != null) {
                                    for (String enchant : enchantSection.getKeys(false)) {
                                        enchant = enchant.toLowerCase();
                                        rewardEntity.getArmor(type1).addUnsafeEnchantment(Enchantment.getByKey(NamespacedKey.fromString(enchant)), enchantSection.getInt(enchant + ".level"));
                                    }
                                }
                            }
                        }

                        rewards.add(rewardEntity);
                    }
                    if (type.equals("command")) {
                        String command = sec_rewards.getString(key_reward + ".command");
                        RewardCommand rewardCommand = new RewardCommand(command);
                        rewards.add(rewardCommand);
                    }
                }
                pack = new Pack(file.getName(), pack_displayname, pack_custommodeldata, rewards);
            } else {
                pack = new Pack(file.getName(), pack_displayname, pack_custommodeldata);
            }
            pack.setEnablepermission(permissions);
            pack.setDropChance(dropchance);
            list.add(pack);
        }
        return list;
    }

    boolean isNum(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
            return true;
        }
        return false;
    }

    int getLastFileNum(File[] files) {
        int mx = 0;
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                String name = files[i].getName().replace(".yml", "");
                name = name.substring(name.lastIndexOf('_') + 1);

                if (!isNum(name)) {
                    continue;
                }
                int n = Integer.parseInt(name);
                if (n > mx) {
                    mx = n;
                }
            }
        }
        return mx;
    }

    String generateName() {
        File[] files = f_packs.listFiles();
        int num = getLastFileNum(files);
        num++;
        return "Pack_" + num;
    }

    public void Save(Pack pack, boolean isnew) {
        if (isnew) {
            addNewPack(pack);
        } else {
            Save(pack);
        }
    }

    public void update(Pack pack) {
        File f = new File(main.mainPlugin.getDataFolder() + File.separator + "packs" + File.separator + pack.Name + ".yml");
        if (!f.exists()) {
            return;
        }
        YamlConfiguration conf = YamlConfiguration.loadConfiguration(f);
        //conf.set("Pack.name", pack.Name);
        conf.set("Pack.displayname", pack.getDisplayname().replace('§', '&'));
        conf.set("Pack.custommodeldata", pack.getCustomModelData());
        conf.set("Pack.chance", pack.getDropChance());
        conf.set("Pack.rewards", null);
        conf.set("Pack.permissions", pack.isEnablepermission());
        if (pack.getRewards().size() > 0) {
            ConfigurationSection section = conf.createSection("Pack.rewards");
            for (RewardAbstract reward : pack.getRewards()) {
                reward.Save(section);
            }
        }

        try {
            conf.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Save(Pack pack) {
        File f = new File(main.mainPlugin.getDataFolder() + File.separator + "packs" + File.separator + pack.Name + ".yml");
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        YamlConfiguration conf = YamlConfiguration.loadConfiguration(f);
        //conf.set("Pack.name", pack.Name);
        conf.set("Pack.displayname", pack.getDisplayname().replace('§', '&'));
        conf.set("Pack.custommodeldata", pack.getCustomModelData());
        conf.set("Pack.chance", pack.getDropChance());
        conf.set("Pack.rewards", null);
        conf.set("Pack.permissions", pack.isEnablepermission());
        if (pack.getRewards().size() > 0) {
            ConfigurationSection section = conf.createSection("Pack.rewards");
            for (RewardAbstract reward : pack.getRewards()) {
                reward.Save(section);
            }
        }

        try {
            conf.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addNewPack(Pack pack) {
        pack.Name = generateName();
        File f = new File(main.mainPlugin.getDataFolder() + File.separator + "packs" + File.separator + pack.Name + ".yml");

        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        YamlConfiguration conf = YamlConfiguration.loadConfiguration(f);
        //conf.set("Pack.name", pack.Name);
        conf.set("Pack.displayname", pack.getDisplayname().replace('§', '&'));
        conf.set("Pack.custommodeldata", pack.getCustomModelData());
        conf.set("Pack.chance", pack.getDropChance());
        conf.set("Pack.rewards", null);
        if (pack.getRewards().size() > 0) {
            ConfigurationSection section = conf.createSection("Pack.rewards");
            for (RewardAbstract reward : pack.getRewards()) {
                reward.Save(section);
            }
        }

        try {
            conf.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean removePack(Pack pack) {
        File f = new File(main.mainPlugin.getDataFolder() + File.separator + "packs" + File.separator + pack.Name + ".yml");
        return f.delete();
    }

    public boolean removePack(String name) {
        File f = new File(main.mainPlugin.getDataFolder() + File.separator + "packs" + File.separator + name + ".yml");
        return f.delete();
    }

    public Pack laodFromFile(Pack pack) {
        File f = new File(main.mainPlugin.getDataFolder() + File.separator + "packs" + File.separator + pack.Name + ".yml");
        if (!f.exists()) {
            return null;
        }
        YamlConfiguration conf = YamlConfiguration.loadConfiguration(f);

        String pack_displayname = conf.getString("Pack.displayname", "").replace('&', '§');
        //String pack_name = conf.getString("Pack.name", "");
        int pack_custommodeldata = conf.getInt("Pack.custommodeldata");
        int dropchance = conf.getInt("Pack.chance");
        boolean permissions = conf.getBoolean("Pack.permissions");
        ConfigurationSection sec_rewards = conf.getConfigurationSection("Pack.rewards");
        if (sec_rewards != null) {
            Set<String> keys_rewards = sec_rewards.getKeys(false);

            List<RewardAbstract> rewards = new ArrayList<>(keys_rewards.size());
            for (String key_reward : keys_rewards) {
                String type = sec_rewards.getString(key_reward + ".type");
                if (type.equals("item")) {
                    String material = sec_rewards.getString(key_reward + ".material");
                    int count = sec_rewards.getInt(key_reward + ".amount");
                    int custommodeldata = sec_rewards.getInt(key_reward + ".custommodeldata", -1);
                    String displayname = sec_rewards.getString(key_reward + ".displayname", "").replace('&', '§');

                    int chance = sec_rewards.getInt(key_reward + ".chance");

                    Material m = Material.getMaterial(material);
                    ItemStack is = new ItemStack(m, count);
                    ItemMeta meta = is.getItemMeta();
                    if (displayname.length() > 0)
                        meta.setDisplayName(displayname);
                    if (custommodeldata > -1)
                        meta.setCustomModelData(custommodeldata);
                    is.setItemMeta(meta);
                    RewardItem rewardItem = new RewardItem(is, chance);
                    rewards.add(rewardItem);
                }
                if (type.equals("mob")) {
                    String mobtype = sec_rewards.getString(key_reward + ".entitytype");
                    EntityType etype = EntityType.valueOf(mobtype);
                    int amount = sec_rewards.getInt(key_reward + ".amount");
                    int chance = sec_rewards.getInt(key_reward + ".chance");
                    RewardEntity rewardEntity = new RewardEntity(etype, amount, chance);

                    ConfigurationSection equipsection = sec_rewards.getConfigurationSection(key_reward + ".equipment");
                    if (equipsection != null) {
                        for (String type1 : equipsection.getKeys(false)) {
                            Material m = Material.getMaterial(type1);
                            rewardEntity.setArmor(type1, new ItemStack(m));

                            ConfigurationSection enchantSection = equipsection.getConfigurationSection(type1 + ".enchants");
                            if (enchantSection != null) {
                                for (String enchant : enchantSection.getKeys(false)) {
                                    rewardEntity.getArmor(type1).addEnchantment(Enchantment.getByKey(NamespacedKey.fromString(enchant)), enchantSection.getInt(enchant + ".level"));
                                }
                            }
                        }
                    }

                    rewards.add(rewardEntity);
                }
                if (type.equals("command")) {
                    String command = sec_rewards.getString(key_reward + ".command");
                    RewardCommand rewardCommand = new RewardCommand(command);
                    rewards.add(rewardCommand);
                }
            }
            pack = new Pack(f.getName(), pack_displayname, pack_custommodeldata, rewards);
        } else {
            pack = new Pack(f.getName(), pack_displayname, pack_custommodeldata);
        }
        pack.setDropChance(dropchance);
        pack.setEnablepermission(permissions);
        return pack;
    }

}
