package com.tokensmp.commands;

import com.tokensmp.TokenSMPPlugin;
import com.tokensmp.token.TokenType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("tokensmp.admin")) {
            sender.sendMessage("§cYou don't have permission.");
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage("§6Usage: /tokensmp <gui|reload|give|remove>");
            return true;
        }
        switch (args[0].toLowerCase()) {
            case "gui":
                if (sender instanceof Player p) {
                    TokenSMPPlugin.getInstance().getAdminGUI().openMainMenu(p);
                } else {
                    sender.sendMessage("Console cannot open GUI.");
                }
                break;
            case "reload":
                TokenSMPPlugin.getInstance().reloadConfig();
                TokenSMPPlugin.getInstance().saveResource("messages.yml", true);
                sender.sendMessage("§aTokenSMP reloaded.");
                break;
            case "give":
                if (args.length < 3) {
                    sender.sendMessage("Usage: /tokensmp give <player> <tokenName>");
                    return true;
                }
                Player target = sender.getServer().getPlayer(args[1]);
                if (target == null) {
                    sender.sendMessage("§cPlayer not found.");
                    return true;
                }
                try {
                    TokenType token = TokenType.valueOf(args[2].toUpperCase());
                    TokenSMPPlugin.getInstance().getDataManager().addToken(target.getUniqueId(), token);
                    sender.sendMessage("§aGiven " + token.getDisplayName() + " to " + target.getName());
                } catch (IllegalArgumentException e) {
                    sender.sendMessage("§cInvalid token name.");
                }
                break;
            case "remove":
                if (args.length < 3) {
                    sender.sendMessage("Usage: /tokensmp remove <player> <tokenName>");
                    return true;
                }
                Player targetR = sender.getServer().getPlayer(args[1]);
                if (targetR == null) {
                    sender.sendMessage("§cPlayer not found.");
                    return true;
                }
                try {
                    TokenType token = TokenType.valueOf(args[2].toUpperCase());
                    TokenSMPPlugin.getInstance().getDataManager().removeToken(targetR.getUniqueId(), token);
                    sender.sendMessage("§aRemoved " + token.getDisplayName() + " from " + targetR.getName());
                } catch (IllegalArgumentException e) {
                    sender.sendMessage("§cInvalid token name.");
                }
                break;
            default:
                sender.sendMessage("§cUnknown subcommand.");
        }
        return true;
    }
}
