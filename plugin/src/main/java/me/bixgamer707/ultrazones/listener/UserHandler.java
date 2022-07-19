package me.bixgamer707.ultrazones.listener;

import me.bixgamer707.ultrazones.UltraZones;
import me.bixgamer707.ultrazones.user.User;
import me.bixgamer707.ultrazones.wgevents.events.RegionEnteredEvent;
import me.bixgamer707.ultrazones.wgevents.events.RegionLeftEvent;
import me.bixgamer707.ultrazones.wgevents.events.RegionsEnteredEvent;
import me.bixgamer707.ultrazones.wgevents.events.RegionsLeftEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.concurrent.CompletableFuture;

public class UserHandler implements Listener {
    private final UltraZones plugin;
    public UserHandler(UltraZones plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onLeftRegion(RegionLeftEvent event){
        if(event.getPlayer() == null)return;

        CompletableFuture<User> user = plugin.getUsersManager().getUserByUuid(
                event.getPlayer().getUniqueId(),
                event.getPlayer().getName()
        );

        user.thenAcceptAsync(userData -> {
            userData.onRegionLeft(event);
        });
    }

    @EventHandler
    public void onLeftRegions(RegionsLeftEvent event){
        if(event.getPlayer() == null)return;

        CompletableFuture<User> user = plugin.getUsersManager().getUserByUuid(
                event.getPlayer().getUniqueId(),
                event.getPlayer().getName()
        );

        user.thenAcceptAsync(userData -> {
            userData.onRegionsLeft(event);
        });
    }

    @EventHandler
    public void onJoinRegion(RegionEnteredEvent event){
        if(event.getPlayer() == null)return;

        CompletableFuture<User> user = plugin.getUsersManager().getUserByUuid(
                event.getPlayer().getUniqueId(),
                event.getPlayer().getName()
        );

        user.thenAcceptAsync(userData -> {
            userData.onRegionJoin(event);
        });
    }

    @EventHandler
    public void onJoinRegions(RegionsEnteredEvent event){
        if(event.getPlayer() == null)return;

        CompletableFuture<User> user = plugin.getUsersManager().getUserByUuid(
                event.getPlayer().getUniqueId(),
                event.getPlayer().getName()
        );

        user.thenAcceptAsync(userData -> {
            userData.onRegionsJoin(event);
        });
    }
}
