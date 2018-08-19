package mainPkg;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

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
    }
    
    // Fired when plugin is disabled.
    @Override
    public void onDisable() {

    }
}
