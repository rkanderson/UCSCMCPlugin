package mainPkg;

import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import net.md_5.bungee.api.ChatColor;

public class CollegeDescriptions {
	
	static final String descString = "{\"0\": {\"Gym-Goer\": \"You have increased strength and speed.\"}, \"1\": {\"Toughness\": \"You're slightly more damage resistant. You also dig faster.\"}, \"2\": {\"Hill_Climber\": \"Your jump height is 2 BLOCKS!\", \"FPS_Addict\": \"If you hit a mob with an arrow, you get that arrow back.\", \"Lightning_Hacks\": \"Your arrows have a chance of summoning lightning if they hit.\"}, \"3\": {\"Sterile\": \"You're immune to all poison effects.\", \"Repulsion\": \"Punching causes high knockback.\"}, \"4\": {\"The_Best_Dining_Hall\": \"You lose hunger points more slowly, and you heal more quickly.\", \"Social_Withdrawal\": \"Hold down shift to turn invisible! You need to take off your clothes to get the full effect tho.\"}, \"5\": {\"The_Best_Dining_Hall\": \"You lose hunger points more slowly, and you heal more quickly.\", \"Social_Withdrawal\": \"Hold down shift to turn invisible! You need to take off your clothes to get the full effect tho.\"}, \"6\": {\"Dank_Mode\": \"Right click with blaze powder in your hand to enter dank mode for 10 seconds. While in dank mode, you're faster, stronger, fire resistant, and have night vision; but your vision also gets a little distorted and you have a weak poison effect.\"}, \"7\": {\"Painting\": \"Right click on any wool block with any dye in your hand to dye the wool that color!\", \"Inspiration\": \"Right clicking on other players with certain dyes in your hand can grant a variety of short term buffs. If you aren't targeting any players, the buff is applied to yourself. Players can only be affected by one buff at a time.\"}, \"8\": {\"Efficient_Farming\": \"Right click on crops while holding a stick to make them instantly harvestable!\", \"Carrot_Dagger\": \"Carrots can be used to deal a lot of damage, but only at short range. Golden carrots cause instant death to all non-boss mobs but are usable only once.\"}, \"9\": {\"OAKES!!!\": \"Upon killing a mob with an axe, there is a chance of spawning an oak tree which creates an area of effect attack that significantly damages all mobs within a 10 block radius and lights them on fire.\"} }";
	static final JsonObject obj;
	static {
		obj = new JsonParser().parse(descString).getAsJsonObject();
	}
	
	// Returns something that can be put directly into the chat.
	public static String getDesc(int index) {
		if(index < 0 || index > 9) {
			return ChatColor.GREEN+"You're not affiliated with any colleges. Use /college choose";
		}
		String collegeName = collegeNameByIndex(index);
		String ret = ChatColor.WHITE+"************\n"+ChatColor.GREEN+"Here is a list of traits and abilities for "+collegeName+":\n\n";
		String key = Integer.toString(index);
		JsonObject collegeObject = obj.getAsJsonObject(key);
		for(Entry<String, JsonElement> entry : collegeObject.entrySet()) {
			String traitName = entry.getKey();
			String desc = ((JsonPrimitive)entry.getValue()).getAsString();
			ret += ""+ChatColor.AQUA+ChatColor.BOLD+traitName+ChatColor.WHITE+" - "+ChatColor.GREEN+desc+"\n";
		}
		ret+=ChatColor.WHITE+"************";
		return ret;
	}
	
	// Gives properly formatted college names given the index
	private static String collegeNameByIndex(int i) {
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
