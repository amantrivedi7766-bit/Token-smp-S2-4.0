package com.tokensmp.commands;

import com.tokensmp.TokenSMPPlugin;
import com.tokensmp.gui.PlayerTokensGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TokenCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }
        new PlayerTokensGUI().open(p);
        return true;
    }
}
