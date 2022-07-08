package me.bixgamer707.ultrazones;

import com.sk89q.worldguard.WorldGuard;
import me.bixgamer707.ultrazones.file.FileManager;
import me.bixgamer707.ultrazones.manager.UsersManager;
import me.bixgamer707.ultrazones.register.RegisterPlugin;
import me.bixgamer707.ultrazones.wgevents.WorldGuardChecks;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;
    private FileManager fileManager;
    public String nmsVer;
    private boolean useOldMethods = false;
    private RegisterPlugin registerPlugin;
    private UsersManager usersManager;
    @Override
    public void onEnable(){
        // CREATING INSTANCES
        instance = this;
        fileManager = new FileManager(this);
        new WorldGuardChecks(WorldGuard.getInstance().getPlatform().getRegionContainer());
        nmsVer = Bukkit.getServer().getClass().getPackage().getName();
        nmsVer = nmsVer.substring(nmsVer.lastIndexOf(".") + 1);
        if (nmsVer.equalsIgnoreCase("v1_8_R1") || nmsVer.startsWith("v1_7_")) {
            useOldMethods = true;
        }
        registerPlugin = new RegisterPlugin(this);
        usersManager = new UsersManager(this);

        //REGISTER FUNCTIONS
        registerPlugin.registerAll();
        fileManager.registerFiles();
    }

    @Override
    public void onDisable(){
        instance = null;
        fileManager = null;
        registerPlugin = null;
        nmsVer = null;
    }


    public static Main getInstance() {
        return instance;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public boolean isUseOldMethods() {
        return useOldMethods;
    }

    public String getNmsVer() {
        return nmsVer;
    }

    public UsersManager getUsersManager() {
        return usersManager;
    }
}
