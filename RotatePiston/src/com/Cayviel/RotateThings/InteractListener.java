package com.Cayviel.RotateThings;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.block.Action;

public class InteractListener extends PlayerListener {
	byte pdataE = 1;
	byte pdataS = 1;
	BlockFace chd = BlockFace.NORTH;
	
	boolean RL = true;
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
		if (RotateThings.opOnlyBoolean && (!PlayerWithPiston.getPlayer().isOp()))
			return;
		if (RotateThings.usePerm){
			if (!RotateThings.permissionHandler.has(PlayerWithPiston.getPlayer(), "rotatethings"))
				return;
		}
		if (RotateThings.useWand && (PlayerWithPiston.getPlayer().getItemInHand().getType()!=Material.getMaterial(RotateThings.WandName)))
			return;
		if (((PlayerWithPiston.getAction().equals(Action.RIGHT_CLICK_BLOCK) || 
			PlayerWithPiston.getAction().equals(Action.LEFT_CLICK_BLOCK)) && 
			PlayerWithPiston.getPlayer().isSneaking())) { // if they clicked and sneaking
			
			Material Blocktype = PlayerWithPiston.getClickedBlock().getType();
			
			if (((Blocktype == Material.PISTON_BASE) || (Blocktype == Material.PISTON_STICKY_BASE)) && (RotateThings.EnPistons)) {

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
				return;
			}

			RL=PlayerWithPiston.getAction().equals(Action.RIGHT_CLICK_BLOCK);
			
			if (((Blocktype == Material.PUMPKIN)||(Blocktype == Material.JACK_O_LANTERN))&&RotateThings.EnPumpkins){
				
				if (RL){
					PlayerWithPiston.getClickedBlock().setData((byte)((PlayerWithPiston.getClickedBlock().getData()+3) % 4));

				}else{
					PlayerWithPiston.getClickedBlock().setData((byte)((PlayerWithPiston.getClickedBlock().getData()+1) % 4));
				}
				return;
			}
			if ((Blocktype == Material.COBBLESTONE_STAIRS)||(Blocktype == Material.WOOD_STAIRS)&&RotateThings.EnStairs){
				if (RL){
				switch (PlayerWithPiston.getClickedBlock().getData()){
				case 0:PlayerWithPiston.getClickedBlock().setData((byte) 3); break;
				case 3:PlayerWithPiston.getClickedBlock().setData((byte) 1); break;
				case 1:PlayerWithPiston.getClickedBlock().setData((byte) 2); break;
				case 2:PlayerWithPiston.getClickedBlock().setData((byte) 0); break;
				default: RotateThings.log.info("Error during Stairs rotation"); break;
				}}else{
					switch (PlayerWithPiston.getClickedBlock().getData()){
					case 0:PlayerWithPiston.getClickedBlock().setData((byte) 2); break;
					case 3:PlayerWithPiston.getClickedBlock().setData((byte) 0); break;
					case 1:PlayerWithPiston.getClickedBlock().setData((byte) 3); break;
					case 2:PlayerWithPiston.getClickedBlock().setData((byte) 1); break;
					default: RotateThings.log.info("Error during Stairs rotation"); break;
					}					
				}
				return;
			}
			if (Blocktype == Material.FURNACE&&RotateThings.EnFurn){
				if (RL){
					return; 
					}
				else{
					switch (PlayerWithPiston.getClickedBlock().getData()){
					case 2:PlayerWithPiston.getClickedBlock().setData((byte) 5); break;
					case 4:PlayerWithPiston.getClickedBlock().setData((byte) 2); break;
					case 3:PlayerWithPiston.getClickedBlock().setData((byte) 4); break;
					case 5:PlayerWithPiston.getClickedBlock().setData((byte) 3); break;
					default:
						RotateThings.log.info("Error during Furnace rotation");
						RotateThings.log.info("Furnace Data = "+ PlayerWithPiston.getClickedBlock().getData());
						break;
				}
			}
				return;
			}
			if (Blocktype == Material.BURNING_FURNACE&&RotateThings.EnFurn){
				if (RL){
					return; 
					}
				else{
					switch (PlayerWithPiston.getClickedBlock().getData()){
					case 2:PlayerWithPiston.getClickedBlock().setData((byte) 5); break;
					case 4:PlayerWithPiston.getClickedBlock().setData((byte) 2); break;
					case 3:PlayerWithPiston.getClickedBlock().setData((byte) 4); break;
					case 5:PlayerWithPiston.getClickedBlock().setData((byte) 3); break;
					default:
						RotateThings.log.info("Error during Furnace rotation");
						RotateThings.log.info("Furnace Data = "+ PlayerWithPiston.getClickedBlock().getData());
						break;
				}
			}
				return;
			}
			
			if (Blocktype == Material.LEVER&&RotateThings.EnLever){
					switch (PlayerWithPiston.getClickedBlock().getData()){
					
					case 5:PlayerWithPiston.getClickedBlock().setData((byte) 6); break;
					case 6:PlayerWithPiston.getClickedBlock().setData((byte) 13); break;
					case 13:PlayerWithPiston.getClickedBlock().setData((byte) 14); break;
					case 14:PlayerWithPiston.getClickedBlock().setData((byte) 5); break;
					case 9: break;
					case 1: break;
					default:
						RotateThings.log.info("Error during Lever rotation");
						RotateThings.log.info("Lever Data = "+ PlayerWithPiston.getClickedBlock().getData()); 

						break;
					}
				return;
			}
			
			if (Blocktype == Material.DISPENSER&&RotateThings.EnDisp){
				if (RL){
					return; 
					}
				else{
					switch (PlayerWithPiston.getClickedBlock().getData()){
					case 2:PlayerWithPiston.getClickedBlock().setData((byte) 5); break;
					case 4:PlayerWithPiston.getClickedBlock().setData((byte) 2); break;
					case 3:PlayerWithPiston.getClickedBlock().setData((byte) 4); break;
					case 5:PlayerWithPiston.getClickedBlock().setData((byte) 3); break;
					default:
						RotateThings.log.info("Error during Dispenser rotation");
						RotateThings.log.info("Dispenser Data = "+ PlayerWithPiston.getClickedBlock().getData());
						break;
				}
			}
				return;
			}

			if (Blocktype == Material.CHEST&&RotateThings.EnChest){
				if (RL){
					return; 
					}
				else{
					if ((PlayerWithPiston.getClickedBlock().getRelative(BlockFace.NORTH).getType()==Material.CHEST)||(PlayerWithPiston.getClickedBlock().getRelative(BlockFace.SOUTH).getType()==Material.CHEST)||(PlayerWithPiston.getClickedBlock().getRelative(BlockFace.EAST).getType()==Material.CHEST)||(PlayerWithPiston.getClickedBlock().getRelative(BlockFace.WEST).getType()==Material.CHEST)){
						if (PlayerWithPiston.getClickedBlock().getRelative(BlockFace.NORTH).getType()==Material.CHEST) chd=BlockFace.NORTH;
						else if (PlayerWithPiston.getClickedBlock().getRelative(BlockFace.SOUTH).getType()==Material.CHEST) chd=BlockFace.SOUTH;
						else if (PlayerWithPiston.getClickedBlock().getRelative(BlockFace.EAST).getType()==Material.CHEST) chd=BlockFace.EAST;
						else if (PlayerWithPiston.getClickedBlock().getRelative(BlockFace.WEST).getType()==Material.CHEST) chd=BlockFace.WEST;
					
						switch (PlayerWithPiston.getClickedBlock().getData()){
						case 2:
							PlayerWithPiston.getClickedBlock().setData((byte) 3);
							PlayerWithPiston.getClickedBlock().getRelative(chd).setData((byte) 3); break;
						case 3:
							PlayerWithPiston.getClickedBlock().setData((byte) 2);
							PlayerWithPiston.getClickedBlock().getRelative(chd).setData((byte) 2); break;
						case 4:
							PlayerWithPiston.getClickedBlock().setData((byte) 5);
							PlayerWithPiston.getClickedBlock().getRelative(chd).setData((byte) 5); break;
						case 5:
							PlayerWithPiston.getClickedBlock().setData((byte) 4);
							PlayerWithPiston.getClickedBlock().getRelative(chd).setData((byte) 4); break;
						}
					}
					else{
						switch (PlayerWithPiston.getClickedBlock().getData()){
						case 2:PlayerWithPiston.getClickedBlock().setData((byte) 5); break;
						case 4:PlayerWithPiston.getClickedBlock().setData((byte) 2); break;
						case 3:PlayerWithPiston.getClickedBlock().setData((byte) 4); break;
						case 5:PlayerWithPiston.getClickedBlock().setData((byte) 3); break;
						default:
							RotateThings.log.info("Error during Chest rotation");
							RotateThings.log.info("Chest Data = "+ PlayerWithPiston.getClickedBlock().getData());
							break;
					}
				}
			}
				return;
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
				RotateThings.log.info("enum default chose");
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
				RotateThings.log.info("[RotateThings]: something went wrong in Piston rotation!");
				break;
		}
	}
}
