package mainPkg;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class Drone {
	Location loc;
	World world;
	
	public Drone(Location start) {
		//Takes location passed in and clips it to the center of the block
		loc = new Location(start.getWorld(), (int)(start.getX()), (int)(start.getY())+0.5, (int)(start.getZ()));
		if(loc.getX() >= 0) {
			loc.setX(loc.getX()+0.5);
		} else {
			loc.setX(loc.getX()-0.5);
		}
		
		if(loc.getZ()>=0) {
			loc.setZ(loc.getZ()+0.5);
		} else {
			loc.setZ(loc.getZ()-0.5);
		}
		
		world = loc.getWorld();
	}
	
	public void changeX(int dx) {
		loc.setX(loc.getX()+dx);
	}
	public void changeY(int dy) {
		loc.setY(loc.getY()+dy);
	}
	public void changeZ(int dz) {
		loc.setZ(loc.getZ()+dz);
	}
	public void changeLoc(int dx, int dy, int dz) {
		this.changeX(dx); this.changeY(dy); this.changeZ(dz);
	}
	public Block setBlockAtLoc(Material mat) {
		Block b = world.getBlockAt(this.loc);
		System.out.println("World: "+world.toString());
		System.out.println("Loc "+this.loc);
		System.out.println("block "+b.toString());
		
		b.setType(mat);
		b.getState().update(true);
		return b;
	}
	public void setBlockRelativeToLocation(int rx, int ry, int rz, Material mat) {
		this.changeLoc(rx, ry, rz);
		Block b = setBlockAtLoc(mat);
		this.changeLoc(-rx, -ry, -rz);
		
		if(mat == Material.STONE) b.setData((byte)6);
	}
	
	// Builds a filled circle!
	public void buildCircle(Material mat, int radius) {
		for(int x=-radius; x<=radius; x+=1) {
			int chordLength = (int)(2*(float)Math.sqrt((float)Math.pow(radius, 2)-Math.pow(x, 2)));
			if(chordLength % 2 == 0) chordLength+=1; //Make sure the chord lengths are odd
			for(int i=0; i<chordLength; i++) {
				int relativeZ = (chordLength - 1)/2 * -1 + i;
				setBlockRelativeToLocation(x, 0, relativeZ, mat);
			}
		}
		System.out.println("filled circle complate");
	}
	
	//hollow circle
	public void buildHCircle(Material mat, int radius) {
		buildSphere(mat, radius);
		buildSphere(Material.AIR, radius-1);
	}
	
//	public void buildHCircle(Material mat, int radius) {
//		double deltaTheta = 0.1 / radius;
//		for(double theta = 0; theta<2*Math.PI; theta+=deltaTheta) {
//			int rx = Math.round(radius*(float)Math.cos(theta));
//			int rz = Math.round(radius*(float)Math.sin(theta));
//			this.setBlockRelativeToLocation(rx, 0, rz, mat);
//			center.getWorld().getBlockAt(buildLoc).setType(mat);
//			center.getWorld().getBlockAt(buildLoc).getState().update(true);
//			System.out.println("Built a block: " + center.getWorld().getBlockAt(buildLoc).toString());
//		}
//		System.out.println("Built a cricle type "+mat);
//	}
	
	//Hollow cylinder
	public void buildHCyl(Material mat, int radius, int h) {
		for(int i=0; i<Math.abs(h); i++) {
			buildHCircle(mat, radius);
			changeY(h/Math.abs(h));
		}	
	}
	
	//Non hollow cylinder
	public void buildCyl(Material mat, int radius, int h) {
		for(int i=0; i<Math.abs(h); i++) {
			buildCircle(mat, radius);
			changeY(h/Math.abs(h));
		}	
	}
	
	// Hollow sphere
	public void buildHSphere(Material mat, int radius) {
		buildSphere(mat, radius);
		buildSphere(Material.AIR, radius-1);
	}

//	public void buildHSphere(Material mat, int radius) {
//		double deltaTheta = 0.1 / radius;
//		for(double theta = 0; theta <= Math.PI; theta += deltaTheta) {
//			int relY = Math.round(radius * (float)Math.cos(theta));
//			int circleRad = Math.round(radius * (float)Math.sin(theta));
//			changeY(relY);
//			buildHCircle(mat, circleRad);
//			changeY(-relY);
//		}
//	}
	
	//Builds a filled sphere
	public void buildSphere(Material mat, int radius) {
		changeY(-radius);
		for(double y=0; y<2*radius; y+=0.25) {
			int subRad = Math.round((float)Math.sqrt(Math.pow(radius, 2)-Math.pow(y-radius, 2)));
			changeY((int)y);
			buildCircle(mat, subRad);
			changeY((int)-y);
		}
		changeY(radius);
	}
}
