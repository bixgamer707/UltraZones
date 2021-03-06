package me.bixgamer707.ultrazones.utils;

import me.bixgamer707.ultrazones.UltraZones;
import me.bixgamer707.ultrazones.file.File;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Text {
    public static String hexColors(String text) {
        File config = UltraZones.getInstance().getFileManager().getConfig();
        text = text.replaceAll(
                "%prefix%", config.getString("Prefix"));
        Pattern HEX_PATTERN = Pattern.compile("#[a-fA-F0-9]{6}");
        if (Bukkit.getVersion().contains("1.16")) {
            Matcher match = HEX_PATTERN.matcher(text);
            while (match.find()) {
                String color = text.substring(match.start(), match.end());
                text = text.replace(color, ChatColor.of(color) + "");
                match = HEX_PATTERN.matcher(text);
            }
        }
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String sanitizeString(Player player, String text) {
        if (UltraZones.getInstance().getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            return hexColors(PlaceholderAPI.setPlaceholders(player, text));

        } else {
            return hexColors(text);
        }
    }

    public static String colorize(String path){
        File config = UltraZones.getInstance().getFileManager().getConfig();

        return hexColors(config.getString(path));
    }

    public static void sendMsgConsole(String... msg){
        for(String txt : msg){
            UltraZones.getInstance().getLogger().info(hexColors(txt));
        }
    }

    public static void colorizeList(CommandSender player, String path){
        File config = UltraZones.getInstance().getFileManager().getConfig();

        if(player instanceof Player){
            List<String> list = config.getStringList(path);
            StringBuilder message = new StringBuilder();
            for (String s : list) {
                message.append(sanitizeString((Player) player, s)).append("\n");
            }
            player.sendMessage(Text.hexColors(message.toString()));
            return;
        }
        List<String> list = config.getStringList(path);
        for(String text : list){
            UltraZones.getInstance().getLogger().info(hexColors(text));
        }
    }

    public static void colorizeList(Player player, String path){
        File config = UltraZones.getInstance().getFileManager().getConfig();

        List<String> list = config.getStringList(path);
        StringBuilder message = new StringBuilder();
        for (String s : list) {
            message.append(sanitizeString(player, s));
        }
        player.sendMessage(hexColors(message.toString()));
    }

    public static void sendActionBar(Player player, String message) {
        if (!player.isOnline()) return;

        if(Utils.versionEquals("1.9","1.10","1.11","1.12","1.13","1.14","1.15","1.16","1.17","1.18","1.19")){
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(hexColors(message)));
        }else{
            boolean useOldMethods = false;
            String nmsver = Bukkit.getServer().getClass().getPackage().getName();
            nmsver = nmsver.substring(nmsver.lastIndexOf(".") + 1);
            if (nmsver.equalsIgnoreCase("v1_8_R1") || nmsver.startsWith("v1_7_")) {
                useOldMethods = true;
            }
            try {
                Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + nmsver + ".entity.CraftPlayer");
                Object craftPlayer = craftPlayerClass.cast(player);
                Object packet;
                Class<?> packetPlayOutChatClass = Class.forName("net.minecraft.server." + nmsver + ".PacketPlayOutChat");
                Class<?> packetClass = Class.forName("net.minecraft.server." + nmsver + ".Packet");
                if (useOldMethods) {
                    Class<?> chatSerializerClass = Class.forName("net.minecraft.server." + nmsver + ".ChatSerializer");
                    Class<?> iChatBaseComponentClass = Class.forName("net.minecraft.server." + nmsver + ".IChatBaseComponent");
                    Method m3 = chatSerializerClass.getDeclaredMethod("a", String.class);
                    Object cbc = iChatBaseComponentClass.cast(m3.invoke(chatSerializerClass, "{\"text\": \"" + message + "\"}"));
                    packet = packetPlayOutChatClass.getConstructor(new Class<?>[]{iChatBaseComponentClass, byte.class}).newInstance(cbc, (byte) 2);
                }else {
                    Class<?> chatComponentTextClass = Class.forName("net.minecraft.server." + nmsver + ".ChatComponentText");
                    Class<?> iChatBaseComponentClass = Class.forName("net.minecraft.server." + nmsver + ".IChatBaseComponent");
                    try {
                        Class<?> chatMessageTypeClass = Class.forName("net.minecraft.server." + nmsver + ".ChatMessageType");
                        Object[] chatMessageTypes = chatMessageTypeClass.getEnumConstants();
                        Object chatMessageType = null;
                        for (Object obj : chatMessageTypes) {
                            if (obj.toString().equals("GAME_INFO")) {
                                chatMessageType = obj;
                            }
                        }
                        Object chatCompontentText = chatComponentTextClass.getConstructor(new Class<?>[]{String.class}).newInstance(message);
                        packet = packetPlayOutChatClass.getConstructor(new Class<?>[]{iChatBaseComponentClass, chatMessageTypeClass}).newInstance(chatCompontentText, chatMessageType);
                    } catch (ClassNotFoundException cnfe) {
                        Object chatCompontentText = chatComponentTextClass.getConstructor(new Class<?>[]{String.class}).newInstance(message);
                        packet = packetPlayOutChatClass.getConstructor(new Class<?>[]{iChatBaseComponentClass, byte.class}).newInstance(chatCompontentText, (byte) 2);
                    }
                }
                Method craftPlayerHandleMethod = craftPlayerClass.getDeclaredMethod("getHandle");
                Object craftPlayerHandle = craftPlayerHandleMethod.invoke(craftPlayer);
                Field playerConnectionField = craftPlayerHandle.getClass().getDeclaredField("playerConnection");
                Object playerConnection = playerConnectionField.get(craftPlayerHandle);
                Method sendPacketMethod = playerConnection.getClass().getDeclaredMethod("sendPacket", packetClass);
                sendPacketMethod.invoke(playerConnection, packet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}