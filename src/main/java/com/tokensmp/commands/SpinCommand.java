package com.tokensmp.commands;

import com.tokensmp.TokenSMPPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpinCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }
        if (TokenSMPPlugin.getInstance().getConfig().getBoolean("spin-on-join", true) || p.hasPermission("tokensmp.admin")) {
            TokenSMPPlugin.getInstance().getSpinManager().startSpin(p);
        } else {
            p.sendMessage("§cManual spinning is disabled on this server.");
        }
        return true;
    }
}
