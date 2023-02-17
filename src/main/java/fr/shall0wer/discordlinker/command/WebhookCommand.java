package fr.shall0wer.discordlinker.command;

import fr.shall0wer.discordlinker.DiscordLinker;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;


public class WebhookCommand implements CommandExecutor {

    private FileConfiguration config = DiscordLinker.getLinker().getFileConfig();

    /**
     *
     * @param sender Source of the command, in this case only a player can execute the command
     * @param command Command which was executed
     * @param label Alias of the command which was used
     * @param args Passed command arguments
     * @return
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            return false;
        }

        Player player = (Player) sender;
        if(args.length != 1 || args[0].equalsIgnoreCase("help")){
            player.sendMessage("§6Webhook §m-----------------------------------------+" +
                    "\n§r " +
                    "\n§f» §e/§fwebhook §binfo§f: Gives info about the Webhook's config." +
                    "\n§f» §e/§fwebhook §breload§f: Reload configuration." +
                    "\n§r " +
                    "\n§6§m-------------------------------------------------+");
        } else if(args[0].equalsIgnoreCase("info")){
            player.sendMessage("§6Webhook §m-----------------------------------------+" +
                    "\n§r " +
                    "\n§f» §eUsername: " + (config.getString("username") != "" ? "§b" + config.getString("username") : "§cDefault username") +
                    "\n§f» §eAvatar: §b" + (config.getString("avatar-url") != "" ? "§b" + config.getString("avatar-url") : "§cDefault avatar") +
                    "\n§r " +
                    "\n§6§m-------------------------------------------------+");
        } else if(args[0].equalsIgnoreCase("reload")){
            reloadConfig();
            player.sendMessage("§6Webhook§f: Plugin reloaded.");
        }
        return false;
    }

    private void reloadConfig(){
        DiscordLinker.getLinker().reloadConfig();
        DiscordLinker.getLinker().setFileConfig(DiscordLinker.getLinker().getConfig());
    }
}
