package me.bixgamer707.ultrazones.commands;

import me.bixgamer707.ultrazones.UltraZones;
import me.bixgamer707.ultrazones.file.File;
import me.bixgamer707.ultrazones.utils.Text;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class MainCommand implements CommandExecutor {
    private final UltraZones plugin;
    public MainCommand(UltraZones plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        File config = plugin.getFileManager().getConfig();
        File messagesES = plugin.getFileManager().getMessages_ES();
        File messagesEN = plugin.getFileManager().getMessages_EN();
        File saves = plugin.getFileManager().getSaves();
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(!(args.length > 0)){
                Text.colorizeList(player,"help-message");
                return false;
            }
            if("reload".equalsIgnoreCase(args[0])){
                if(!(player.hasPermission("ultrazones.command.reload") || player.hasPermission("ultrazones.*"))){
                    player.sendMessage(Text.colorize("no-permission"));
                    return false;
                }
                config.reload();
                messagesEN.reload();
                messagesES.reload();
                saves.reload();
                player.sendMessage(Text.colorize("reload-message"));
                return true;
            }
            Text.colorizeList(player,"help-message");
            return true;
        }
        return false;
    }
}
