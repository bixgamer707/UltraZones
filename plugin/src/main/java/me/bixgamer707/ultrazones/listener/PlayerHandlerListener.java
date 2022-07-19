package me.bixgamer707.ultrazones.listener;

import me.bixgamer707.ultrazones.UltraZones;
import me.bixgamer707.ultrazones.user.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.concurrent.CompletableFuture;

public class PlayerHandlerListener implements Listener {
    private final UltraZones plugin;
    public PlayerHandlerListener(UltraZones plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onAsyncPreJoin(AsyncPlayerPreLoginEvent event){
        CompletableFuture<User> user = plugin.getUsersManager().getUserByUuid(
                event.getUniqueId(),
                event.getName()
        );

        user.thenAcceptAsync(User::loadData);
    }
}
