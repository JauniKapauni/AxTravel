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
        Location loc = Bukkit.getServer().getPlayer(p.getUniqueId()).getLocation();
        try(Connection conn = reference.getDatabaseManager().getConnection()){
            UUID uuid = p.getUniqueId();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO homes(uuid, world, x, y, z, pitch, yaw, name) VALUES (?,?,?,?,?,?,?,?)");
            ps.setString(1, uuid.toString());
            ps.setString(2, loc.getWorld().getName());
            ps.setDouble(3, loc.x());
            ps.setDouble(4, loc.y());
            ps.setDouble(5, loc.z());
            ps.setDouble(6, loc.getPitch());
            ps.setDouble(7, loc.getYaw());
            ps.setString(8, args[0]);
            ps.executeUpdate();
            p.sendMessage("Your home " + args[0] + " was set!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
