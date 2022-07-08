package me.bixgamer707.ultrazones.utils;

import com.google.common.base.Strings;
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
        Pattern HEX_PATTERN = Pattern.compile(UltraZones.getInstance().getFileManager().getConfig().getString("Settings.rgb-color"));
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
        if(config == null)return hexColors("&7Config.yml is null!");

        File messages = new File(UltraZones.getInstance(),config.getString("Language"),
                new java.io.File(UltraZones.getInstance().getFileManager().getLangFolder().toString()));

        return hexColors(messages.getString(path).replaceAll(
                "%prefix%",config.getString("Prefix")
        ));
    }

    public static void colorizeList(CommandSender player, String path){
        File config = UltraZones.getInstance().getFileManager().getConfig();
        File messages = new File(UltraZones.getInstance(),config.getString("Language"),
                new java.io.File(UltraZones.getInstance().getDataFolder().getAbsolutePath() + "/lang/"));

        if(player instanceof Player){
            List<String> list = messages.getStringList(path);
            StringBuilder message = new StringBuilder();
            for(int i=0;i<list.size();i++){
                message.append(sanitizeString((Player) player,list.get(i)));
            }
            player.sendMessage(message.toString());
            return;
        }
        List<String> list = messages.getStringList(path);
        for(String text : list){
            UltraZones.getInstance().getLogger().info(text);
        }
    }

    public static void colorizeList(Player player, String path){
        File config = UltraZones.getInstance().getFileManager().getConfig();
        File messages = new File(UltraZones.getInstance(),config.getString("Language"),
                new java.io.File(UltraZones.getInstance().getDataFolder().getAbsolutePath() + "/lang/"));

        List<String> list = messages.getStringList(path);
        StringBuilder message = new StringBuilder();
        for(int i=0;i<list.size();i++){
            message.append(sanitizeString((Player) player,list.get(i)));
        }
        player.sendMessage(message.toString());
    }

    public static void sendActionBar(Player player, String message) {
        if (!player.isOnline()) return;

        if(Utils.versionEquals("1.9","1.10","1.11","1.12","1.13","1.14","1.15","1.16","1.17","1.18","1.19")){
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(hexColors(message)));
        }else{
            String nmsver = UltraZones.getInstance().getNmsVer();
            try {
                Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + nmsver + ".entity.CraftPlayer");
                Object craftPlayer = craftPlayerClass.cast(player);
                Object packet;
                Class<?> packetPlayOutChatClass = Class.forName("net.minecraft.server." + nmsver + ".PacketPlayOutChat");
                Class<?> packetClass = Class.forName("net.minecraft.server." + nmsver + ".Packet");
                if (UltraZones.getInstance().isUseOldMethods()) {
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
    public static String getProgressBar(int current, int max, int totalBars, String symbol, String completedColor, String notCompletedColor) {
        float percent = (float) current/max;
        int progressBars = (int) (totalBars * percent);

        return Strings.repeat("" + completedColor + symbol, progressBars) + Strings.repeat("" + notCompletedColor + symbol, totalBars - progressBars);
    }
}