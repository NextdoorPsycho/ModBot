package com.volmit.modbot.adapters.prefix;

import com.volmit.modbot.util.Core;
import com.volmit.modbot.ModBot;
import com.volmit.modbot.adapters.VolmitCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;


public class Shutdown extends VolmitCommand {

    // Constructor
    public Shutdown() {
        super(
                "stop",
                new String[]{"stop", "kill", "s"},
                new String[]{Core.get().adminControllerRole},
                "Stops the Bot boi",
                false,
                null
        );
    }

    // Handle
    @Override
    public void handle(List<String> args, MessageReceivedEvent e) {
        ModBot.warn("Terminating the Bot");
        String oidcheck = e.getMessage().getAuthor().getId();
        if (oidcheck.equals(Core.get().botOwnerID)) {
            ModBot.warn("KILLING BOT");
            ModBot.shutdown();
        } else {
            e.getMessage().reply("uR noT my DAddY!").queue();
        }
    }
}
