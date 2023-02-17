package fr.shall0wer.discordlinker.discord;

import fr.shall0wer.discordlinker.DiscordLinker;

import java.io.IOException;

public class WebhookSender {

    private final DiscordLinker linker = DiscordLinker.getLinker();
    private DiscordWebhook webhook;


    /**
     *
     * @param url of the webhook in the config.yml
     * @param username will be display
     * @param avatar will be use
     * @param message will be send
     * @throws IOException
     */
    public WebhookSender(String url, String username, String avatar, String message) throws IOException {
        webhook = new DiscordWebhook(url);
        webhook.setContent(message);
        webhook.setUsername(username);
        webhook.setAvatarUrl(avatar);
        webhook.execute();
    }
}
