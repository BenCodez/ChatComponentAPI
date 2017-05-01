package net.pl3x.bukkit.chatapi.nms.v1_10_R1;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;

import net.pl3x.bukkit.chatapi.api.ChatComponentPacket;
import net.pl3x.bukkit.chatapi.api.ReflectionUtils;


import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ChatComponentPacketHandler implements ChatComponentPacket {
	public void sendMessage(Player player, ChatMessageType position, BaseComponent... components) {
		if (player == null) {
			return;
		}

		sendMessageReflection(player, components);
	}

	private void sendMessageReflection(Player player, BaseComponent... components) {
		for (BaseComponent comp : components) {
			String jsonMessage = comp.toLegacyText();
			ReflectionUtils reflectChat = new ReflectionUtils(null, getNMSClass("ChatSerializer"));
			Object chatComponent = ReflectionUtils.invokeMethod(reflectChat.getMethodDeclared("a", String.class), null,
					jsonMessage);
			Object packetPlayOutChat = ReflectionUtils
					.constructObject(new ReflectionUtils(null, getNMSClass("PacketPlayOutChat"))
							.getConstructor(getNMSClass("IChatBaseComponent")), chatComponent);
			Field playerConnection = new ReflectionUtils(null, getNMSClass("EntityPlayer"))
					.getFieldDeclared("playerConnection");
			Method sendPacket = new ReflectionUtils(null, getNMSClass("PlayerConnection"))
					.getMethodDeclared("sendPacket", getNMSClass("Packet"));

			Object handle = ReflectionUtils.invokeMethod(
					new ReflectionUtils(player, player.getClass()).getMethodDeclared("getHandle"), player);
			Object connection = ReflectionUtils.getFieldValue(playerConnection, handle);
			ReflectionUtils.invokeMethod(sendPacket, connection, packetPlayOutChat);
		}
	}

	private Class<?> getNMSClass(String name) {
		return ReflectionUtils.getClassForName("net.minecraft.server."
				+ Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3] + "." + name);
	}
}
