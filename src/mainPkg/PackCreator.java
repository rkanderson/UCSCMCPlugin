package mainPkg;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class PackCreator {

	public static ItemStack[] getPack(int collegeIndex) {
		// TODO matt can u do this por favor?
		/*
		 * To know what college index refers to, start on east side of campus with stevenson and
		 * make your way counter clockwise to oakes.
		 * 0 = stevenson, 1 = cowell, 2 = crown, 3 = merril, 
		 * 4 = c9, 5=c10, 6=kresge, 7=porter, 8=rachel carson, 9=oakes. 
		 */
		switch(collegeIndex) {
			//TODO return an item pack for each college. 
		    //	Already did a little smthing for oakes. U can change.
			// Probably good to copy and paste new ItemStack(...)
		case 9: //OAKES!!!!!
			return new ItemStack[]{
					new ItemStack(Material.IRON_AXE, 1),
					new ItemStack(Material.LOG,64),
					new ItemStack(Material.SAPLING, 16)
			};
		default: return new ItemStack[]{};
		}
	}

}
