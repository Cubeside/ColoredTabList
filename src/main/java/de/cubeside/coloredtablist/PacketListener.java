package de.cubeside.coloredtablist;

import com.comphenix.packetwrapper.WrapperPlayServerPlayerInfo;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;

import static com.comphenix.protocol.PacketType.Play.Server.PLAYER_INFO;

public class PacketListener {
    public static void registerPlayerInfoPacketListener(Plugin plugin) {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(plugin, PLAYER_INFO) {
            @Override
            public void onPacketSending(PacketEvent event) {
                WrapperPlayServerPlayerInfo wrapper = new WrapperPlayServerPlayerInfo(event.getPacket());

                List<PlayerInfoData> playerInfoDataList = wrapper.getData();

                if (wrapper.getAction() == EnumWrappers.PlayerInfoAction.ADD_PLAYER) {
                    List<PlayerInfoData> newPlayerInfoDataList = Lists.newArrayList();
                    for (PlayerInfoData playerInfoData : playerInfoDataList) {
                        Player player;

                        if (playerInfoData == null || playerInfoData.getProfile() == null || (player = Bukkit.getPlayer(playerInfoData.getProfile().getUUID())) == null || !player.isOnline()) {
                            newPlayerInfoDataList.add(playerInfoData);
                            continue;
                        }

                        WrappedChatComponent newDisplayName = getNewDisplayName(playerInfoData, player);

                        PlayerInfoData newPlayerInfoData = new PlayerInfoData(playerInfoData.getProfile(), playerInfoData.getPing(), playerInfoData.getGameMode(), newDisplayName);
                        newPlayerInfoDataList.add(newPlayerInfoData);
                    }
                    wrapper.setData(newPlayerInfoDataList);
                }
            }
        });
    }

    public static WrappedChatComponent getNewDisplayName(PlayerInfoData playerInfoData, Player player) {
        if (playerInfoData.getGameMode() != EnumWrappers.NativeGameMode.SPECTATOR) {
            return WrappedChatComponent.fromText(ChatColor.LIGHT_PURPLE + "[Staff] " + player.getName());
        } else {
            return playerInfoData.getDisplayName();
        }
    }
}