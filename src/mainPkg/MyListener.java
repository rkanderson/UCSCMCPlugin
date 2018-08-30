package mainPkg;

import org.bukkit.CropState;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.SlimeSplitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.material.Crops;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.JsonPrimitive;

import net.md_5.bungee.api.ChatColor;

public class MyListener implements Listener {
	// For a list of all events, go to https://hub.spigotmc.org/javadocs/spigot/org/bukkit/event/class-use/Event.html
	
	UCSCPluginMain plugin;
	
	public MyListener(UCSCPluginMain p) {
		plugin = p;
	}
	
	@EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        event.setJoinMessage("Welcome, " + event.getPlayer().getName() + "!");
        Player player = event.getPlayer();
        String niceMessage = UCSCPluginMain.config.getString("niceMessage");
        String meanMessage = UCSCPluginMain.config.getString("meanMessage");
        if(Math.random() > 0.5)
        {
        	player.sendMessage(niceMessage);
        }else{
        	player.sendMessage(meanMessage);
        }
        
//        if (UCSCPluginMain.config.getBoolean("youAreAwesome")) {
//            player.sendMessage("You are awesome!");
//        } else {
//            player.sendMessage("You are not awesome...");
//        }  
        
    }
	
	@EventHandler
	public void onPlayerClick(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		int collegeIndex = plugin.playerDataHelper.getInt(p.getName(), "college");
		
		// Rachel Carson makes crops grow if you right click on seeds with a stick in your hand.
		// TODO add limit + cooldown
		if(collegeIndex == 8 && p.getItemInHand().getType() == Material.STICK) {
			EquipmentSlot slot = e.getHand();
			if(slot.equals(EquipmentSlot.HAND)) {
				Block target = p.getTargetBlock(null, 5);
				if(target.getType() == Material.CROPS) {
					p.sendMessage(ChatColor.AQUA+"Let it Grow!");
					BlockState bs = target.getState();
					bs.setData(new Crops(CropState.RIPE));
					bs.update();
				}
			}
		} else if(collegeIndex == 6) {
			// Kresge: Right click with blaze powder in hand = dankMode
			EquipmentSlot slot = e.getHand();
			if(slot.equals(EquipmentSlot.HAND)) {
				if(e.getItem().getType() == Material.BLAZE_POWDER && !plugin.playerDataHelper.getBoolean(p.getName(), "is_dank_mode")) {
					plugin.playerDataHelper.setProperty(p.getName(), "is_dank_mode", new JsonPrimitive(true));
					plugin.playerDataHelper.setProperty(p.getName(), "dank_mode_timestamp", new JsonPrimitive(System.currentTimeMillis()));
					p.sendMessage(""+ChatColor.DARK_GREEN+ChatColor.BOLD+ChatColor.ITALIC+"DANK MODE ACTIVATED");
					e.getItem().setAmount(e.getItem().getAmount()-1);
				}
			}
		}
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event)
	{
		Entity e = event.getEntity();
		if(e.getLastDamageCause() instanceof EntityDamageByEntityEvent)
		{
			EntityDamageByEntityEvent nEvent = (EntityDamageByEntityEvent) e.getLastDamageCause();
			if(nEvent.getDamager() instanceof Player)
			{
				Player p = (Player)nEvent.getDamager();
				if(plugin.playerDataHelper.getInt(p.getName(), "college") == 9 && Math.random()>0.6 &&
						(p.getItemInHand().getType() == Material.STONE_AXE ||
						p.getItemInHand().getType() == Material.IRON_AXE ||
						p.getItemInHand().getType() == Material.DIAMOND_AXE)) {
					// OAKES
					p.sendMessage(ChatColor.BOLD+"OAKES!");
					e.getLocation().getWorld().strikeLightning(e.getLocation());
					
					// Move the drone down to temporarily make a grass block if needed.
					// After the tree is grown, the block is replaced to be what it originally was.
					Drone d = new Drone(e.getLocation());
					d.setBlockAtLoc(Material.AIR);
					d.changeY(-1);
					World world = e.getWorld();
					Block base = world.getBlockAt(d.loc);
					Material baseType = null;
					if(base.getType() != Material.GRASS) {
						baseType = base.getType();
						base.setType(Material.GRASS);
					}
					world.generateTree(e.getLocation(), TreeType.TREE);
					if(baseType != null) d.setBlockAtLoc(baseType);
					
					// Light all the nearby mobs on fire.
					for(Entity ent: world.getNearbyEntities(e.getLocation(), 7, 5, 5)) {
						if(ent.getType() != EntityType.PLAYER && 
								ent.getType() != EntityType.WOLF && 
								ent.getType() != EntityType.OCELOT) {
							ent.setFireTicks(200);
						}
					}
				}
            }
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
