package net.pl3x.bukkit.chatapi.nms.v1_8_R1;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import net.minecraft.server.v1_8_R1.ChatSerializer;
import net.minecraft.server.v1_8_R1.PacketPlayOutChat;
import net.pl3x.bukkit.chatapi.api.ChatComponentPacket;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ChatComponentPacketHandler implements ChatComponentPacket {
    public void sendMessage(Player player, ChatMessageType position, BaseComponent... components) {
        if (player == null) {
            return;
        }
        PacketPlayOutChat packet = new PacketPlayOutChat(
                ChatSerializer.a(ComponentSerializer.toString(components)),
                (byte) position.ordinal());
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }
}
