package de.jaunikapauni.axtravel.command;

import de.jaunikapauni.axtravel.AxTravel;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class RTPTabCompleter implements TabCompleter {

    AxTravel reference;
    public RTPTabCompleter(AxTravel reference){
        this.reference = reference;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(args.length != 1){
            return List.of();
        }
        List<String> rtpWorlds = new ArrayList<>();
        String input = args[0].toLowerCase();
        for(String rtpWorld : reference.getConfig().getStringList("rtp-tabcompletions")){
            if(rtpWorld.toLowerCase().startsWith(input)){
                rtpWorlds.add(rtpWorld);
            }
        }
        return rtpWorlds;
    }
}
