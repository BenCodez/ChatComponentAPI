package net.pl3x.bukkit.chatapi.nms.v1_9_R1;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import net.minecraft.server.v1_9_R1.IChatBaseComponent;
import net.minecraft.server.v1_9_R1.PacketPlayOutChat;
import net.pl3x.bukkit.chatapi.api.ChatComponentPacket;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ChatComponentPacketHandler implements ChatComponentPacket {
    public void sendMessage(Player player, ChatMessageType position, BaseComponent... components) {
        if (player == null) {
            return;
        }
        IChatBaseComponent component = IChatBaseComponent.ChatSerializer.a(ComponentSerializer.toString(components));
        PacketPlayOutChat packet = new PacketPlayOutChat(component, (byte) position.ordinal());
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }
}
