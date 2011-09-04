package com.Cayviel.RotatePiston;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.block.Action;

public class InteractListener extends PlayerListener {
	byte pdataE = 1;
	byte pdataS = 1;
	
	byte clickeddirc(byte clickeddir) {
		switch (clickeddir) {
			case 0:
				return 1;
			case 1:
				return 0;
			case 2:
				return 3;
			case 3:
				return 2;
			case 4:
				return 5;
			case 5:
				return 4;
			default:
				return 1;
		}
	}
	
	public void onPlayerInteract(PlayerInteractEvent PlayerWithPiston) {
		
		if (RotatePiston.permissionHandler == null && RotatePiston.opOnlyBoolean && (!PlayerWithPiston.getPlayer().isOp()))
			return;
		else if (!RotatePiston.permissionHandler.has(PlayerWithPiston.getPlayer(), "rotatepiston"))
			return;
		
		if ((PlayerWithPiston.getAction().equals(Action.RIGHT_CLICK_BLOCK) || 
			PlayerWithPiston.getAction().equals(Action.LEFT_CLICK_BLOCK)) && 
			PlayerWithPiston.getPlayer().isSneaking()) { // if they clicked and sneaking
			
			Material Blocktype = PlayerWithPiston.getClickedBlock().getType();
			if ((Blocktype == Material.PISTON_BASE) || (Blocktype == Material.PISTON_STICKY_BASE)) {
				
				if (Blocktype == Material.PISTON_STICKY_BASE)
					pdataS = 3;
				else
					pdataS = 1;
				
				BlockFace clickedface = PlayerWithPiston.getBlockFace();
				Block piston = PlayerWithPiston.getClickedBlock();
				
				byte dir = (byte) (piston.getData() % 8);
				byte clickeddir;
				byte dirsetto;
				
				if (clickedface == BlockFace.valueOf("NORTH"))
					clickeddir = 4;
				else if (clickedface == BlockFace.valueOf("EAST"))
					clickeddir = 2;
				else if (clickedface == BlockFace.valueOf("SOUTH"))
					clickeddir = 5;
				else if (clickedface == BlockFace.valueOf("WEST"))
					clickeddir = 3;
				else if (clickedface == BlockFace.valueOf("UP"))
					clickeddir = 1;
				else if (clickedface == BlockFace.valueOf("DOWN"))
					clickeddir = 0;
				else
					clickeddir = 1; // by default we will pick up
				
				if (PlayerWithPiston.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
					if (clickeddir == dir) 
						dirsetto = clickeddirc(clickeddir);
					else
						dirsetto = clickeddir;
				}
				else {
					while (((byte) ((dir + 1) % 6) == clickeddir) || 
						   ((byte) ((dir + 1) % 6) == clickeddirc(clickeddir))) {
						dir = (byte) ((dir + 1) % 6);
					} // don't try sides already accessible
					dirsetto = (byte) ((dir + 1) % 6);
				}
				
				if ((piston.isBlockPowered() || piston.isBlockIndirectlyPowered()) &&
					piston.getRelative(convertdirtoface(dirsetto)).getType() == Material.AIR) 
					pdataE = 2;
				else
					pdataE = 1;
				
				Rotate(piston, (byte) (pdataE * pdataS), dirsetto);
				pdataE = 1;
				pdataS = 1;
				
			}
			
		}
	}
	
	private BlockFace convertdirtoface(byte dir) {
		switch (dir) {
			case 0:
				return BlockFace.DOWN;
			case 1:
				return BlockFace.UP;
			case 2:
				return BlockFace.EAST;
			case 3:
				return BlockFace.WEST;
			case 4:
				return BlockFace.NORTH;
			case 5:
				return BlockFace.SOUTH;
			default: {
				RotatePiston.log.info("enum default chose");
				return BlockFace.DOWN;
			}
		}
	}
	
	private void Rotate(Block piston, byte pdata, byte dirsetto) {
		piston.setTypeId(0);
		switch (pdata) {
			case 1: {
				piston.setTypeIdAndData(33, dirsetto, false);
			}
				break;
			case 2: {
				piston.setTypeIdAndData(33, (byte) (dirsetto + 8), false);
				piston.getRelative(convertdirtoface(dirsetto)).setTypeIdAndData(34, dirsetto, false);
			}
				break;
			case 3: {
				piston.setTypeIdAndData(29, dirsetto, false);
			}
				break;
			case 6: {
				piston.setTypeIdAndData(29, (byte) (dirsetto + 8), false);
				piston.getRelative(convertdirtoface(dirsetto)).setTypeIdAndData(34, (byte) (dirsetto + 8), false);
			}
				break;
			default:
				RotatePiston.log.info("[RotatePiston]: something went wrong in rotation!");
				break;
		}
	}
}
