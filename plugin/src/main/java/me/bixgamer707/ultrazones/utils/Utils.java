package me.bixgamer707.ultrazones.utils;

import org.bukkit.Bukkit;

public class Utils {

    public static boolean versionEquals(String... versions) {
        String ver = Bukkit.getServer().getVersion();
        for(String v : versions){
            if(ver.contains(v)) return true;
        }
        return false;
    }


}
