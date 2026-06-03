package de.jaunikapauni.axtravel.command;

import de.jaunikapauni.axtravel.AxTravel;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class HomeTabCompleter implements TabCompleter {
    AxTravel reference;
    public HomeTabCompleter(AxTravel reference){
        this.reference = reference;
    }
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        List<String> list = new ArrayList<>();
        if(!(sender instanceof Player)){
            return null;
        }
        Player p = (Player) sender;
        if(args.length == 1){
            String input = args[0].toLowerCase();
            for(String homeName : reference.getPlayerManager().getHomeNames(p)){
                if(homeName.toLowerCase().startsWith(input)){
                    list.add(homeName);
                }
            }
        }
        return list;
    }
}
