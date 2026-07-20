package de.jaunikapauni.axtravel.command;

import de.jaunikapauni.axtravel.AxTravel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class TpDenyCommand implements CommandExecutor {
    AxTravel reference;
    public TpDenyCommand(AxTravel reference){
        this.reference = reference;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Only players can run this command!");
            return true;
        }
        Player p = (Player) sender;
        if(!p.hasPermission("axtravel.tpdeny")){
            p.sendMessage("You don't have the permission! [axtravel.tpdeny]");
            return true;
        }
        String[] request = reference.getPlayerManager().getTpaRequest(p.getName());
        if(request == null){
            p.sendMessage(ChatColor.RED + "No pending teleport request!");
            return true;
        }
        UUID requesterUUID = UUID.fromString(request[0]);
        String requesterName = request[1];
        Bukkit.getScheduler().runTaskAsynchronously(reference, () -> {
            reference.getPlayerManager().deleteTpaRequest(p.getName());
            Bukkit.getScheduler().runTask(reference, () -> {
                Player requester = Bukkit.getPlayer(requesterUUID);
                if(requester != null){
                    requester.sendMessage(ChatColor.RED + p.getName() + " denied your teleport request!");
                }
                p.sendMessage(ChatColor.GREEN + "Teleport request from " + requesterName + " denied!");
            });
        });
        return true;
    }
}
