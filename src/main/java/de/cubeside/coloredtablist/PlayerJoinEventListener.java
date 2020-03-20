package de.cubeside.coloredtablist;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class PlayerJoinEventListener implements Listener {

    public Plugin plugin;

    public PlayerJoinEventListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent event) {
        PacketListener.registerPlayerInfoPacketListener(this.plugin);
    }
}
