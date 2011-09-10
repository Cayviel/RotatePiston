package com.Cayviel.RotatePiston;

import java.util.logging.Logger;

import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.config.Configuration;

public class RotatePiston extends JavaPlugin {

	private static InteractListener il = new InteractListener();
	public static PermissionHandler permissionHandler;
	public static Logger log = Logger.getLogger("Minecraft");

	private Configuration config;
	static public Boolean opOnlyBoolean;
	static public Boolean useWand;
	static public Boolean usePerm = false;
	static public String WandName;
	
	@Override
	public void onDisable() {
		getServer().getLogger().info("RotatePiston Disabled!");
	}

	@Override
	public void onEnable() {
		// grab an instance of plugin manager
		PluginManager pm = getServer().getPluginManager();
		// register player interaction
		pm.registerEvent(Event.Type.PLAYER_INTERACT, il, Priority.Normal, this);

		// grab our configuration file if there is none created it will create it for us with OP default true
		config = getConfiguration();
		useWand = config.getBoolean("Wand.Use a Wand", false);
		WandName = config.getString("Wand.Wand", "AIR");
		opOnlyBoolean = config.getBoolean("Op only (instead of Permissions)", true);
		config.save();

		// set up our permissions
		if (!opOnlyBoolean && (getServer().getPluginManager().getPlugin("Permissions")!=null)){
			setupPermissions();
			usePerm = true;
		}
		// we have successfully enabled the plugin
		log.info("[RotatePiston]: Enabled!");
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
