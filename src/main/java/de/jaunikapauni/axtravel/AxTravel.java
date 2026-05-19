package de.jaunikapauni.axtravel;

import de.jaunikapauni.axtravel.command.*;
import de.jaunikapauni.axtravel.listener.PlayerJoinListener;
import de.jaunikapauni.axtravel.manager.DatabaseManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class AxTravel extends JavaPlugin {
    DatabaseManager databaseManager;
    public DatabaseManager getDatabaseManager(){
        return databaseManager;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        databaseManager = new DatabaseManager(this);
        try{
            if(databaseManager.initDatabaseTable1() && databaseManager.initDatabaseTable2() == false){
                getLogger().severe("Error creating homes table!");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        getCommand("home").setExecutor(new HomeCommand(this));
        getCommand("homes").setExecutor(new HomesCommand(this));
        getCommand("sethome").setExecutor(new SetHomeCommand(this));
        getCommand("setspawn").setExecutor(new SetSpawnCommand(this));
        getCommand("setwarp").setExecutor(new SetWarpCommand(this));
        getCommand("spawn").setExecutor(new SpawnCommand(this));
        getCommand("tp").setExecutor(new TeleportCommand(this));
        getCommand("tphere").setExecutor(new TeleportHereCommand(this));
        getCommand("warp").setExecutor(new WarpCommand(this));
        getCommand("warps").setExecutor(new WarpsCommand(this));
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public boolean isSpawnOnJoin(){
        return getConfig().getBoolean("spawnOnJoin", true);
    }
}
