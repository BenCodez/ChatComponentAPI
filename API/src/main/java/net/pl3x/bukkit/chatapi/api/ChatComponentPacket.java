package net.pl3x.bukkit.chatapi.api;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.entity.Player;

public interface ChatComponentPacket {
    void sendMessage(Player player, ChatMessageType position, BaseComponent... components);
}
