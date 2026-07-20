package de.jaunikapauni.axtravel.command;

import de.jaunikapauni.axtravel.AxTravel;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SpawnCommand implements CommandExecutor {
    AxTravel reference;
    public SpawnCommand(AxTravel reference){
        this.reference = reference;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can run this command!");
            return true;
        }
        Player p = (Player) sender;
        if(!p.hasPermission("axtravel.spawn")){
            p.sendMessage("You don't have the permission! [axtravel.spawn]");
            return true;
        }
        reference.getPlayerManager().delayTeleport(p, () -> {
            Location spawn = reference.getSpawnLocation();
            if(spawn == null || spawn.getWorld() == null){
                return;
            }
            p.teleport(spawn);
            p.sendMessage(ChatColor.GREEN + "You were teleported to the spawn!");
        });
        return true;
    }
}
