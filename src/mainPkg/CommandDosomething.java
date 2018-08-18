package mainPkg;
import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public class CommandDosomething implements CommandExecutor {
	// Heh heh this class is basically useless. But it gives a little example.
	@Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            // Create a new ItemStack (type: diamond)
            ItemStack diamond = new ItemStack(Material.DIAMOND);

            // Create a new ItemStack (type: brick)
            ItemStack bricks = new ItemStack(Material.BRICK);

            // Set the amount of the ItemStack
            bricks.setAmount(20);

            // Give the player our items (comma-seperated list of all ItemStack)
            player.getInventory().addItem(bricks, diamond);
        }

        // If the player (or console) uses our command correct, we can return true
        return true;
    }
}
