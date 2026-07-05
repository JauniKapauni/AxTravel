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

public class HomeCommand implements CommandExecutor {
    AxTravel reference;
    public HomeCommand(AxTravel reference){
        this.reference = reference;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Only players can run this command!");
            return true;
        }
        if(args.length == 0){
            sender.sendMessage(ChatColor.RED + "Please enter a home name!");
            return false;
        }
        Player p = (Player) sender;
        if(!p.hasPermission("axtravel.home")){
            p.sendMessage("You don't have the permission! [axtravel.home]");
            return true;
        }
        String targetServer = reference.getPlayerManager().getHome(p, args[0])[0];
        if(reference.getMessage("server").equals(targetServer)){
            reference.getPlayerManager().home(p, args[0]);
        } else {
            String[] home = reference.getPlayerManager().getHome(p, args[0]);
            reference.getPlayerManager().savePendingTeleport(p.getUniqueId(), home[0], home[1], Double.valueOf(home[2]), Double.valueOf(home[3]), Double.valueOf(home[4]), Float.valueOf(home[5]), Float.valueOf(home[6]));
            reference.getPlayerManager().connectToServer(p, targetServer);
        }
        return true;
    }
}
