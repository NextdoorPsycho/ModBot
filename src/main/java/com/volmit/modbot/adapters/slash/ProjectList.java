package com.volmit.modbot.adapters.slash;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import com.volmit.modbot.ModBot;
import com.volmit.modbot.adapters.slash.json.Root;
import com.volmit.modbot.util.BotEmbed;

import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProjectList extends SlashCommand {

    public ProjectList() {
        this.name = "projectlist"; // This has to be lowercase
        this.help = "Lists all projects";
        this.category = new Category("Development"); // This is where the command will show up in the help menu
    }

    @Override
    protected void execute(SlashCommandEvent event) {
        List<JsonObject> projects = getFiles() != null ? getFiles() : null;
        if (projects == null) {
            event.reply("No projects found!").setEphemeral(true).queue();
            return;
        }

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Root root = gsonBuilder.create().fromJson(projects.get(0), Root.class);


    }


    private static List<JsonObject> getFiles() {
        try {
            File f = new File("Data/Projects");
            FilenameFilter filter = (file, name) -> name.endsWith(".json");
            File[] files = f.listFiles(filter);
            List<JsonObject> jsonProjects = new ArrayList<>();

            // Get the names of the files by using the .getName() method
            if (files != null)
                for (File file : files) {
                    jsonProjects.add(convertFileToJSON(file.getPath()));
                    ModBot.info("Loading Project: " + file.getName());
                }
            return jsonProjects;
        } catch (Exception e) {
            File f = new File("Projects");
            ModBot.error("Failed to get files");
            ModBot.error(f.getPath());
        }
        return null;
    }

    private static JsonObject convertFileToJSON(String fileName) {
        // Read from File to String
        JsonObject jsonObject = new JsonObject();

        try {
            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(new FileReader(fileName));
            jsonObject = jsonElement.getAsJsonObject();
        } catch (IOException ignored) {

        }


        return jsonObject;
    }
}
