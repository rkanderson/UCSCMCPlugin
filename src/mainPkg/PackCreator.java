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
		case 0: //Stevenson
			return new ItemStack[]{
					new ItemStack(Material.IRON_SWORD, 1),
					new ItemStack(Material.STONE_HOE, 1),
					new ItemStack(Material.STONE_PICKAXE, 1),
					new ItemStack(Material.STONE_SPADE, 1),
					new ItemStack(Material.STONE_AXE, 1),
					new ItemStack(Material.BREAD, 64),
			};
		case 1: //Cowell
			return new ItemStack[]{
					new ItemStack(Material.STONE_SWORD, 1),
					new ItemStack(Material.STONE_HOE, 1),
					new ItemStack(Material.IRON_PICKAXE, 1),
					new ItemStack(Material.STONE_SPADE, 1),
					new ItemStack(Material.STONE_AXE, 1),
					new ItemStack(Material.BREAD, 64),
			};
		case 2: //Crown
			return new ItemStack[]{
					new ItemStack(Material.STONE_SWORD, 1),
					new ItemStack(Material.STONE_HOE, 1),
					new ItemStack(Material.STONE_PICKAXE, 1),
					new ItemStack(Material.STONE_SPADE, 1),
					new ItemStack(Material.STONE_AXE, 1),
					new ItemStack(Material.BREAD, 64),
					new ItemStack(Material.REDSTONE, 64),
					new ItemStack(Material.REDSTONE, 64),
					new ItemStack(Material.REDSTONE, 64),
					new ItemStack(Material.PISTON_BASE, 16),
					new ItemStack(Material.SLIME_BALL, 16),
					new ItemStack(Material.IRON_INGOT, 32),
			};
		case 3: //Merril
			return new ItemStack[]{
					new ItemStack(Material.STONE_SWORD, 1),
					new ItemStack(Material.STONE_HOE, 1),
					new ItemStack(Material.STONE_PICKAXE, 1),
					new ItemStack(Material.STONE_SPADE, 1),
					new ItemStack(Material.STONE_AXE, 1),
					new ItemStack(Material.COOKIE, 64),
					new ItemStack(Material.CHORUS_FRUIT, 64),
			};
		case 4: //College 9
			return new ItemStack[]{
					new ItemStack(Material.STONE_SWORD, 1),
					new ItemStack(Material.STONE_HOE, 1),
					new ItemStack(Material.STONE_PICKAXE, 1),
					new ItemStack(Material.STONE_SPADE, 1),
					new ItemStack(Material.STONE_AXE, 1),
					new ItemStack(Material.BREAD, 64),
					new ItemStack(Material.BREAD, 64),
					new ItemStack(Material.BREAD, 64),
					new ItemStack(Material.COOKED_BEEF, 64),
					new ItemStack(Material.GRILLED_PORK, 64),
					new ItemStack(Material.BAKED_POTATO, 64),
					new ItemStack(Material.CAKE, 64),
					new ItemStack(Material.COOKIE, 64),
					new ItemStack(Material.PUMPKIN_PIE, 64),
			};
		case 5: //College 10
			return new ItemStack[]{
					new ItemStack(Material.STONE_SWORD, 1),
					new ItemStack(Material.STONE_HOE, 1),
					new ItemStack(Material.STONE_PICKAXE, 1),
					new ItemStack(Material.STONE_SPADE, 1),
					new ItemStack(Material.STONE_AXE, 1),
					new ItemStack(Material.BREAD, 64),
					new ItemStack(Material.BREAD, 64),
					new ItemStack(Material.BREAD, 64),
					new ItemStack(Material.COOKED_BEEF, 64),
					new ItemStack(Material.GRILLED_PORK, 64),
					new ItemStack(Material.BAKED_POTATO, 64),
					new ItemStack(Material.CAKE, 64),
					new ItemStack(Material.COOKIE, 64),
					new ItemStack(Material.PUMPKIN_PIE, 64),
			};
		case 6: //Kresge
			return new ItemStack[]{
					new ItemStack(Material.STONE_SWORD, 1),
					new ItemStack(Material.STONE_HOE, 1),
					new ItemStack(Material.STONE_PICKAXE, 1),
					new ItemStack(Material.STONE_SPADE, 1),
					new ItemStack(Material.STONE_AXE, 1),
					new ItemStack(Material.BLAZE_POWDER, 4),
					new ItemStack(Material.BLAZE_POWDER, 20),
					new ItemStack(Material.BLAZE_POWDER, 64),
					new ItemStack(Material.RED_MUSHROOM, 32),
					new ItemStack(Material.BROWN_MUSHROOM, 32),
					new ItemStack(Material.BREAD, 64),
			};
		case 7: //Porter
			return new ItemStack[]{ 
					new ItemStack(Material.STONE_SWORD, 1),
					new ItemStack(Material.STONE_PICKAXE, 1),
					new ItemStack(Material.STONE_SPADE, 1),
					new ItemStack(Material.STONE_AXE, 16),
					new ItemStack(Material.INK_SACK, 64, (short)1), //RED
					new ItemStack(Material.INK_SACK, 64, (short)2), //GREEN
					new ItemStack(Material.INK_SACK, 64, (short)4), //BLUE
					new ItemStack(Material.INK_SACK, 64, (short)11), //YELLOW
					new ItemStack(Material.WOOL, 64),
					new ItemStack(Material.WOOL, 64),
					new ItemStack(Material.WOOL, 64),
			};
		case 8: //Rachel Carson
			return new ItemStack[]{
					new ItemStack(Material.STICK, 1),
					new ItemStack(Material.DIAMOND_HOE, 1),
					new ItemStack(Material.GOLD_SWORD, 1),
					new ItemStack(Material.STONE_PICKAXE, 1),
					new ItemStack(Material.STONE_SPADE, 1),
					new ItemStack(Material.STONE_AXE, 1),
					new ItemStack(Material.SEEDS, 16),
					new ItemStack(Material.BONE, 32),
					new ItemStack(Material.POTATO, 16),
					new ItemStack(Material.CARROT, 16),
					new ItemStack(Material.MELON_SEEDS, 16),
					new ItemStack(Material.PUMPKIN_SEEDS, 16),
					new ItemStack(Material.SADDLE, 1),
			};
		case 9: //OAKES!!!!!
			return new ItemStack[]{
					new ItemStack(Material.IRON_AXE, 1),
					new ItemStack(Material.IRON_AXE, 1),
					new ItemStack(Material.IRON_AXE, 1),
					new ItemStack(Material.STONE_PICKAXE, 1),
					new ItemStack(Material.STONE_SPADE, 1),
					new ItemStack(Material.STONE_HOE, 1),
					new ItemStack(Material.STONE_SWORD, 1),
					new ItemStack(Material.LOG, 64),
					new ItemStack(Material.LOG, 64),
					new ItemStack(Material.SAPLING, 16),
					new ItemStack(Material.BREAD, 64),
			};
		default: return new ItemStack[]{};
		}
	}
	

}
