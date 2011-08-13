package com.Cayviel.RotatePiston;

import java.io.File;

import org.bukkit.util.config.Configuration;

public class Rotateconfig {

    public String directory;
    public File configFile;
    
	Rotateconfig(File conf, String dir){
		directory = dir;
		configFile = conf;
	}
		public void configCheck(){
	        new File(directory).mkdir();
	        if(!configFile.exists()){
	            try {
	            	configFile.createNewFile();
	                addDefaults();
		            } catch (Exception ex) {
	                ex.printStackTrace();
	            }
	        } 
		}
	
   public void write(String root, Object x){
       Configuration config = load();
       config.setProperty(root, x);
       config.save();
   }
   
   private Configuration load(){
	       try {
           Configuration config = new Configuration(configFile);
           config.load();
           return config;
	       } catch (Exception e) {
           e.printStackTrace();
       }
       return null;
   }
   
   private void addDefaults(){
    RotatePiston.log.info("Generating Config File...");
    write("if no Permissions.op only",false);
   }
   
   public boolean getOpOnly(){
       Configuration config = load();
       return config.getBoolean("if no Permissions.op only",false);
   }
}
