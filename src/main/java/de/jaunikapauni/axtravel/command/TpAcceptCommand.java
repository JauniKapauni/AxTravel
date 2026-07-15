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

public class TpAcceptCommand implements CommandExecutor {
    AxTravel reference;
    public TpAcceptCommand(AxTravel reference){
        this.reference = reference;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Only players can run this command!");
            return true;
        }
        Player p = (Player) sender;
        if(!p.hasPermission("axtravel.tpaccept")){
            p.sendMessage("You don't have the permission! [axtravel.tpaccept]");
            return true;
        }
        String[] request = reference.getPlayerManager().getTpaRequest(p.getName());
        if(request == null){
            p.sendMessage(ChatColor.RED + "No pending teleport requests!");
            return true;
        }
        reference.getPlayerManager().delayTeleport(p, () -> {
            UUID requesterUUID = UUID.fromString(request[0]);
            String requesterName = request[1];
            reference.getPlayerManager().deleteTpaRequest(p.getName());
            Player requester = Bukkit.getPlayer(requesterUUID);
            if(requester != null){
                requester.teleport(p.getLocation());
                requester.sendMessage(ChatColor.GREEN + p.getName() + " accepted your teleport request!");
                p.sendMessage(ChatColor.GREEN + requesterName + " was teleported to you!");
            } else {
                reference.getPlayerManager().savePendingTeleport(requesterUUID, reference.getMessage("server"), p.getWorld().getName(), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), p.getLocation().getYaw(), p.getLocation().getPitch());
                reference.getPlayerManager().connectOtherToServer(p, requesterName, reference.getMessage("server"));
                p.sendMessage(ChatColor.GREEN + "Teleport accepted! " + requesterName + " is being connected to this server!");
            }
        });
        return true;
    }
}
