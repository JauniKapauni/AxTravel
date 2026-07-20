package de.jaunikapauni.axtravel.command;

import de.jaunikapauni.axtravel.AxTravel;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DelHomeCommand implements CommandExecutor {
    AxTravel reference;
    public DelHomeCommand(AxTravel reference){
        this.reference = reference;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Only players can run this command!");
            return true;
        }
        Player p = (Player) sender;
        if(!p.hasPermission("axtravel.delhome")){
            p.sendMessage("You don't have the permission! [axtravel.delhome]");
            return true;
        }
        Bukkit.getScheduler().runTaskAsynchronously(reference, () -> {
            reference.getPlayerManager().delHome(p, args[0]);
            Bukkit.getScheduler().runTask(reference, () -> {
                p.sendMessage("Your home " + args[0] + " was successfully deleted!");
            });
        });
        return true;
    }
}
