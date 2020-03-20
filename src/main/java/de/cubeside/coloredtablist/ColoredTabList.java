package de.cubeside.coloredtablist;

import org.bukkit.*;
import org.bukkit.plugin.java.JavaPlugin;

public class ColoredTabList extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new PlayerJoinEventListener(this), this);
    }

}
