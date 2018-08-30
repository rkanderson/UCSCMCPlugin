package mainPkg;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class UCSCPluginMain extends JavaPlugin {
	public static FileConfiguration config;
	public PlayerDataHelper playerDataHelper;
	// Fired when plugin is first enabled
    @Override
    public void onEnable() {
    	config = getConfig();
    	
    	config.addDefault("youAreAwesome", true);
        config.options().copyDefaults(true);
        saveConfig();
        
        playerDataHelper = new PlayerDataHelper(this);
        
    	this.getCommand("test1").setExecutor(new CommandTest());
    	this.getCommand("test2").setExecutor(new CommandTest());
    	this.getCommand("test3").setExecutor(new CommandTest());
    	this.getCommand("test4").setExecutor(new CommandTest());
    	this.getCommand("college").setExecutor(new CommandCollege(this));
    	
        getServer().getPluginManager().registerEvents(new MyListener(this), this);
        
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
            	updateStates();
            }
        }, 0L, 20L);
    }
    
    // Fired when plugin is disabled.
    @Override
    public void onDisable() {

    }
    
    // Can handle anything that requires frequent checking.
    // E.g. effects different colleges or classes require.
    private void updateStates(){
    	
    	// TODO add potion effects for all the colleges!!!
    	for(Player p: getServer().getOnlinePlayers()){
    		p.setWalkSpeed(0.2F);
    		int collegeIndex = playerDataHelper.getInt(p.getName(), "college");
    		if(collegeIndex==0) { //Stevenson
    			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 1));
    			p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 1));
    		} else if(collegeIndex==1) { //Cowell
    			p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 200, 1));
    			p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 200, 1));
    		} else if(collegeIndex==2) { //Crown
    			p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 200, 1));
    		} else if(collegeIndex == 4 || collegeIndex == 5) { // 9/10
    			p.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 200, 1));
    		} else if(collegeIndex == 6) {
    			boolean isDankMode = playerDataHelper.getBoolean(p.getName(), "is_dank_mode");
    			long dankModeTimestamp = playerDataHelper.getLong(p.getName(), "dank_mode_timestamp");
    			long secondsSinceTimestamp = (System.currentTimeMillis() - dankModeTimestamp) / 1000;
    			if(isDankMode) {
    				// Must work on balancing
	    			p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20, 5));
	    			p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20, 4));
	    			p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20, 4));
	    			p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 20, 0));
	    			p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 4));
	    			p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20, 4));
	    			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, 3));
	    			p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20, 0));
	    			p.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 20, 2));
	    			p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 5));
    			}
    			if(dankModeTimestamp != -1 && secondsSinceTimestamp >= 10) {
    				playerDataHelper.setProperty(p.getName(), "is_dank_mode", new JsonPrimitive(false));
    			}
    			
    		}
    	}
    }
}
