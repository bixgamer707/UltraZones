package me.bixgamer707.ultrazones.tabcomplete;

import com.destroystokyo.paper.event.server.AsyncTabCompleteEvent;
import com.destroystokyo.paper.event.server.AsyncTabCompleteEvent.Completion;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public final class PaperTabCompleter implements Listener {
    private final Completion reloadCompletion;

    public PaperTabCompleter() {
        reloadCompletion = Completion.completion(
                "reload", Component.text("Reload the plugin", NamedTextColor.GREEN));
    }

    @EventHandler
    public void onTabComplete(AsyncTabCompleteEvent event) {
        final String buffer = event.getBuffer();
        final String input = buffer.charAt(0) == '/' ? buffer.substring(1) : buffer;
        final String[] tokens = input.split(" ");

        if(tokens.length == 0) return;

        if (tokens.length == 1 && ("ultrazones".equalsIgnoreCase(tokens[0]) || "uz".equalsIgnoreCase(tokens[0]) || "ultrazone".equalsIgnoreCase(tokens[0]))) {
            final List<Completion> completions = new ArrayList<>(1);
            if (event.getSender().hasPermission("ultrazones.command.reload")) {
                completions.add(reloadCompletion);
            }
            event.completions(completions);
        }
    }
}
