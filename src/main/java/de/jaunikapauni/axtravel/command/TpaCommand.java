package de.jaunikapauni.axtravel.command;

import de.jaunikapauni.axtravel.AxTravel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TpaCommand implements CommandExecutor {
    AxTravel reference;
    public TpaCommand(AxTravel reference){
        this.reference = reference;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Only players can run this command!");
            return true;
        }
        Player p = (Player) sender;
        reference.getPlayerManager().saveTpaRequest(p.getUniqueId(), p.getName(), args[0]);
        Player target = Bukkit.getPlayerExact(args[0]);
        if(target != null){
            target.sendMessage(ChatColor.YELLOW + p.getName() + " wants to teleport to you! Use /tpaccept or /tpdeny");
        } else {
            reference.getPlayerManager().sendMessageToPlayer(p, args[0], ChatColor.YELLOW + p.getName() + " wants to teleport to you! Use /tpaccept or /tpdeny" );
        }
        p.sendMessage(ChatColor.GREEN + "Teleport request sent to " + args[0] + "!");
        return true;
    }
}
