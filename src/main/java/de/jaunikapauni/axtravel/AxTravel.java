package de.jaunikapauni.axtravel;

import de.jaunikapauni.axtravel.command.*;
import de.jaunikapauni.axtravel.listener.PlayerJoinListener;
import de.jaunikapauni.axtravel.listener.PlayerQuitListener;
import de.jaunikapauni.axtravel.manager.DatabaseManager;
import de.jaunikapauni.axtravel.manager.PlayerManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class AxTravel extends JavaPlugin {
    File serverFile;
    FileConfiguration serverFileConfig;
    String server;
    DatabaseManager databaseManager;
    public DatabaseManager getDatabaseManager(){
        return databaseManager;
    }
    PlayerManager playerManager;
    public PlayerManager getPlayerManager(){
        return playerManager;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        createLangFile();
        server = getMessage("server");
        databaseManager = new DatabaseManager(this);
        playerManager = new PlayerManager(this);
        try{
            if(databaseManager.initDatabaseTable1() && databaseManager.initDatabaseTable2() && databaseManager.initDatabaseTable3() && databaseManager.initDatabaseTable4() && databaseManager.initDatabaseTable5() == false){
                getLogger().severe("Error creating tables!");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        getCommand("home").setExecutor(new HomeCommand(this));
        getCommand("home").setTabCompleter(new HomeTabCompleter(this));
        getCommand("homes").setExecutor(new HomesCommand(this));
        getCommand("sethome").setExecutor(new SetHomeCommand(this));
        getCommand("setspawn").setExecutor(new SetSpawnCommand(this));
        getCommand("setwarp").setExecutor(new SetWarpCommand(this));
        getCommand("spawn").setExecutor(new SpawnCommand(this));
        getCommand("tp").setExecutor(new TeleportCommand(this));
        getCommand("tphere").setExecutor(new TeleportHereCommand(this));
        getCommand("warp").setExecutor(new WarpCommand(this));
        getCommand("warp").setTabCompleter(new WarpTabCompleter(this));
        getCommand("warps").setExecutor(new WarpsCommand(this));
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getCommand("tpa").setExecutor(new TpaCommand(this));
        getCommand("tpa").setTabCompleter(new TpaTabCompleter(this));
        getCommand("tpaccept").setExecutor(new TpAcceptCommand(this));
        getCommand("tpdeny").setExecutor(new TpDenyCommand(this));

        getCommand("delhome").setExecutor(new DelHomeCommand(this));
        getCommand("delhome").setTabCompleter(new DelHomeTabCompleter(this));
        getCommand("delwarp").setExecutor(new DelWarpCommand(this));
        getCommand("delwarp").setTabCompleter(new DelWarpTabCompleter(this));
        getLogger().info("");
        getLogger().info("----------------------------------------");
        getLogger().info("Name: " + getName());
        getLogger().info("Version: " + getDescription().getVersion());
        getLogger().info(String.join("Authors: " + ", ", getDescription().getAuthors()));
        getLogger().info("----------------------------------------");
        getLogger().info("");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        databaseManager.close();
    }

    public boolean isSpawnOnJoin(){
        return getConfig().getBoolean("spawnOnJoin", true);
    }

    public void createLangFile(){
        serverFile = new File(getDataFolder(), "server.yml");
        if(!serverFile.exists()){
            saveResource("server.yml", false);
        }
        serverFileConfig = YamlConfiguration.loadConfiguration(serverFile);
    }

    public String getMessage(String path){
        return serverFileConfig.getString(path);
    }
}
