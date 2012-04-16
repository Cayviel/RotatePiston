package com.Cayviel.RotateThings;

import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;


public class RotateThings extends JavaPlugin {

	private static InteractListener il = new InteractListener();
	public static Logger log = Logger.getLogger("Minecraft");

//	private YamlConfiguration config;
	static public Boolean opOnlyBoolean;
	static public Boolean useWand;
	static public String WandName;
	static public Boolean usePerm = false;
	static public Boolean EnPistons;
	static public Boolean EnPumpkins;
	static public Boolean EnStairs;
	static public Boolean EnFurn;
	static public Boolean EnDisp;
	static public Boolean EnChest;
	static public Boolean EnLever;
	static public Boolean EnDiode;
	static public Boolean EnRails;
	static public Boolean EnFenceGate;
	static public Boolean EnTrapDoor;
	static public Boolean EnIronDoor;
	static public Boolean EnWoodDoor;

	
//	static File confile;
	
	@Override
	public void onDisable() {
		getServer().getLogger().info("RotateThings Disabled!");
	}
	
		@Override
	public void onEnable() {
		// grab an instance of plugin manager
		PluginManager pm = getServer().getPluginManager();
		// register player interaction
		
		pm.registerEvents(il, this);
		
		FileConfiguration config = this.getConfig();
		
		config.addDefault("Wand.Use a Wand",true);
		config.addDefault("Wand.Wand", "AIR");
		config.addDefault("Op only (instead of Permissions)", true);
		config.addDefault("Rotate.Pistons", true);
		config.addDefault("Rotate.Pumpkins", true);
		config.addDefault("Rotate.Stairs", false);
		config.addDefault("Rotate.Furnaces", false);
		config.addDefault("Rotate.Dispensers", false);
		config.addDefault("Rotate.Chest", false);
		config.addDefault("Rotate.Levers", false);
		config.addDefault("Rotate.Fence Gates", false);
		config.addDefault("Rotate.Wooden Doors", false);
		config.addDefault("Rotate.Iron Doors", false);
		config.addDefault("Rotate.Trap Doors", false);
		config.addDefault("Rotate.Redstone Repeaters", false);
		config.addDefault("Rotate.Rails", false);
		
		config.options().copyDefaults(true);
		this.saveConfig();
		
		useWand = config.getBoolean("Wand.Use a Wand");
		WandName = config.getString("Wand.Wand", "AIR");
		opOnlyBoolean = config.getBoolean("Op only (instead of Permissions)");
		EnPistons = config.getBoolean("Rotate.Pistons");
		EnPumpkins = config.getBoolean("Rotate.Pumpkins");
		EnStairs = config.getBoolean("Rotate.Stairs");
		EnFurn = config.getBoolean("Rotate.Furnaces");
		EnDisp = config.getBoolean("Rotate.Dispensers");
		EnChest = config.getBoolean("Rotate.Chest");
		EnLever = config.getBoolean("Rotate.Levers");
		EnFenceGate = config.getBoolean("Rotate.Fence Gates");
		EnWoodDoor = config.getBoolean("Rotate.Wooden Doors");
		EnIronDoor = config.getBoolean("Rotate.Iron Doors");
		EnTrapDoor = config.getBoolean("Rotate.Trap Doors");
		EnDiode = config.getBoolean("Rotate.Redstone Repeaters");
		EnRails = config.getBoolean("Rotate.Rails");
		
		// set up our permissions
		if (!opOnlyBoolean){
			usePerm = true;
		}
		// we have successfully enabled the plugin
		log.info("[RotateThigns]: Enabled!");
	}
}

 