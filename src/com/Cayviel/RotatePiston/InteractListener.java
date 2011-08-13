package com.Cayviel.RotatePiston;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.block.Action;

public class InteractListener extends PlayerListener {

	byte clickeddirc(byte clickeddir){
	switch (clickeddir){
	case 0: return 1; 
	case 1: return 0; 
	case 2: return 3; 
	case 3: return 2;
	case 4: return 5;
	case 5: return 4;
	default: return 1;
	}
	}
	
	public void onPlayerInteract(PlayerInteractEvent PlayerWithPiston){

		if (RotatePiston.permissionHandler == null) {
			if (RotatePiston.useop&&(!PlayerWithPiston.getPlayer().isOp())){ return; }
			}else{		
				if (!RotatePiston.permissionHandler.has(PlayerWithPiston.getPlayer(),"rotatepiston")){ return; }
			}
		
		if (PlayerWithPiston.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
			
		Material Blocktype = PlayerWithPiston.getClickedBlock().getType();
		
		if ((Blocktype == Material.PISTON_BASE)||(Blocktype == Material.PISTON_STICKY_BASE)){
			BlockFace clickedface = PlayerWithPiston.getBlockFace();
			Block piston = PlayerWithPiston.getClickedBlock();			
			byte dir = piston.getData();
			byte clickeddir;

			if (clickedface == BlockFace.valueOf("NORTH")){clickeddir=4;}else{
				if (clickedface == BlockFace.valueOf("EAST")){clickeddir=2;}else{
					if (clickedface == BlockFace.valueOf("SOUTH")){clickeddir=5;}else{
						if (clickedface == BlockFace.valueOf("WEST")){clickeddir=3;}else{
							if (clickedface == BlockFace.valueOf("UP")){clickeddir=1;}else{
								if (clickedface == BlockFace.valueOf("DOWN")){clickeddir=0;}else{
									clickeddir=1;
								}
							}
						}
					}
				}
			}
			
		if (!PlayerWithPiston.getPlayer().isSneaking()){
			if (clickeddir == dir){
				piston.setData(clickeddirc(clickeddir));	
			}else{
				piston.setData(clickeddir);
			}
		}else{
			while(((byte)((dir+1) % 6)==clickeddir)||((byte)((dir+1) % 6)==clickeddirc(clickeddir))){dir = (byte)((dir+1) % 6);} //don't try sides already accessible
			piston.setData((byte)((dir+1) % 6));
			}
		}
	}
		
	}
}