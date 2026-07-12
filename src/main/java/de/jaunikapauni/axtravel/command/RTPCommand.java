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
        Random random = new Random();
        int radius = reference.getConfig().getInt("radiusOfRTP");
        int x = random.nextInt(radius * 2 + 1) - radius;
        int z = random.nextInt(radius * 2 + 1) - radius;
        World world = Bukkit.getWorld(args[0]);
        int y = world.getHighestBlockYAt(x, z);
        Location loc = new Location(world, x + 0.5, y, z + 0.5);
        p.teleport(loc);
        p.sendMessage(ChatColor.GREEN + "You were teleported to a random location!");
        return true;
    }
}
