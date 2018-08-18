package mainPkg;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class MyListener implements Listener {
	// For a list of all events, go to https://hub.spigotmc.org/javadocs/spigot/org/bukkit/event/class-use/Event.html
	@EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        event.setJoinMessage("Welcome, " + event.getPlayer().getName() + "!");
        Player player = event.getPlayer();

        if (UCSCPluginMain.config.getBoolean("youAreAwesome")) {
            player.sendMessage("You are awesome!");
        } else {
            player.sendMessage("You are not awesome...");
        }
    }
}
