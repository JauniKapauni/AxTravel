package de.jaunikapauni.axtravel.command;

import de.jaunikapauni.axtravel.AxTravel;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TpaTabCompleter implements TabCompleter {
    AxTravel reference;
    public TpaTabCompleter(AxTravel reference){
        this.reference = reference;
    }
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        List<String> list = new ArrayList<>();
        if(args.length == 1){
            String input = args[0].toLowerCase();
            for(String name : reference.getPlayerManager().getNetworkPlayers()){
                if(name.toLowerCase().startsWith(input)){
                    list.add(name);
                }
            }
        }
        return list;
    }
}
