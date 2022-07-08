package me.bixgamer707.ultrazones.listener;

import me.bixgamer707.ultrazones.UltraZones;
import me.bixgamer707.ultrazones.user.User;
import me.bixgamer707.ultrazones.wgevents.events.RegionEnteredEvent;
import me.bixgamer707.ultrazones.wgevents.events.RegionLeftEvent;
import me.bixgamer707.ultrazones.wgevents.events.RegionsEnteredEvent;
import me.bixgamer707.ultrazones.wgevents.events.RegionsLeftEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class UserHandler implements Listener {
    private final UltraZones plugin;
    public UserHandler(UltraZones plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onLeftRegion(RegionLeftEvent event){
        User user = plugin.getUsersManager().getUserByUuid(event.getUUID());
        if(user==null)return;

        user.onRegionLeft(event);
    }

    @EventHandler
    public void onLeftRegions(RegionsLeftEvent event){
        User user = plugin.getUsersManager().getUserByUuid(event.getUUID());
        if(user==null)return;

        user.onRegionsLeft(event);
    }

    @EventHandler
    public void onJoinRegion(RegionEnteredEvent event){
        User user = plugin.getUsersManager().getUserByUuid(event.getUUID());
        if(user==null)return;

        user.onRegionJoin(event);
    }

    @EventHandler
    public void onJoinRegions(RegionsEnteredEvent event){
        User user = plugin.getUsersManager().getUserByUuid(event.getUUID());
        if(user==null)return;

        user.onRegionsJoin(event);
    }
}
