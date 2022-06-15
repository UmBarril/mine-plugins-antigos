package me.minebarros.barrilplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements CommandExecutor, Listener {

    public boolean naoAndar = false;

    public String chat(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        for (String cmd : this.getDescription().getCommands().keySet()){
            this.getCommand(cmd).setExecutor(this);
        }
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("parado")){
            ConsoleCommandSender consoleSender = Bukkit.getConsoleSender();
            if (!naoAndar){
                naoAndar = true;
                sender.sendMessage(chat("&6Parou tudo!"));
                Bukkit.dispatchCommand(consoleSender, "gamerule doDaylightCycle false");
                Bukkit.dispatchCommand(consoleSender,"execute as @e@e[type=!player] run data merge entity @s {NoAI:1}");
            } else {
                naoAndar = false;
                sender.sendMessage(chat("&6Voltou tudo!"));
                Bukkit.dispatchCommand(consoleSender,"gamerule doDaylightCycle true");
                Bukkit.dispatchCommand(consoleSender,"execute as @e@e[type=!player] run data merge entity @s {NoAI:0}");
            }
            return true;
        }
        return false;
    }
    @EventHandler
    public void onPlayerDropEvent(final PlayerDropItemEvent event) {
        if (naoAndar) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void entitySpawnEvent(final EntitySpawnEvent event) {
        if (naoAndar) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onPlayer(final PlayerInteractEvent event) {
        if (event.getPlayer().getGameMode() == GameMode.SURVIVAL){
            if (naoAndar) {
                event.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onPlayerMoveEvent(final PlayerMoveEvent event) {
        if (event.getPlayer().getGameMode() == GameMode.SURVIVAL){
            if (naoAndar) {
                event.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onFoodLevelChange(final FoodLevelChangeEvent event) {
        if (naoAndar) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onWeatherChange(final WeatherChangeEvent event) {
        if (naoAndar) {
            event.setCancelled(true);
        }
    }
}