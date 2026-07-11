package de.jaunikapauni.axtravel.command;

import de.jaunikapauni.axtravel.AxTravel;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TpPosCommand implements CommandExecutor {

    AxTravel reference;
    public TpPosCommand(AxTravel reference){
        this.reference = reference;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Only players can run this command!");
            return true;
        }
        Player p = (Player) sender;
        if(!p.hasPermission("axtravel.tppos")){
            p.sendMessage("You don't have the permission! [axtravel.tppos]");
            return true;
        }
        if(args.length < 3){
            return false;
        }
        Location loc = new Location(p.getWorld(), Double.valueOf(args[0]), Double.valueOf(args[1]), Double.valueOf(args[2]));
        p.teleport(loc);
        p.sendMessage("You got teleported!");
        return true;
    }
}
