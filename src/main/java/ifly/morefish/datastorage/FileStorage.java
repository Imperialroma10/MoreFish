package ifly.morefish.datastorage;

import ifly.morefish.fishpack.pack.Pack;
import ifly.morefish.fishpack.pack.reward.RewardAbstract;
import ifly.morefish.fishpack.pack.reward.RewardCommand;
import ifly.morefish.fishpack.pack.reward.RewardEntity;
import ifly.morefish.fishpack.pack.reward.RewardItem;
import ifly.morefish.main;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FileStorage {

    String filename = "pack.yml";
    File f = new File(main.mainPlugin.getDataFolder().getPath()+File.separator+ "packs");
    public FileStorage() {
        if(!f.exists()) {
            main.mainPlugin.saveResource("packs/pack.yml", false);
        }
    }

    public List<Pack> getPacks() {


        if (!f.exists()){
            f.mkdirs();
        }
        File[] files = f.listFiles();
        List<Pack> list = new ArrayList<>(files.length);

        for(File file : files) {

            YamlConfiguration conf = YamlConfiguration.loadConfiguration(file);

            String pack_displayname = conf.getString("Pack.displayname", "").replace('&', '§');
            String pack_name = conf.getString("Pack.name", "");
            int pack_custommodeldata = conf.getInt("Pack.custommodeldata");
            int dropchance = conf.getInt("Pack.chance");

            ConfigurationSection sec_rewards = conf.getConfigurationSection("Pack.rewards");
            Set<String> keys_rewards = sec_rewards.getKeys(false);

            List<RewardAbstract> rewards = new ArrayList<>(keys_rewards.size());
            for(String key_reward : keys_rewards) {
                String type = sec_rewards.getString(key_reward + ".type");
                if(type.equals("item")) {
                    String material = sec_rewards.getString(key_reward + ".material").toUpperCase();
                    int count = sec_rewards.getInt(key_reward + ".amount");
                    int custommodeldata = sec_rewards.getInt(key_reward + ".custommodeldata", -1);
                    String displayname = sec_rewards.getString(key_reward + ".displayname", "").replace('&', '§');

                    int chance = sec_rewards.getInt(key_reward + ".chance");

                    Material m = Material.getMaterial(material);
                    ItemStack is = new ItemStack(m, count);
                    if(displayname.length() > 0)
                        is.editMeta(im -> im.displayName(Component.text(displayname)));
                    if(custommodeldata > -1)
                        is.editMeta(im -> im.setCustomModelData(custommodeldata));

                    RewardItem rewardItem = new RewardItem(is, chance);

                    ConfigurationSection enchantSection = sec_rewards.getConfigurationSection(key_reward+".enchants");
                    if (enchantSection != null){
                        for (String enchant: enchantSection.getKeys(false)){
                            rewardItem.addEnchantments(Enchantment.getByName(enchant), enchantSection.getInt(enchant+".level"));
                        }
                    }
                    rewards.add(rewardItem);


                }
                if(type.equals("mob")) {
                    String mobtype = sec_rewards.getString(key_reward + ".entitytype");
                    EntityType etype = EntityType.valueOf(mobtype);
                    int amount = sec_rewards.getInt(key_reward + ".amount");
                    int chance = sec_rewards.getInt(key_reward + ".chance");
                    RewardEntity rewardEntity = new RewardEntity(etype, amount, chance);

                    ConfigurationSection equipsection = sec_rewards.getConfigurationSection(key_reward+".equipment");
                    if (equipsection != null){
                        for (String type1: equipsection.getKeys(false)) {
							Material m = Material.getMaterial(type1);
							rewardEntity.setArmor(type1, new ItemStack(m));

							ConfigurationSection enchantSection = equipsection.getConfigurationSection(type1 + ".enchants");
							if(enchantSection != null) {
								for(String enchant : enchantSection.getKeys(false)) {
									rewardEntity.getArmor(type1).addEnchantment(Enchantment.getByKey(NamespacedKey.fromString(enchant)), enchantSection.getInt(enchant + ".level"));
								}
							}
						}
                    }

                    rewards.add(rewardEntity);
                }
                if(type.equals("command")) {
                    String command = sec_rewards.getString(key_reward + ".command");
                    RewardCommand rewardCommand = new RewardCommand(command);
                    rewards.add(rewardCommand);
                }
            }

            Pack pack = new Pack(pack_name, pack_displayname, pack_custommodeldata, rewards);
            pack.setDropChance(dropchance);
            list.add(pack);
        }
        return list;
    }

    public Pack Update(Pack pack)
	{
		File f = new File(main.mainPlugin.getDataFolder() + File.separator + pack.Name + ".yml");
		if(!f.exists()) { return null; }
		YamlConfiguration conf = YamlConfiguration.loadConfiguration(f);

		String pack_displayname = conf.getString("Pack.displayname", "").replace('&', '§');
		String pack_name = conf.getString("Pack.name", "");
		int pack_custommodeldata = conf.getInt("Pack.custommodeldata");
		int dropchance = conf.getInt("Pack.chance");

		ConfigurationSection sec_rewards = conf.getConfigurationSection("Pack.rewards");
		Set<String> keys_rewards = sec_rewards.getKeys(false);

		List<RewardAbstract> rewards = new ArrayList<>(keys_rewards.size());
		for(String key_reward : keys_rewards) {
			String type = sec_rewards.getString(key_reward + ".type");
			if(type.equals("item")) {
				String material = sec_rewards.getString(key_reward + ".material");
				int count = sec_rewards.getInt(key_reward + ".amount");
				int custommodeldata = sec_rewards.getInt(key_reward + ".custommodeldata", -1);
				String displayname = sec_rewards.getString(key_reward + ".displayname", "").replace('&', '§');

				int chance = sec_rewards.getInt(key_reward + ".chance");

				Material m = Material.getMaterial(material);
				ItemStack is = new ItemStack(m, count);
				if(displayname.length() > 0)
					is.editMeta(im -> im.displayName(Component.text(displayname)));
				if(custommodeldata > -1)
					is.editMeta(im -> im.setCustomModelData(custommodeldata));
				RewardItem rewardItem = new RewardItem(is, chance);
				rewards.add(rewardItem);
			}
			if(type.equals("mob")) {
				String mobtype = sec_rewards.getString(key_reward + ".entitytype");
				EntityType etype = EntityType.valueOf(mobtype);
				int amount = sec_rewards.getInt(key_reward + ".amount");
				int chance = sec_rewards.getInt(key_reward + ".chance");
				RewardEntity rewardEntity = new RewardEntity(etype, amount, chance);
				rewards.add(rewardEntity);
			}
			if(type.equals("command")) {
				String command = sec_rewards.getString(key_reward + ".command");
				RewardCommand rewardCommand = new RewardCommand(command);
				rewards.add(rewardCommand);
			}
		}
		pack = new Pack(pack_name, pack_displayname, pack_custommodeldata, rewards);
		pack.setDropChance(dropchance);
		return pack;
	}

}
