package de.jaunikapauni.axtravel.command;

import de.jaunikapauni.axtravel.AxTravel;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DelWarpCommand implements CommandExecutor {
    AxTravel reference;
    public DelWarpCommand(AxTravel reference){
        this.reference = reference;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Only players can run this command!");
            return true;
        }
        Player p = (Player) sender;
        if(!p.hasPermission("axtravel.delwarp")){
            p.sendMessage("You don't have the permission! [axtravel.delwarp]");
            return true;
        }
        Bukkit.getScheduler().runTaskAsynchronously(reference, () -> {
            reference.getPlayerManager().delWarp(args[0]);
            Bukkit.getScheduler().runTask(reference, () -> {
                p.sendMessage("The warp " + args[0] + " was deleted");
            });
        });
        return true;
    }
}
