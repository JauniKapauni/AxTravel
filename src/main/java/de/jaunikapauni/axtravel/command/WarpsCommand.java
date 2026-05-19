package de.jaunikapauni.axtravel.command;

import de.jaunikapauni.axtravel.AxTravel;
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

public class WarpsCommand implements CommandExecutor {
    AxTravel reference;
    public WarpsCommand(AxTravel reference){
        this.reference = reference;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can run this command!");
            return true;
        }
        Player p = (Player) sender;
        try (Connection conn = reference.getDatabaseManager().getConnection()) {
            UUID uuid = p.getUniqueId();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM warps WHERE uuid = ?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            p.sendMessage("Warps:");
            while (rs.next()) {
                int x = rs.getInt("x");
                int y = rs.getInt("y");
                int z = rs.getInt("z");
                String name = rs.getString("name");
                p.sendMessage("- " + name + "(" + x + "|" + y + "|" + z + ")");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
