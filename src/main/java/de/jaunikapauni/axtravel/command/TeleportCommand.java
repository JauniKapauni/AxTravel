package de.jaunikapauni.axtravel.command;

import de.jaunikapauni.axtravel.AxTravel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TeleportCommand implements CommandExecutor {
    AxTravel reference;
    public TeleportCommand(AxTravel reference){
        this.reference = reference;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Only players can run this command!");
            return true;
        }
        if(args.length == 0){
            sender.sendMessage(ChatColor.RED + "Please enter a playername!");
            return false;
        }
        Player p = (Player) sender;
        if(!p.hasPermission("axtravel.teleport")){
            p.sendMessage("You don't have the permission! [axtravel.teleport]");
            return true;
        }
        if(args.length == 0){
            return false;
        }
        Player target = Bukkit.getPlayerExact(args[0]);
        if(!target.isOnline()){
            return false;
        }
        p.teleport(target.getLocation());
        p.sendMessage(ChatColor.GREEN + "You were teleported to " + target.getName());
        target.sendMessage(ChatColor.GREEN + p.getName() + " was teleported to you");
        return true;
    }
}
