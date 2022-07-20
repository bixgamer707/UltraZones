package me.bixgamer707.ultrazones.register;

import me.bixgamer707.ultrazones.UltraZones;
import me.bixgamer707.ultrazones.commands.MainCommand;
import me.bixgamer707.ultrazones.listener.UserHandler;
import me.bixgamer707.ultrazones.listener.PlayerHandlerListener;
import me.bixgamer707.ultrazones.manager.LoaderManager;
import me.bixgamer707.ultrazones.placeholders.Placeholders;
import me.bixgamer707.ultrazones.tabcomplete.OldPaperTabCompleter;
import me.bixgamer707.ultrazones.tabcomplete.PaperTabCompleter;
import me.bixgamer707.ultrazones.tabcomplete.SpigotTabCompleter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class RegisterPlugin implements LoaderManager {
    private final UltraZones plugin;
    public RegisterPlugin(UltraZones plugin){
        this.plugin = plugin;
    }

    public void registerListener(Listener... listeners){
        for(Listener listener : listeners){
            plugin.getServer().getPluginManager().registerEvents(listener,plugin);
        }
    }

    public void registerCommand(){
        plugin.getCommand("ultrazones").setExecutor(new MainCommand(plugin));
    }

    public void registerTabCompletion() {
        try {
            Class.forName("com.destroystokyo.paper.event.server.AsyncTabCompleteEvent");
            plugin.getServer().getPluginManager().registerEvents(
                    hasAdventure()
                            ? new PaperTabCompleter()
                            : new OldPaperTabCompleter(),
                    plugin);
        } catch (ClassNotFoundException e) {
            plugin.getCommand("ultrazones").setTabCompleter(new SpigotTabCompleter());
        }
    }

    public boolean hasAdventure() {
        try {
            Class<?> clazz = Class.forName("net.kyori.adventure.text.Component");
            clazz.getMethod("text", String.class);
            return true;
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            return false;
        }
    }

    public void registerPlaceholders(){
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
            new Placeholders(plugin).register();
        }
    }

    @Override
    public void start() {
        registerCommand();
        registerListener(
                new UserHandler(plugin),
                new PlayerHandlerListener(plugin)
        );
        registerTabCompletion();
        registerPlaceholders();
    }

    @Override
    public void stop() {

    }
}
