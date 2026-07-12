package de.jaunikapauni.axtravel.listener;

import de.jaunikapauni.axtravel.AxTravel;
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
        if(reference.isSpawnOnJoin()){
            Location spawn = reference.getSpawnLocation();
            p.teleport(spawn);
            p.sendMessage(ChatColor.GREEN + "You were teleported to the spawn!");
            reference.getPlayerManager().checkForPending(p);
        }
        String[] tpaRequest = reference.getPlayerManager().getTpaRequest(p.getName());
        if(tpaRequest != null){
            p.sendMessage(ChatColor.YELLOW + tpaRequest[1] + " wants to teleport to you! Use /tpaccept or /tpdeny");
        }
        reference.getPlayerManager().updatePlayerStatus(p.getUniqueId(), p.getName(), reference.getMessage("server"), true);
    }
}
