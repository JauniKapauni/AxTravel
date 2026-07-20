package de.jaunikapauni.axtravel.command;

import de.jaunikapauni.axtravel.AxTravel;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class SetHomeCommand implements CommandExecutor {
    AxTravel reference;
    public SetHomeCommand(AxTravel reference){
        this.reference = reference;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can run this command!");
            return true;
        }
        Player p = (Player) sender;
        if(!p.hasPermission("axtravel.sethome")){
            p.sendMessage("You don't have the permission! [axtravel.sethome]");
            return true;
        }
        if(args.length == 0){
            return false;
        }
        Location loc = Bukkit.getServer().getPlayer(p.getUniqueId()).getLocation();
        Bukkit.getScheduler().runTaskAsynchronously(reference, () -> {
            reference.getPlayerManager().setHome(p, loc, args[0]);
        });
        return true;
    }
}
