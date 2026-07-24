package de.jaunikapauni.axtravel.listener;

import de.jaunikapauni.axtravel.AxTravel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    AxTravel reference;
    public PlayerJoinListener(AxTravel reference){
        this.reference = reference;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        Bukkit.getScheduler().runTaskAsynchronously(reference, () -> {
            boolean hasPending = reference.getPlayerManager().checkForPending(p);
            if(!hasPending && reference.isSpawnOnJoin()){
                Bukkit.getScheduler().runTask(reference, () -> {
                    Location spawn = reference.getSpawnLocation();
                    if(spawn != null && spawn.getWorld() != null){
                        Bukkit.getScheduler().runTask(reference, () -> {
                            p.teleport(spawn);
                            p.sendMessage(ChatColor.GREEN + "You were teleported to the spawn!");
                        });
                    }
                });
            }
            String[] tpaRequest = reference.getPlayerManager().getTpaRequest(p.getName());
            if(tpaRequest != null){
                Bukkit.getScheduler().runTask(reference, () -> {
                    p.sendMessage(ChatColor.YELLOW + tpaRequest[1] + " wants to teleport to you! Use /tpaccept or /tpdeny");
                });
            }
            reference.getPlayerManager().updatePlayerStatus(p.getUniqueId(), p.getName(), reference.getMessage("server"), true);
        });
    }
}
