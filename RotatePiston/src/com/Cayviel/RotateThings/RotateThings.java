package com.Cayviel.RotateThings;

import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import org.bukkit.plugin.Plugin;


public class RotateThings extends JavaPlugin {

	private static InteractListener il = new InteractListener();
	public static PermissionHandler permissionHandler;
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
		
		pm.registerEvent(Event.Type.PLAYER_INTERACT, il, Priority.Normal, this);
		FileConfiguration config = this.getConfig();
		
		config.addDefault("Wand.Use a Wand",true);
		config.addDefault("Wand.Wand", "AIR");
		config.addDefault("Op only (instead of Permissions)", true);
		config.addDefault("Rotate.Pistons", true);
		config.addDefault("Rotate.Pumpkins", false);
		config.addDefault("Rotate.Stairs", false);
		config.addDefault("Rotate.Furnaces", false);
		config.addDefault("Rotate.Dispensers", false);
		config.addDefault("Rotate.Chest", false);
		config.addDefault("Rotate.Levers", false);		
		
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
		
		// set up our permissions
		if (!opOnlyBoolean && (getServer().getPluginManager().getPlugin("Permissions")!=null)){
			setupPermissions();
			usePerm = true;
		}
		// we have successfully enabled the plugin
		log.info("[RotateThigns]: Enabled!");
	}

	private void setupPermissions() {
		// if our permissions handler isn't null we shall return
		if (permissionHandler != null)
			return;

		// grab an instance of the permissions plugin
		Plugin permissionsPlugin = getServer().getPluginManager().getPlugin("Permissions");

		permissionHandler = ((Permissions) permissionsPlugin).getHandler();
		log.info("Found and will use plugin " + ((Permissions) permissionsPlugin).getDescription().getFullName());
	}
}

 