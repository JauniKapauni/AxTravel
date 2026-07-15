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

import java.util.Random;

public class RTPCommand implements CommandExecutor {

    AxTravel reference;
    public RTPCommand(AxTravel reference){
        this.reference = reference;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Only players can run this command!");
            return true;
        }
        Player p = (Player) sender;
        World world = Bukkit.getWorld(args[0]);
        reference.getPlayerManager().delayTeleport(p, () -> {
            Random random = new Random();
            int radius = reference.getConfig().getInt("radiusOfRTP");
            Location loc = null;
            for(int i = 0; i < 10 && loc == null; i++){
                int x = random.nextInt(radius * 2 + 1) - radius;
                int z = random.nextInt(radius * 2 + 1) - radius;
                loc = getSafeLocation(world, x, z);
            }
            if(loc == null){
                p.sendMessage(ChatColor.RED + "No safe location found after 10 attempts! Please try again!");
                return;
            }
            p.teleport(loc);
            p.sendMessage(ChatColor.GREEN + "You were teleported to a random location!");
        });
        return true;
    }

    Location getSafeLocation(World world, int x, int z){
        String path = "rtps." + world.getName();
        int minY = reference.getRtpsFileConfig().getInt(path + ".minY");
        int maxY = reference.getRtpsFileConfig().getInt(path + ".maxY");
        if(world.getEnvironment() == World.Environment.NETHER){
            maxY = 120;
        }
        for(int y = maxY - 2; y > minY; y--){
            Location loc = new Location(world, x + 0.5, y, z + 0.5);
            if(loc.getBlock().getType().isAir() && loc.clone().add(0, 1, 0).getBlock().getType().isAir() && loc.clone().subtract(0, 1, 0).getBlock().getType().isSolid()){
                return loc;
            }
        }
        return null;
    }
}
