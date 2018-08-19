package mainPkg;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class PlayerDataHelper {
	private JavaPlugin plugin;
	private File dataFile;
	private JsonObject dataObj;
	public PlayerDataHelper(JavaPlugin plugin) {
		this.plugin = plugin;
		dataFile = new File("plugins/UCSCPlugin/player_data.json");
		// Create the file if it doesn't already exist.
		try { 
			dataFile.createNewFile();
			loadJson();
		} 
		catch(Exception e){ e.printStackTrace(); }
		
		// Schedule repeating saving. Every 5 seconds (100 ticks).
		BukkitScheduler scheduler = plugin.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
            	saveJson();
            }
        }, 0L, 100L);
		
	}
	
	// Creates the JSON object based on the contents of dataFile
	private void loadJson() throws FileNotFoundException{
		String jsonString = "";
		Scanner sc = new Scanner(dataFile);
		while(sc.hasNextLine()) {
			jsonString += sc.nextLine();
		}
		sc.close();
		if(jsonString.equals("")) jsonString = "{}";
		dataObj = new JsonParser().parse(jsonString).getAsJsonObject();
	}
	
	// Saves contents of json object back as s
	private void saveJson() {
		PrintWriter writer;
		try {
			writer = new PrintWriter(dataFile);
			writer.print(dataObj.toString().equals("") ? "{}" : dataObj.toString());
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void setProperty(String playerName, String prop, JsonElement value) {
		if(!dataObj.has(playerName)) dataObj.add(playerName, new JsonObject());
		JsonObject playerData = (JsonObject)dataObj.get(playerName);
		if(playerData.has(prop)) playerData.remove(prop);
		playerData.add(prop, value);
	}
	
	// Returns the property as a JsonElement. Returns null if it doesn't exist.
	public JsonElement getProperty(String playerName, String prop) {
		if(!dataObj.has(playerName)) dataObj.add(playerName, new JsonObject());
		JsonObject playerData = (JsonObject)dataObj.get(playerName);
		if(playerData.has(prop)) return playerData.get(prop);
		else return null;
		
	}
	
	public String getString(String playerName, String prop) {
		JsonElement element = getProperty(playerName, prop);
		return element.getAsString();
	}
	
	public int getInt(String playerName, String prop) {
		JsonElement element = getProperty(playerName, prop);
		try {
			int i = element.getAsInt();
			return i;
		} catch(NullPointerException e) {
			return -1;
		}
	}
}
