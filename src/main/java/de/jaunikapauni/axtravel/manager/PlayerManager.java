package de.jaunikapauni.axtravel.manager;

import de.jaunikapauni.axtravel.AxTravel;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class PlayerManager {
    AxTravel reference;
    public PlayerManager(AxTravel reference){
        this.reference = reference;
    }
    public void home(Player p, String name){
        try(Connection conn = reference.getDatabaseManager().getConnection()){
            UUID uuid = p.getUniqueId();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM homes WHERE uuid = ? AND name = ?");
            ps.setString(1, uuid.toString());
            ps.setString(2, name);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                World world = Bukkit.getWorld("world");
                double x = rs.getDouble("x");
                double y = rs.getDouble("y");
                double z = rs.getDouble("z");
                float yaw = rs.getFloat("yaw");
                float pitch = rs.getFloat("pitch");
                Location loc = new Location(world, x, y, z, yaw, pitch);
                p.teleport(loc);
                p.sendMessage("You was teleported to " + name);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void homes(Player p){
        try (Connection conn = reference.getDatabaseManager().getConnection()) {
            UUID uuid = p.getUniqueId();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM homes WHERE uuid = ?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            p.sendMessage("Your homes:");
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
    }

    public void setHome(Player p, Location loc, String name){
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
            ps.setString(8, name);
            ps.executeUpdate();
            p.sendMessage("Your home " + name + " was set!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setWarp(Player p, Location loc, String name){
        try(Connection conn = reference.getDatabaseManager().getConnection()){
            UUID uuid = p.getUniqueId();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO warps(uuid, world, x, y, z, pitch, yaw, name) VALUES (?,?,?,?,?,?,?,?)");
            ps.setString(1, uuid.toString());
            ps.setString(2, loc.getWorld().getName());
            ps.setDouble(3, loc.x());
            ps.setDouble(4, loc.y());
            ps.setDouble(5, loc.z());
            ps.setDouble(6, loc.getPitch());
            ps.setDouble(7, loc.getYaw());
            ps.setString(8, name);
            ps.executeUpdate();
            p.sendMessage("Your warp " + name + " was set!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void warp(Player p, String name){
        try(Connection conn = reference.getDatabaseManager().getConnection()){
            UUID uuid = p.getUniqueId();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM warps name = ?");
            ps.setString(1, uuid.toString());
            ps.setString(2, name);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                World world = Bukkit.getWorld("world");
                double x = rs.getDouble("x");
                double y = rs.getDouble("y");
                double z = rs.getDouble("z");
                float yaw = rs.getFloat("yaw");
                float pitch = rs.getFloat("pitch");
                Location loc = new Location(world, x, y, z, yaw, pitch);
                p.teleport(loc);
                p.sendMessage("You was teleported to " + name);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void warps(Player p){
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
    }
}
