package me.bixgamer707.ultrazones;

import me.bixgamer707.ultrazones.manager.*;
import me.bixgamer707.ultrazones.register.RegisterPlugin;
import me.bixgamer707.ultrazones.utils.Text;
import org.bukkit.plugin.java.JavaPlugin;

public class UltraZones extends JavaPlugin {

    private static UltraZones instance;
    private FileManager fileManager;
    private RegisterPlugin registerPlugin;
    private UsersManager usersManager;
    private WorldGuardManager wgManager;
    @Override
    public void onEnable(){
        // CREATING INSTANCES
        instance = this;
        long ms = System.currentTimeMillis();
        this.registerPlugin = new RegisterPlugin(this);
        this.fileManager = new FileManager(this);
        this.wgManager = new WorldGuardManager(this);
        this.usersManager = new UsersManager();

        //REGISTER FUNCTIONS
        loader(
                this.fileManager,
                this.usersManager,
                this.wgManager,
                this.registerPlugin
        );


        new Metrics(this, 15809);
        new UpdateChecker(this, 103295).getVersion(version -> {
            if (this.getDescription().getVersion().equals(version)) {
                getLogger().info(Text.hexColors("&aThere is not a new update available."));
            } else {
                Text.sendMsgConsole(
                        "&6There is a new update available.",
                        "&9You can download in: ",
                        "&bhttps://www.spigotmc.org/resources/ultrazones.103295/"
                );
            }
        });
        Text.sendMsgConsole(
                "&fLOAD PLUGIN IN: &a"+ (System.currentTimeMillis() - ms) + "ms"
        );
    }

    @Override
    public void onDisable(){
        long ms = System.currentTimeMillis();
        unLoader(
                this.registerPlugin,
                this.usersManager,
                this.fileManager,
                this.wgManager
        );
        Text.sendMsgConsole(
                "&fUN LOAD PLUGIN IN: &6"+ (System.currentTimeMillis() - ms) + "ms"
        );
        instance = null;
    }


    public void loader(LoaderManager... loaderManagers){
        for(LoaderManager loaderManager : loaderManagers){
            loaderManager.start();
        }
    }

    public void unLoader(LoaderManager... loaderManagers){
        for(LoaderManager loaderManager : loaderManagers){
            loaderManager.stop();
        }
    }

    public static UltraZones getInstance() {
        return instance;
    }

    public FileManager getFileManager() {
        return this.fileManager;
    }

    public UsersManager getUsersManager() {
        return this.usersManager;
    }

    public WorldGuardManager getWgManager() {
        return wgManager;
    }
}
