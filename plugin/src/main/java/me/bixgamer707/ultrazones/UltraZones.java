package me.bixgamer707.ultrazones;

import com.sk89q.worldguard.WorldGuard;
import me.bixgamer707.ultrazones.manager.FileManager;
import me.bixgamer707.ultrazones.manager.Metrics;
import me.bixgamer707.ultrazones.manager.UpdateChecker;
import me.bixgamer707.ultrazones.manager.UsersManager;
import me.bixgamer707.ultrazones.register.RegisterPlugin;
import me.bixgamer707.ultrazones.utils.Text;
import me.bixgamer707.ultrazones.wgevents.Entry;
import me.bixgamer707.ultrazones.wgevents.WorldGuardChecks;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class UltraZones extends JavaPlugin {

    private static UltraZones instance;
    private FileManager fileManager;
    public String nmsVer;
    private boolean useOldMethods = false;
    private RegisterPlugin registerPlugin;
    private UsersManager usersManager;
    @Override
    public void onEnable(){
        // CREATING INSTANCES
        long currentMs = System.currentTimeMillis();
        instance = this;
        fileManager = new FileManager(this);
        new WorldGuardChecks(WorldGuard.getInstance().getPlatform().getRegionContainer());
        nmsVer = Bukkit.getServer().getClass().getPackage().getName();
        nmsVer = nmsVer.substring(nmsVer.lastIndexOf(".") + 1);
        if (nmsVer.equalsIgnoreCase("v1_8_R1") || nmsVer.startsWith("v1_7_")) {
            useOldMethods = true;
        }
        registerPlugin = new RegisterPlugin(this);
        usersManager = new UsersManager();

        //REGISTER FUNCTIONS
        if (!WorldGuard.getInstance().getPlatform().getSessionManager().registerHandler(Entry.factory, null)) {
            Text.sendMsgConsole(
                    "&cCould not register the entry handler!",
                    "&ePlease report this error. &4The plugin will now be disabled."
            );

            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        registerPlugin.registerAll();
        fileManager.registerFiles();
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
        instance = null;
        fileManager = null;
        registerPlugin = null;
        usersManager = null;
        nmsVer = null;
    }


    public static UltraZones getInstance() {
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
