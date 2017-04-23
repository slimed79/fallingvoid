package fr.gravenilvec.fallingvoid.utils;

import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_11_R1.IChatBaseComponent;
import net.minecraft.server.v1_11_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_11_R1.PacketPlayOutChat;
import net.minecraft.server.v1_11_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_11_R1.PacketPlayOutTitle.EnumTitleAction;

public class Title {
	
	public static void sendTitle(Player player, String title, String subtitle, int ticks){
		IChatBaseComponent basetitle = ChatSerializer.a("{\"text\": \"" + title + "\"}");
		IChatBaseComponent basesubtitle = ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");
		PacketPlayOutTitle titlepacket = new PacketPlayOutTitle(EnumTitleAction.TITLE, basetitle);
		PacketPlayOutTitle subtitlepacket = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, basesubtitle);
		sendTime(player, ticks);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(titlepacket);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(subtitlepacket);
	}
	
	private static void sendTime(Player player, int ticks){
		PacketPlayOutTitle titlepacket = new PacketPlayOutTitle(EnumTitleAction.TIMES, null, 20, ticks, 20);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(titlepacket);
	}
	
	public static void sendActionBar(Player player, String message){
		IChatBaseComponent basetitle = ChatSerializer.a("{\"text\": \"" + message + "\"}");
		PacketPlayOutChat packet = new PacketPlayOutChat(basetitle, (byte)2);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}

}


