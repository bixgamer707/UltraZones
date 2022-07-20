package me.bixgamer707.ultrazones.manager;

import com.sk89q.worldguard.WorldGuard;
import me.bixgamer707.ultrazones.UltraZones;
import me.bixgamer707.ultrazones.file.File;
import me.bixgamer707.ultrazones.user.User;
import me.bixgamer707.ultrazones.utils.Text;
import me.bixgamer707.ultrazones.wgevents.Entry;
import me.bixgamer707.ultrazones.wgevents.WorldGuardChecks;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class WorldGuardManager implements LoaderManager {
    private final UltraZones plugin;
    private WorldGuardChecks worldGuardChecks;
    private int taskID;
    public WorldGuardManager(UltraZones plugin){
        this.plugin = plugin;
        this.taskID = 0;
    }

    public boolean startWG(){
        this.worldGuardChecks = new WorldGuardChecks(
                WorldGuard.getInstance().getPlatform().getRegionContainer()
        );
        try{
            Class.forName("com.sk89q.worldguard.session.handler.Handler");
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
            if(!config.contains("Zones")) {
                return;
            }

            for(Player player : Bukkit.getOnlinePlayers()){
                 User user = plugin.getUsersManager().getUserByUuid(
                        player.getUniqueId(),
                        player.getName()
                ).join();

                user.setCurrentZone(config.getString("Player-no-region"));
                for(String regions : getWorldGuardChecks().getRegionsNames(player.getUniqueId())) {
                    if (config.contains("Zones." + regions)) {
                        if (!config.getBoolean("Zones." + regions + ".placeholder.enable")) {
                            continue;
                        }
                        user.setCurrentZone(config.getString("Zones." + regions + ".placeholder.replacer"));

                    }
                }
            }
        },0L,20L).getTaskId());
    }

    @Override
    public void start() {
        startWG();
        startZones();
    }

    @Override
    public void stop() {
        plugin.getServer().getScheduler().cancelTask(taskID);
    }
}
