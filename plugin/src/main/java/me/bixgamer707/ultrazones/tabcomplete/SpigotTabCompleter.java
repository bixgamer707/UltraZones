package me.bixgamer707.ultrazones.tabcomplete;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SpigotTabCompleter implements TabCompleter {

    @Override
    public @Nullable
    List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd,
                               @NotNull String label, @NotNull String[] args) {
        if (args.length > 1) return null;
        final List<String> completions = new ArrayList<>(1);
        if (sender.hasPermission("ultrazones.command.reload")) {
            completions.add("reload");
        }
        return completions;
    }
}
