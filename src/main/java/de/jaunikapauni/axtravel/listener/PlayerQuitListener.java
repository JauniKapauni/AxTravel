package de.jaunikapauni.axtravel.listener;

import de.jaunikapauni.axtravel.AxTravel;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    AxTravel reference;
    public PlayerQuitListener(AxTravel reference){
        this.reference = reference;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player p = e.getPlayer();
        reference.getPlayerManager().updatePlayerStatus(p.getUniqueId(), p.getName(), "", false);
    }
}
