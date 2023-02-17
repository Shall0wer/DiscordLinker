package fr.shall0wer.discordlinker;

import fr.shall0wer.discordlinker.command.WebhookCommand;
import fr.shall0wer.discordlinker.discord.DiscordWebhook;
import fr.shall0wer.discordlinker.discord.WebhookSender;
import fr.shall0wer.discordlinker.listeners.MinecraftListeners;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public final class DiscordLinker extends JavaPlugin {

    private static DiscordLinker linker = null;
    private FileConfiguration config;

    private Map<String, Boolean> options = new HashMap<>();

    @Override
    public void onLoad() {
        if(linker == null){
            linker = this;
        }
    }

    @Override
    public void onEnable() {
        // Configuration part
        saveDefaultConfig();
        config = getConfig();

        // Register part
        registerCommands();
        registerListeners();
        registerOptions();

        // Send start webhook
        if(config.getBoolean("server-start")){
            try {
                new WebhookSender(config.getString("webhook-url"), config.getString("username"), config.getString("avatar-url"), ":purple_circle: Server **" + getServer().getServerName() + "** has started!");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void onDisable() {
        try {
            new WebhookSender(config.getString("webhook-url"), config.getString("username"), config.getString("avatar-url"), ":purple_circle: Server **" + getServer().getServerName() + "** has stopped!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if(linker != null){
            linker = null;
        }
    }

    public static DiscordLinker getLinker(){
        return linker;
    }

    private void registerCommands(){
        getCommand("webhook").setExecutor(new WebhookCommand());
    }

    private void registerListeners(){
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new MinecraftListeners(), this);
    }

    public FileConfiguration getFileConfig() {
        return config;
    }

    public void setFileConfig(FileConfiguration config) {
        this.config = config;
    }

    public void registerOptions(){
        options.clear();
        options.put("server-start", config.getBoolean("server-start"));
        options.put("server-shutdown", config.getBoolean("server-shutdown"));
        options.put("server-chat", config.getBoolean("server-chat"));
        options.put("player-join-server", config.getBoolean("player-join-server"));
        options.put("player-quit-server", config.getBoolean("player-quit-server"));
    }

    public boolean isOptionActivate(String option){
        return options.get(option);
    }
}
