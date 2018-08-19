package mainPkg;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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
        }, 0L, 100L);
    }
    
    // Fired when plugin is disabled.
    @Override
    public void onDisable() {

    }
    
    // Can handle anything that requires frequent checking.
    // E.g. effects different colleges or classes require.
    private void updateStates(){
    	
    	for(Player p: getServer().getOnlinePlayers()){
    		int collegeIndex = playerDataHelper.getInt(p.getName(), "college");
    		if(collegeIndex==0) { //Stevenson
    			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 2));
    		}
    	}
    }
}
