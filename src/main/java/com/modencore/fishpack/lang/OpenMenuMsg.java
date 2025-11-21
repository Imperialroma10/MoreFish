package com.modencore.fishpack.lang;

import org.bukkit.configuration.ConfigurationSection;

public class OpenMenuMsg {


   public final String titlename;

    public OpenMenuMsg(ConfigurationSection configurationSection) {

        titlename = configurationSection.getString("title");

    }
}
