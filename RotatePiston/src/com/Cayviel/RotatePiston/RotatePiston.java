package com.Cayviel.RotatePiston;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import org.bukkit.plugin.Plugin;

public class RotatePiston extends JavaPlugin{

	private static InteractListener il = new InteractListener();
    public static PermissionHandler permissionHandler;
    public static boolean useop;
	public static Logger log = Logger.getLogger("Minecraft");

	public static String directory = "plugins" + File.separator + "RotatePiston";	
    public static File configFile = new File(directory + File.separator + "config.yml");
    public static File serverproperties = new File("server.properties");
	public static Properties properties = new Properties();
	public static Rotateconfig rotateconfig = new Rotateconfig(configFile,directory);
	
	@Override
	public void onDisable() {
		getServer().getLogger().info("RotatePiston Disabled!");
		
	}

	@Override
	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.PLAYER_INTERACT, il, Priority.Normal, this);
		getServer().getLogger().info("[RotatePiston]: Enabled!");
		setupPermissions();

		rotateconfig.configCheck();
	    try {
			FileInputStream inputfile = new FileInputStream(serverproperties);
			properties.load(inputfile);
			inputfile.close();
		} catch (IOException eceptin) {
			eceptin.printStackTrace();
		}	
	}
	
	private void setupPermissions() { 
		if (permissionHandler != null) { return; }

		Plugin permissionsPlugin = getServer().getPluginManager().getPlugin("Permissions");

		if (permissionsPlugin == null) {
			if (rotateconfig.getOpOnly()){
				log.info("Permission system not detected, defaulting to OP");
				useop = true;
				return;
			}else{useop= false; return;}
		}
		permissionHandler = ((Permissions) permissionsPlugin).getHandler();
		useop = false;
		log.info("Found and will use plugin "+((Permissions)permissionsPlugin).getDescription().getFullName());
	}
}
