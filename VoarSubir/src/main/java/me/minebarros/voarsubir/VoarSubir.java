package me.minebarros.voarsubir;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class VoarSubir extends JavaPlugin implements CommandExecutor {

    public String chat(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.getCommand("voar").setExecutor(this);
        }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(this.chat(this.getConfig().getString("erro_console")));
            return false;
        }
        Player p = (Player) sender;
        if (!p.hasPermission("voar.permissao")) {
            p.sendMessage(this.chat(this.getConfig().getString("sem_permissao")));
            return false;
        }

        if (args.length == 0) {
            if (!p.getAllowFlight()) {
                p.setAllowFlight(true);
                p.sendMessage(this.chat(this.getConfig().getString("voar.ativado")));
                return true;
            } else {
                p.setAllowFlight(false);
                p.sendMessage(this.chat(this.getConfig().getString("voar.desativado")));
                return true;
            }
        }

        if (this.getServer().getPlayer(args[0]) != null) {
            Player f = this.getServer().getPlayer(args[0]);
            if (!f.getAllowFlight()) {
                f.setAllowFlight(true);
                f.sendMessage(this.chat(this.getConfig().getString("voar.ativado")));
                return true;
            } else {
                f.setAllowFlight(false);
                f.sendMessage(this.chat(this.getConfig().getString("voar.desativado")));
                return true;
            }
        }
        sender.sendMessage(this.chat(this.getConfig().getString("uso")));
        return false;
    }
}