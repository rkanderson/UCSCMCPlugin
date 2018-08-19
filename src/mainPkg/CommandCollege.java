package mainPkg;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.JsonPrimitive;

import net.md_5.bungee.api.ChatColor;

public class CommandCollege implements CommandExecutor {
	private UCSCPluginMain plugin;
	private IconMenu menu;
	public CommandCollege(UCSCPluginMain p) {
		plugin = p;
		menu = new IconMenu("College Choice Menu", 18, new IconMenu.OptionClickEventHandler() {
            @Override
            public void onOptionClick(IconMenu.OptionClickEvent event) {
            	event.getPlayer().performCommand("college set "+event.getPosition());
            }
        }, plugin);
		Material[] choiceMaterials = new Material[]{Material.COBBLESTONE, Material.BRICK, Material.GOLD_BLOCK, 
				Material.ICE, Material.ANVIL, Material.NETHERRACK, Material.QUARTZ_BLOCK, Material.WOOL, 
				Material.LEAVES, Material.LOG};
		for(int i=0; i<10; i++) {
			menu.setOption(i, new ItemStack(choiceMaterials[i]), collegeNameByIndex(i));
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED+"You must be a player to use this command.");
			return false;
		}
		Player player = (Player)sender;
		if(args.length == 0) {
			// If there's nothing specified, just echo back the player's college name
			int collegeIndex = plugin.playerDataHelper.getInt(player.getName(), "college");
			if(collegeIndex == -1) {
				player.sendMessage(ChatColor.AQUA+"You're currently not associated with any college. Use /college set or /collge choose.");
				return false;
			} else {
				player.sendMessage(ChatColor.GREEN+"You are affiliated with "+collegeNameByIndex(collegeIndex)+"!");
				return true;
			}
		}
		if(args[0].equals("set")) {
			
			if(args.length >= 2) {
				String collegeName = args[1];
				String playerName = player.getName();
				int index = validateCollege(collegeName);
				if(index != -1) {
					plugin.playerDataHelper.setProperty(playerName, "college", new JsonPrimitive(index));
					player.sendMessage(ChatColor.GREEN+"College successfully set!");
					return true;
				} else {
					player.sendMessage(ChatColor.RED+"Choose a valid college plz.");
					return false;
				}
			} else {
				sender.sendMessage(ChatColor.AQUA+"/college set {college name}");
				sender.sendMessage(ChatColor.AQUA+"/college set {college index}");
				return false;
			}
		} else if(args[0].equals("choose")){
			// Makes a GUI pop up where the user can choose.
			menu.open(player);
			player.sendMessage(ChatColor.AQUA+"Choose wisely!");
			return true;
			
		} else if(args[0].equals("pack")){
			//TODO check for a cooldown timer.
			int collegeIndex = plugin.playerDataHelper.getInt(player.getName(), "college");
			if(collegeIndex == -1) {
				player.sendMessage(ChatColor.AQUA+"You don't seem to be affiliated with any college yet. Use /college choose first.");
				return false;
			} else {
				ItemStack[] pack = PackCreator.getPack(collegeIndex);
				for(ItemStack stack: pack) player.getInventory().addItem(stack);
				player.sendMessage(ChatColor.AQUA+"ENjoy!");
				return true;
			}
		} else {
			return false;
		}
	}
	
	
	// return index 0-9 based on college selection. -1 if invalid
	private int validateCollege(String chosen) {
		try {
			// handle case of passing in an integer. 0 to 9 is valid
			int index = Integer.parseInt(chosen);
			if(index >= 0 && index <= 9) return index;
		} catch(NumberFormatException e) {}
		// Otherwise, we do some cases
		switch(chosen.toLowerCase()) {
		case "stevenson":
			return 0;
		case "cowell":
			return 1;
		case "crown":
			return 2;
		case "merril":
			return 3;
		case "college_9":
			return 4;
		case "college_10":
			return 5;
		case "kresge":
			return 6;
		case "porter":
			return 7;
		case "rachel_carson": case "carson": case "college_8":
			return 8;
		case "oakes":
			return 9;
		}
		return -1;
	}
	
	// Gives properly formatted college names given the index
	private String collegeNameByIndex(int i) {
		switch(i) {
		case 0: return "Stevenson";
		case 1: return "Cowell";
		case 2: return "Crown";
		case 3: return "Merril";
		case 4: return "College 9";
		case 5: return "College 10";
		case 6: return "Kresge";
		case 7: return "Porter";
		case 8: return "Rachel Carson";
		case 9: return "Oakes";
		default: return "NULL";
		}
	}

}
