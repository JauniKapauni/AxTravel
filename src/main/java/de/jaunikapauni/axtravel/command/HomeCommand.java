package de.jaunikapauni.axtravel.command;

import de.jaunikapauni.axtravel.AxTravel;
import org.bukkit.Bukkit;
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
        Player p = (Player) sender;
        try(Connection conn = reference.getDatabaseManager().getConnection()){
            UUID uuid = p.getUniqueId();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM homes WHERE uuid = ? AND name = ?");
            ps.setString(1, uuid.toString());
            ps.setString(2, args[0]);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                World world = Bukkit.getWorld("world");
                double x = rs.getDouble("x");
                double y = rs.getDouble("y");
                double z = rs.getDouble("z");
                float yaw = rs.getFloat("yaw");
                float pitch = rs.getFloat("pitch");
                Location loc = new Location(world, x, y, z, yaw, pitch);
                String name = rs.getString("name");
                p.teleport(loc);
                p.sendMessage("You was teleported to " + name);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
