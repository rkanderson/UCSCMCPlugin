package mainPkg;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandTest implements CommandExecutor {
	
	// Lets use this to test stuff. ANYTHING!!!!

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equals("test1")) {
			sender.sendMessage("Yay you tested something!!!");
		} else if(label.equals("test2")) {
			
		} else if(label.equals("test3")) {
			
		} else if(label.equals("test4")) {
			
		} else {
			return false;
		}
		return true;
	}
	
}
