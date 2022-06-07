package configurationfile.welcomemessage;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class WelcomeMessage extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        setupConfig();
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("reloadpm").setExecutor(new ReloadCommand(this));

    }

    public void setupConfig(){
        saveDefaultConfig();
        //saveConfig();
    }

    @EventHandler
    public void OnPlayerLogin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        String message = getConfig().getString("welcome-message");
        if (message != null) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
        }
    }
    @Override
    public void reloadConfig() {
        // Let JavaPlugin do its stuff before
        super.reloadConfig();
        // Save default config into plugins/<your-plugin>/config.yml if not exists
        saveDefaultConfig();
        // Get config from config file
        //config = getConfig();
        // Put default values into it (from your jar's config.yml file)
        getConfig().options().copyDefaults(true);
        // Add missing / new parameters into plugins/<your-plugin>/config.yml
        saveConfig();
    }

}

class ReloadCommand implements CommandExecutor{

    WelcomeMessage welcomeMessage;

    public ReloadCommand(WelcomeMessage welcomeMessage){
        this.welcomeMessage = welcomeMessage;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p){
            //welcomeMessage.getConfig().options().copyDefaults(true);
            //welcomeMessage.saveConfig();
            welcomeMessage.reloadConfig();
            p.sendMessage("Configuración recargada!");
        }
        return false;
    }
}