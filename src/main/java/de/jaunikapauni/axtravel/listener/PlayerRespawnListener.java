package de.jaunikapauni.axtravel.listener;

import de.jaunikapauni.axtravel.AxTravel;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnListener implements Listener {

    AxTravel reference;
    public PlayerRespawnListener(AxTravel reference){
        this.reference = reference;
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e){
        Location spawn = reference.getSpawnLocation();
        e.setRespawnLocation(spawn);
    }
}
