package me.bixgamer707.ultrazones;

import me.bixgamer707.ultrazones.manager.*;
import me.bixgamer707.ultrazones.register.RegisterPlugin;
import me.bixgamer707.ultrazones.utils.Text;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class UltraZones extends JavaPlugin {

    private static UltraZones instance;
    private FileManager fileManager;
    public String nmsVer;
    private boolean useOldMethods = false;
    private RegisterPlugin registerPlugin;
    private UsersManager usersManager;
    private WorldGuardManager wgManager;
    @Override
    public void onEnable(){
        //REGISTER NMS VERSION
        nmsVer = Bukkit.getServer().getClass().getPackage().getName();
        nmsVer = nmsVer.substring(nmsVer.lastIndexOf(".") + 1);
        if (nmsVer.equalsIgnoreCase("v1_8_R1") || nmsVer.startsWith("v1_7_")) {
            useOldMethods = true;
        }

        // CREATING INSTANCES
        long currentMs = System.currentTimeMillis();
        instance = this;
        this.fileManager = new FileManager(this);
        this.registerPlugin = new RegisterPlugin(this);
        this.usersManager = new UsersManager();
        this.wgManager = new WorldGuardManager(this);

        //REGISTER FUNCTIONS
        registerPlugin.registerAll();
        fileManager.registerFiles();
        wgManager.startWG();
        new Metrics(this, 15809);
        new UpdateChecker(this, 103295).getVersion(version -> {
            if (this.getDescription().getVersion().equals(version)) {
                getLogger().info(Text.hexColors("&aThere is not a new update available."));
            } else {
                getLogger().info(Text.hexColors("&6There is a new update available."));
                getLogger().info(Text.hexColors("&9You can download in: "));
                getLogger().info(Text.hexColors("&bhttps://www.spigotmc.org/resources/ultrazones.103295/"));
            }
        });
        getLogger().info(Text.hexColors("&fLOAD PLUGIN IN: &a"+ (System.currentTimeMillis() - currentMs) + "ms"));
    }

    @Override
    public void onDisable(){
        wgManager.stop();
        instance = null;
        wgManager = null;
        fileManager = null;
        registerPlugin = null;
        usersManager = null;
        nmsVer = null;
    }


    public static UltraZones getInstance() {
        return instance;
    }

    public FileManager getFileManager() {
        return this.fileManager;
    }

    public boolean isUseOldMethods() {
        return useOldMethods;
    }

    public String getNmsVer() {
        return nmsVer;
    }

    public UsersManager getUsersManager() {
        return this.usersManager;
    }

    public WorldGuardManager getWgManager() {
        return wgManager;
    }
}
