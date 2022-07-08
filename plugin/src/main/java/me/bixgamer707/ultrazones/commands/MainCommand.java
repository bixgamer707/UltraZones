package me.bixgamer707.ultrazones.commands;

import me.bixgamer707.ultrazones.Main;
import me.bixgamer707.ultrazones.file.FileCreator;
import me.bixgamer707.ultrazones.utils.Text;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class MainCommand implements CommandExecutor {
    private final Main plugin;
    public MainCommand(Main plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        FileCreator config = plugin.getFileManager().getConfig();
        FileCreator messagesES = plugin.getFileManager().getMessages_ES();
        FileCreator messagesEN = plugin.getFileManager().getMessages_EN();
        FileCreator saves = plugin.getFileManager().getSaves();
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
