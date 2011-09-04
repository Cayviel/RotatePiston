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
		
		// set up our permissions
		setupPermissions();
		
		// grab our configuration file if there is none created it will create it for us with OP default true
		config = getConfiguration();
		opOnlyBoolean = config.getBoolean("if no Permissions.op only", true);
		config.save();

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
