package de.jaunikapauni.axtravel;

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
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
