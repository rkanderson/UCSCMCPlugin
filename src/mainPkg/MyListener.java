package mainPkg;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.CropState;
import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.SlimeSplitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.material.Crops;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BlockIterator;

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
		System.out.println("Marker 0");
		Block target = p.getTargetBlock(null, 5);
		System.out.println("Marker 0.5");

		// Rachel Carson makes crops grow if you right click on seeds with a stick in your hand.
		// TODO add limit + cooldown
		if(collegeIndex == 8 && p.getItemInHand().getType() == Material.STICK) {
			EquipmentSlot slot = e.getHand();
			if(slot.equals(EquipmentSlot.HAND)) {
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
			if(slot.equals(EquipmentSlot.HAND) && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
				if(e.getItem().getType() == Material.BLAZE_POWDER && !plugin.playerDataHelper.getBoolean(p.getName(), "is_dank_mode")) {
					plugin.playerDataHelper.setProperty(p.getName(), "is_dank_mode", new JsonPrimitive(true));
					plugin.playerDataHelper.setProperty(p.getName(), "dank_mode_timestamp", new JsonPrimitive(System.currentTimeMillis()));
					p.sendMessage(""+ChatColor.DARK_GREEN+ChatColor.BOLD+ChatColor.ITALIC+"DANK MODE ACTIVATED");
					e.getItem().setAmount(e.getItem().getAmount()-1);
				}
			}
		} else if(collegeIndex == 7) {
			//Porter: Right click on a block of wool with dye can change the color

			// *We only give a shait about the main hand
			EquipmentSlot slot = e.getHand();
			if(!slot.equals(EquipmentSlot.HAND)) return;
			
			if(e.getAction() == Action.RIGHT_CLICK_BLOCK && 
					e.getItem().getType() == Material.INK_SACK &&
					target.getType() == Material.WOOL) {
				// Note that ink sack can be any kind of dye
				byte dyeData = e.getItem().getData().getData();
				byte wd = (byte)(15 - dyeData); //Target wool data
				target.setData(wd);
			} else if((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && 
					e.getItem().getType() == Material.INK_SACK) {
				// If clicking somewhere else with the dye, it applies a buff on the player
				byte dyeData = e.getItem().getData().getData();
				if(!(dyeData == 1 || dyeData == 2 || dyeData == 4 || dyeData == 11)) return; // Do nothing if the dyes aren't magical
				Player targetPlayer; // The porter player might be targeting another player to apply the buff.
									// If there is no player targeted, the target player becomes the porter player.
				Entity playerViewing = getPlayerTargetEntity(p, 5);

				if(playerViewing != null && playerViewing instanceof Player) targetPlayer = (Player)playerViewing;
				else targetPlayer = p;

				
				// Do nothing if the target already has a buff on.
				System.out.println("before: "+plugin.playerDataHelper.getBoolean(targetPlayer.getName(), "porter-buff-on"));
				if(plugin.playerDataHelper.getBoolean(targetPlayer.getName(), "porter-buff-on")) {
					p.sendMessage(ChatColor.RED+"Wait for the previous buff to end before applying a new one.");
					return;
				}
				
				//Craft a special potion effect based on the dye data. Also decide on color for particle effect.
				PotionEffect potionEffect;
				Effect pEffect; //Particles
				int buffDuration = 200; //in ticks
				switch(dyeData) {
				case 1: //Red
					potionEffect = new PotionEffect(PotionEffectType.HEAL, 1, 1);
					p.sendMessage(""+ChatColor.BOLD+ChatColor.RED+"*Fast Heal*");
					pEffect = Effect.HEART;
					break;
				case 2: //Green
					potionEffect = new PotionEffect(PotionEffectType.ABSORPTION, buffDuration, 2);
					p.sendMessage(""+ChatColor.BOLD+ChatColor.GREEN+"*ABSORPTION*");
					pEffect = Effect.VILLAGER_PLANT_GROW;
					break;
				case 4: // Blue
					potionEffect = new PotionEffect(PotionEffectType.JUMP, buffDuration, 6);
					p.sendMessage(""+ChatColor.BOLD+ChatColor.BLUE+"*JUMP*");
					pEffect = Effect.WATERDRIP;
					break;
				case 11: // Yellow
					potionEffect = new PotionEffect(PotionEffectType.SPEED, buffDuration, 3);
					p.sendMessage(""+ChatColor.BOLD+ChatColor.YELLOW+"*SPEED*");
					pEffect = Effect.SPELL;
					break;
				default: // <--This shouldn't trigger.
					potionEffect = new PotionEffect(PotionEffectType.GLOWING, 2, 0);
					pEffect = Effect.BAT_TAKEOFF; //BATMAN
				}
				
				
				//Apply the potion effect
				targetPlayer.addPotionEffect(potionEffect);
				for(int i=0;i<10;i++)targetPlayer.playEffect(targetPlayer.getLocation(), pEffect, 0); //Erm this just sorta kinda works
				// Must set porter-buff-on to true to prevent simultaneous buffs on any given plater
				plugin.playerDataHelper.setProperty(targetPlayer.getName(), "porter-buff-on", new JsonPrimitive(true));
				System.out.println("porter buff on set to true");
				// Another thread should set this property back to false after buffDuration (converted to millisenconds)
				System.out.println("Spawning new thread.");
				new Thread(new SetPlayerPropertyAfterDuration(targetPlayer, "porter-buff-on", 
						new JsonPrimitive(false), buffDuration / 20 * 1000)).start();
				
				// Finally subtract one from the stack
				e.getItem().setAmount(e.getItem().getAmount()-1);
				System.out.println("New Thread Spawned");
				
			}
		}
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent ev) {
		Entity en = ev.getEntity();
		if(en instanceof Player) {
			Player p = (Player)en;
			int collegeIndex = plugin.playerDataHelper.getInt(p.getName(), "college");
			if(collegeIndex == 3 && ev.getCause() == DamageCause.POISON) {
				// Merill, nullify poison effects
				ev.setCancelled(true);
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
				if(plugin.playerDataHelper.getInt(p.getName(), "college") == 9 && Math.random()>0.9 &&
						(p.getItemInHand().getType() == Material.STONE_AXE ||
						p.getItemInHand().getType() == Material.WOOD_AXE ||
						p.getItemInHand().getType() == Material.GOLD_AXE ||
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
	private class SetPlayerPropertyAfterDuration implements Runnable {
		private long durationMillis;
		private Player player;
		private String propName;
		private JsonPrimitive value;
		public SetPlayerPropertyAfterDuration(Player p, String propName, JsonPrimitive val, long dur) {
			durationMillis = dur;
			player = p;
			this.propName = propName;
			value = val;
		}
		@Override
		public void run() {
			try {
				Thread.sleep(durationMillis);
				System.out.println("New thread is waiting");
			} catch (InterruptedException e) {
				System.out.println("MyListener: The Thread couldn't sleep!!!");
			}
			plugin.playerDataHelper.setProperty(player.getName(), propName, value);
			System.out.println("prop "+propName+" set to "+value+" for player "+player.getName());
		}
	}
	
	public Entity getPlayerTargetEntity(Player player, int range) {
        List<Entity> nearbyE = player.getNearbyEntities(range,
                range, range);
        ArrayList<LivingEntity> livingE = new ArrayList<LivingEntity>();

        for (Entity e : nearbyE) {
            if (e instanceof LivingEntity) {
                livingE.add((LivingEntity) e);
            }
        }

        Entity target = null;
        BlockIterator bItr = new BlockIterator(player, range);
        Block block;
        Location loc;
        int bx, by, bz;
        double ex, ey, ez;
        // loop through player's line of sight
        while (bItr.hasNext()) {
                block = bItr.next();
                bx = block.getX();
                by = block.getY();
                bz = block.getZ();
                        // check for entities near this block in the line of sight
                        for (LivingEntity e : livingE) {
                                loc = e.getLocation();
                                ex = loc.getX();
                                ey = loc.getY();
                                ez = loc.getZ();
                                if ((bx-.75 <= ex && ex <= bx+1.75) && (bz-.75 <= ez && ez <= bz+1.75) && (by-1 <= ey && ey <= by+2.5)) {
                                        // entity is close enough, set target and stop
                                        target = e;
                                        break;
                                }
                        }
                }
        return target;

      }
	
}
