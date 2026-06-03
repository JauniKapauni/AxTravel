package de.jaunikapauni.axtravel.manager;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import de.jaunikapauni.axtravel.AxTravel;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerManager {
    AxTravel reference;

    public PlayerManager(AxTravel reference) {
        this.reference = reference;
    }

    public void home(Player p, String name) {
        try (Connection conn = reference.getDatabaseManager().getConnection()) {
            UUID uuid = p.getUniqueId();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM homes WHERE uuid = ? AND name = ?");
            ps.setString(1, uuid.toString());
            ps.setString(2, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
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

    public void homes(Player p) {
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

    public void setHome(Player p, Location loc, String name) {
        try (Connection conn = reference.getDatabaseManager().getConnection()) {
            UUID uuid = p.getUniqueId();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO homes(uuid, server, world, x, y, z, pitch, yaw, name) VALUES (?,?,?,?,?,?,?,?,?)");
            ps.setString(1, uuid.toString());
            ps.setString(2, reference.getMessage("server"));
            ps.setString(3, loc.getWorld().getName());
            ps.setDouble(4, loc.x());
            ps.setDouble(5, loc.y());
            ps.setDouble(6, loc.z());
            ps.setDouble(7, loc.getPitch());
            ps.setDouble(8, loc.getYaw());
            ps.setString(9, name);
            ps.executeUpdate();
            p.sendMessage("Your home " + name + " was set!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setWarp(Player p, Location loc, String name) {
        try (Connection conn = reference.getDatabaseManager().getConnection()) {
            UUID uuid = p.getUniqueId();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO warps(uuid, server, world, x, y, z, pitch, yaw, name) VALUES (?,?,?,?,?,?,?,?,?)");
            ps.setString(1, uuid.toString());
            ps.setString(2, reference.getMessage("server"));
            ps.setString(3, loc.getWorld().getName());
            ps.setDouble(4, loc.x());
            ps.setDouble(5, loc.y());
            ps.setDouble(6, loc.z());
            ps.setDouble(7, loc.getPitch());
            ps.setDouble(8, loc.getYaw());
            ps.setString(9, name);
            ps.executeUpdate();
            p.sendMessage("Your warp " + name + " was set!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void warp(Player p, String name) {
        try (Connection conn = reference.getDatabaseManager().getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM warps WHERE name = ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
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

    public void warps(Player p) {
        try (Connection conn = reference.getDatabaseManager().getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM warps");
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

    public void savePendingTeleport(UUID uuid, String targetServer, String world, double x, double y, double z, float yaw, float pitch) {
        try (Connection conn = reference.getDatabaseManager().getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO pending_teleports(uuid, target, world, x, y, z, yaw, pitch) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {
                ps.setString(1, uuid.toString());
                ps.setString(2, targetServer);
                ps.setString(3, world);
                ps.setDouble(4, x);
                ps.setDouble(5, y);
                ps.setDouble(6, z);
                ps.setFloat(7, yaw);
                ps.setFloat(8, pitch);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String[] getHome(Player p, String name) {
        try (Connection conn = reference.getDatabaseManager().getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM homes WHERE uuid = ? AND name = ?")) {
                ps.setString(1, p.getUniqueId().toString());
                ps.setString(2, name);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return new String[]{
                            rs.getString("server"),
                            rs.getString("world"),
                            String.valueOf(rs.getDouble("x")),
                            String.valueOf(rs.getDouble("y")),
                            String.valueOf(rs.getDouble("z")),
                            String.valueOf(rs.getFloat("yaw")),
                            String.valueOf(rs.getFloat("pitch"))
                    };
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new String[0];
    }

    public void connectToServer(Player p, String server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(server);
        p.sendPluginMessage(reference, "BungeeCord", out.toByteArray());
    }

    public boolean checkForPending(Player p) {
        try (Connection conn = reference.getDatabaseManager().getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM pending_teleports WHERE uuid = ?")) {
                ps.setString(1, p.getUniqueId().toString());
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    executePendingTeleport(p);
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public void executePendingTeleport(Player p) {
        try (Connection conn = reference.getDatabaseManager().getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM pending_teleports WHERE uuid = ?")) {
                ps.setString(1, p.getUniqueId().toString());
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    Location loc = new Location(Bukkit.getWorld(rs.getString("world")), rs.getDouble("x"), rs.getDouble("y"), rs.getDouble("z"));
                    p.teleport(loc);
                    try(PreparedStatement delete = conn.prepareStatement("DELETE FROM pending_teleports WHERE uuid = ?")){
                        delete.setString(1, p.getUniqueId().toString());
                        delete.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String[] getWarp(Player p, String name) {
        try (Connection conn = reference.getDatabaseManager().getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM warps WHERE name = ?")) {
                ps.setString(1, name);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return new String[]{
                            rs.getString("server"),
                            rs.getString("world"),
                            String.valueOf(rs.getDouble("x")),
                            String.valueOf(rs.getDouble("y")),
                            String.valueOf(rs.getDouble("z")),
                            String.valueOf(rs.getFloat("yaw")),
                            String.valueOf(rs.getFloat("pitch"))
                    };
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new String[0];
    }

    public void saveTpaRequest(UUID requesterUUID, String requesterName, String targetName){
        try(Connection conn = reference.getDatabaseManager().getConnection()){
            try(PreparedStatement delete = conn.prepareStatement("DELETE FROM tpa_requests WHERE target_name = ?")){
                delete.setString(1, targetName);
                delete.executeUpdate();
            }
            try(PreparedStatement ps = conn.prepareStatement("INSERT INTO tpa_requests(requester_uuid, requester_name, target_name) VALUES(?,?,?)")){
                ps.setString(1, requesterUUID.toString());
                ps.setString(2, requesterName);
                ps.setString(3, targetName);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String[] getTpaRequest(String targetName){
        try(Connection conn = reference.getDatabaseManager().getConnection()){
            try(PreparedStatement ps = conn.prepareStatement("SELECT * FROM tpa_requests WHERE target_name = ?")){
                ps.setString(1, targetName);
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    return new String[]{
                            rs.getString("requester_uuid"),
                            rs.getString("requester_name")
                    };
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void deleteTpaRequest(String targetName){
        try(Connection conn = reference.getDatabaseManager().getConnection()){
            try(PreparedStatement ps = conn.prepareStatement("DELETE FROM tpa_requests WHERE target_name = ?")){
                ps.setString(1, targetName);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void connectOtherToServer(Player sender, String playerName, String server){
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("ConnectOther");
        out.writeUTF(playerName);
        out.writeUTF(server);
        sender.sendPluginMessage(reference, "BungeeCord", out.toByteArray());
    }

    public void sendMessageToPlayer(Player sender, String targetName, String message){
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Message");
        out.writeUTF(targetName);
        out.writeUTF(message);
        sender.sendPluginMessage(reference, "BungeeCord", out.toByteArray());
    }

    public void updatePlayerStatus(UUID uuid, String name, String server, boolean online){
        try(Connection conn = reference.getDatabaseManager().getConnection()){
            try(PreparedStatement ps = conn.prepareStatement("REPLACE online_players(uuid, name, server, online) VALUES (?, ?, ?, ?)")){
                ps.setString(1, uuid.toString());
                ps.setString(2, name);
                ps.setString(3, server);
                ps.setBoolean(4, online);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getNetworkPlayers(){
        List<String> list = new ArrayList<>();
        try(Connection conn = reference.getDatabaseManager().getConnection()){
            try(PreparedStatement ps = conn.prepareStatement("SELECT * FROM online_players WHERE online = TRUE")){
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    list.add(rs.getString("name"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<String> getWarpNames(){
        List<String> list = new ArrayList<>();
        try(Connection conn = reference.getDatabaseManager().getConnection()){
            try(PreparedStatement ps = conn.prepareStatement("SELECT * FROM warps")){
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    list.add(rs.getString("name"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
