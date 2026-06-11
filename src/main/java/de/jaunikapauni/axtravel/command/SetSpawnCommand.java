package de.jaunikapauni.axtravel.command;

import de.jaunikapauni.axtravel.AxTravel;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetSpawnCommand implements CommandExecutor {
    AxTravel reference;
    public SetSpawnCommand(AxTravel reference){
        this.reference = reference;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Only players can run this command!");
            return true;
        }
        Player p = (Player) sender;
        if(!p.hasPermission("axtravel.setspawn")){
            p.sendMessage("You don't have the permission! [axtravel.setspawn]");
            return true;
        }
        Location loc = p.getLocation();
        p.getWorld().setSpawnLocation(loc);
        p.sendMessage(ChatColor.GREEN + "The spawb was changed to " + loc);
        return true;
    }
}
