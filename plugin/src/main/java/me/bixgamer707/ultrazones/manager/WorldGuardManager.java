package me.bixgamer707.ultrazones.manager;

import com.sk89q.worldguard.WorldGuard;
import me.bixgamer707.ultrazones.UltraZones;
import me.bixgamer707.ultrazones.file.File;
import me.bixgamer707.ultrazones.user.User;
import me.bixgamer707.ultrazones.utils.Text;
import me.bixgamer707.ultrazones.wgevents.Entry;
import me.bixgamer707.ultrazones.wgevents.WorldGuardChecks;

public class WorldGuardManager implements LoaderManager {
    private final UltraZones plugin;
    private WorldGuardChecks worldGuardChecks;
    private int taskID;
    public WorldGuardManager(UltraZones plugin){
        this.plugin = plugin;
        this.taskID = 0;
    }

    public boolean startWG(){
        new WorldGuardChecks(
                WorldGuard.getInstance().getPlatform().getRegionContainer()
        );
        try{
            Class.forName("com.sk89q.worldguard.session.handler");
            if (!WorldGuard.getInstance().getPlatform().getSessionManager().registerHandler(Entry.factory, null)) {
                Text.sendMsgConsole(
                        "&cCould not register the entry handler!",
                        "&ePlease report this error."
                );

                return false;
            }
            return true;
        } catch (ClassNotFoundException e) {
            Text.sendMsgConsole(
                    "&cYou are using an old version of world guard",
                    "&ein this version the performance can be terrible."
            );
            return false;
        }
    }

    public WorldGuardChecks getWorldGuardChecks() {
        return worldGuardChecks;
    }

    public void startZones(){
        File config = plugin.getFileManager().getConfig();
        taskID = (plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, () ->{
            if(!config.contains("Zones")) return;
            for(User user : plugin.getUsersManager().userMap.values()){
                for(String zone : config.getConfigurationSection("Zones").getKeys(false)){
                    if(worldGuardChecks.isPlayerInAnyRegion(user.getUniqueId(), zone)){
                        user.setCurrentZone(zone);
                        break;
                    }
                }

            }

        },0L,30L).getTaskId());
    }

    @Override
    public void start() {
        startWG();
        startZones();
    }

    @Override
    public void stop() {
        worldGuardChecks = null;
        plugin.getServer().getScheduler().cancelTask(taskID);
    }
}
