package mainPkg;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.SlimeSplitEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class MyListener implements Listener {
	// For a list of all events, go to https://hub.spigotmc.org/javadocs/spigot/org/bukkit/event/class-use/Event.html
	
	JavaPlugin plugin;
	
	public MyListener(JavaPlugin p) {
		plugin = p;
	}
	
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
	
//	@EventHandler
//	public void onSlimeSplit(SlimeSplitEvent event) {
//		System.out.println("Slime split event");
//		Slime slime = event.getEntity();
//		slime.getLocation().getWorld().spawnEntity(slime.getLocation(), EntityType.COW);
//	}
//	
//	@EventHandler
//	public void onBlockBreak(BlockBreakEvent e) {
//		System.out.println("Block broken");
//	}
//	
//	@EventHandler
//	public void onEntityDeath(EntityDeathEvent e) {
//		if(e.getEntityType() == EntityType.ZOMBIE) {
//			e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.ZOMBIE);
//			e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.ZOMBIE);
//		}
//		
//	}
}
