package com.volmit.modbot.adapters.slash.project;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import com.volmit.modbot.ModBot;
import com.volmit.modbot.adapters.slash.project.json.Root;
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
        projects.forEach(project -> {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setPrettyPrinting();
            Root root = gsonBuilder.create().fromJson(project, Root.class);
            BotEmbed embed = new BotEmbed("Project: " + root.getName());
            embed.setThumbnail(ModBot.getJDA().getSelfUser().getAvatarUrl());
            embed.setTitle("Project: " + root.getName());
            embed.setDescription("*" + root.getDescription() + "*");

            if (root.getAuthors() != null) {
                embed.addField("Authors", root.getAuthors(), false);
            }

            if (root.getVersion() != null) {
                embed.addField("Version", "`" + root.getVersion() + "`", true);
            }

            if (root.getStatus() != null) {
                embed.addField("Status", "`" + root.getStatus() + "`", true);
            }

            if (root.getSettings() != null) {
                embed.addField("Settings", root.getSettings().getColorcode() + " " + root.getSettings().getDownloadable(), true);
            }

            if (root.getCategories() != null) {
                root.getCategories().forEach(category -> {
                    StringBuilder stringBuilder = new StringBuilder();
                    category.getEntries().forEach(entry -> {
                        stringBuilder.append("\n\u250C`Name:` **").append(entry.getName()).append("**\n");
                        if (entry.getDescription() != null) {
                            stringBuilder.append("\u251C`Description`: *").append(entry.getDescription()).append("*\n");
                        }
                        if (entry.getPriority() != null) {
                            stringBuilder.append("\u251C`Priority:` *").append(entry.getPriority()).append("*\n");
                        }
                        if (entry.getStatus() != null) {
                            stringBuilder.append("\u2514`Status:` *__").append(entry.getStatus()).append("__*\n");
                        } else {
                            stringBuilder.append("\u2514");
                        }
                    });
                    embed.addField("\u2E3B[Category: __" + category.getName() + "__]\u2E3B",
                            "*" + category.getDescription() + "*\n" + stringBuilder, false);

                });

            }
            event.replyEmbeds(embed.build()).setEphemeral(true).queue();
        });

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
