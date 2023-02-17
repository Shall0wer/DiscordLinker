package fr.shall0wer.discordlinker.listeners;

import fr.shall0wer.discordlinker.DiscordLinker;
import fr.shall0wer.discordlinker.discord.WebhookSender;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.IOException;

public class MinecraftListeners implements Listener {

    private final DiscordLinker linker = DiscordLinker.getLinker();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(linker, () -> {
            if(linker.isOptionActivate("player-join-server")){
                try {
                    new WebhookSender(linker.getFileConfig().getString("webhook-url"), linker.getFileConfig().getString("username"), linker.getFileConfig().getString("avatar-url"), "(" + linker.getServer().getServerName() + ") :green_circle: " + event.getPlayer().getName() + " joined the server!");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @EventHandler
    public void onQuitPlayer(PlayerQuitEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(linker, () -> {
            if(linker.isOptionActivate("player-quit-server")){
                try {
                    new WebhookSender(linker.getFileConfig().getString("webhook-url"), linker.getFileConfig().getString("username"), linker.getFileConfig().getString("avatar-url"), "(" + linker.getServer().getServerName() + ") :red_circle: " + event.getPlayer().getName() + " left the server!");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(linker, () -> {
            if(linker.isOptionActivate("server-chat")){
                try {
                    new WebhookSender(linker.getFileConfig().getString("webhook-url"), linker.getFileConfig().getString("username"), linker.getFileConfig().getString("avatar-url"), "(" + linker.getServer().getServerName() + ") :yellow_circle: " + event.getPlayer().getName() + " said : " + event.getMessage());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
