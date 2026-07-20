package de.jaunikapauni.axtravel.command;

import de.jaunikapauni.axtravel.AxTravel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class WarpCommand implements CommandExecutor {
    AxTravel reference;
    public WarpCommand(AxTravel reference){
        this.reference = reference;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Only players can run this command!");
            return true;
        }
        if(args.length == 0){
            sender.sendMessage(ChatColor.RED + "Please enter a warp name!");
            return false;
        }
        Player p = (Player) sender;
        if(!p.hasPermission("axtravel.warp")){
            p.sendMessage("You don't have the permission! [axtravel.warp]");
            return true;
        }
        reference.getPlayerManager().delayTeleport(p, () -> {
            Bukkit.getScheduler().runTaskAsynchronously(reference, () -> {
                String targetServer = reference.getPlayerManager().getWarp(p, args[0])[0];
                if(targetServer == null){
                    return;
                }
                if(reference.getMessage("server").equals(targetServer)){
                    Location loc = reference.getPlayerManager().warp(p, args[0]);
                    Bukkit.getScheduler().runTask(reference, () -> {
                        p.teleport(loc);
                        p.sendMessage("You was teleported to " + args[0]);
                    });
                } else {
                    String[] warp = reference.getPlayerManager().getWarp(p, args[0]);
                    reference.getPlayerManager().savePendingTeleport(p.getUniqueId(), warp[0], warp[1], Double.valueOf(warp[2]), Double.valueOf(warp[3]), Double.valueOf(warp[4]), Float.valueOf(warp[5]), Float.valueOf(warp[6]));
                    reference.getPlayerManager().connectToServer(p, targetServer);
                }
            });
        });
        return true;
    }
}
