package com.Cayviel.RotateThings;

import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.block.Action;

public class InteractListener extends PlayerListener {
	byte pdataE = 1;
	byte pdataS = 1;
	byte data;
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
	void setD(Block b, int i){
		b.setData((byte)i);
	}
	public void onPlayerInteract(PlayerInteractEvent ievent) {
		if (RotateThings.opOnlyBoolean && (!ievent.getPlayer().isOp()))
			return;
		if (RotateThings.usePerm){
			if (!RotateThings.permissionHandler.has(ievent.getPlayer(), "rotatethings"))
				return;
		}
	
		if (((ievent.getAction().equals(Action.RIGHT_CLICK_BLOCK) || 
			ievent.getAction().equals(Action.LEFT_CLICK_BLOCK)) && 
			ievent.getPlayer().isSneaking())) { // if they clicked and sneaking
			
			if (RotateThings.useWand && (ievent.getPlayer().getItemInHand().getType()!=Material.getMaterial(RotateThings.WandName))){return;}
			
			Block b = ievent.getClickedBlock(); //interacted block, block b
			Material Blocktype = b.getType();//type of block
			int bID = Blocktype.getId();
			BlockFace clickedface = ievent.getBlockFace();


			data = b.getData();
			Logger log = RotateThings.log;
			//Pistons
			if (((Blocktype == Material.PISTON_BASE) || (Blocktype == Material.PISTON_STICKY_BASE)) && (RotateThings.EnPistons)) {

				if (Blocktype == Material.PISTON_STICKY_BASE)
					pdataS = 3;
				else
					pdataS = 1;

				byte dir = (byte) (data % 8);
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

				if (ievent.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
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

				if ((b.isBlockPowered() || b.isBlockIndirectlyPowered()) &&
					b.getRelative(convertdirtoface(dirsetto)).getType() == Material.AIR) 
					pdataE = 2;
				else
					pdataE = 1;

				Rotate(b, (byte) (pdataE * pdataS), dirsetto);
				pdataE = 1;
				pdataS = 1;
				return;
			}

			RL=ievent.getAction().equals(Action.RIGHT_CLICK_BLOCK);
			
			//Diodes
			if (((Blocktype == Material.DIODE)||(Blocktype == Material.DIODE_BLOCK_OFF)|(Blocktype == Material.DIODE_BLOCK_ON))&&(RotateThings.EnDiode)){
				if (RL){
					ievent.setCancelled(true);
					if (data % 4 == 3){setD(b, data-3);}else{setD(b, data+1); }
				return;
				}
				return;
			}
			//Rails
			if (((Blocktype == Material.POWERED_RAIL)||(Blocktype == Material.RAILS)|(Blocktype == Material.DETECTOR_RAIL))&&(RotateThings.EnRails)){
				if (RL){
					if (data % 10 == 9){setD(b, data-9);}else{setD(b, data+1); }
				return;
				}else{
					if (data % 10 == 0){setD(b, data+9);}else{setD(b, data-1); }
				}
				return;
			}
			
			//Fence Gates
			if ((bID == 107)&&RotateThings.EnFenceGate){
				int opened = 0;
				if (data >=4){ opened = 4;}
				if (RL){
					ievent.setCancelled(true);
				switch(data % 4){
					case 0:setD(b, 2+opened); break;
					case 1:setD(b, 3+opened); break;
					case 2:setD(b, 1+opened); break;
					case 3:setD(b, 0+opened); break;
					default: log.info("Error during Fence Gate rotation" + data); break;
				}
				}else{
				switch(data % 4){
					case 2:setD(b, 0+opened); break;
					case 3:setD(b, 1+opened); break;
					case 1:setD(b, 2+opened); break;
					case 0:setD(b, 3+opened); break;
					default: log.info("Error during Fence Gate rotation " + data); break;
				}
				}
			}
			
			//Trap Door
			
			if ((bID == 96)&&RotateThings.EnTrapDoor){ 
				int opened = 0;
				if (data >=4){ opened = 4;}
				ievent.setCancelled(true);
				if (RL){
				switch(data % 4){
					case 0:setD(b, 2+opened); break;
					case 1:setD(b, 3+opened); break;
					case 2:setD(b, 1+opened); break;
					case 3:setD(b, 0+opened); break;
					default: log.info("Error during Trap Door rotation" + data); break;
				}
				}else{
				switch(data % 4){
					case 2:setD(b, 0+opened); break;
					case 3:setD(b, 1+opened); break;
					case 1:setD(b, 2+opened); break;
					case 0:setD(b, 3+opened); break;
					default: log.info("Error during Trap Door rotation " + data); break;
				}
				}
			}

			//Doors
			if (((bID == 64)||(bID==71))){ 
				if (((bID==64)&&!(RotateThings.EnWoodDoor))||((bID==71)&&!(RotateThings.EnIronDoor))){return;} //Cancel, if It is wood, and wood is not enabled. Etc.
				ievent.setCancelled(true); //Don't change door open status.
				boolean top;
				Block b2;
				top = (data >= 8);

				if (top){
					b2 = b.getRelative(BlockFace.DOWN);
				}else{
					b2 = b.getRelative(BlockFace.UP);
				}
				
				if((b2.getType().getId()!=64)&&(b2.getType().getId()!=71)){
					return;
				} 
				if (RL){
					if (data % 4 ==3){
						setD(b, data-3);
						setD(b2,8+ data-3);
					}else{
						setD(b, data+1);
						setD(b2, 8+data+1);
					}
				}else{
					if (data % 4 ==0){
						setD(b, data+3);
						setD(b2, 8+data+3);
					}else{
						setD(b, data-1);
						setD(b2, 8+data-1);
					}
				}
			}
			
			//Pumpkins
			if (((Blocktype == Material.PUMPKIN)||(Blocktype == Material.JACK_O_LANTERN))&&RotateThings.EnPumpkins){
				
				if (RL){
					setD(b,((data+3) % 4));

				}else{
					setD(b,((data+1) % 4));
				}
				return;
			}
			
			//Stairs
			if (((Blocktype == Material.COBBLESTONE_STAIRS)||(Blocktype == Material.WOOD_STAIRS)||(bID == 108)||(bID == 109)||(bID == 114))&&RotateThings.EnStairs){
				if (RL){
				switch (data){
				case 0:setD(b, 3); break;
				case 3:setD(b, 1); break;
				case 1:setD(b, 2); break;
				case 2:setD(b, 0); break;
				default: log.info("Error during Stairs rotation"); break;
				}}else{
					switch (data){
					case 0:setD(b, 2); break;
					case 3:setD(b, 0); break;
					case 1:setD(b, 3); break;
					case 2:setD(b, 1); break;
					default: log.info("Error during Stairs rotation"); break;
					}					
				}
				return;
			}
			
			//Furnaces & Dispensers
			if ((Blocktype == Material.FURNACE&&RotateThings.EnFurn)||(Blocktype == Material.BURNING_FURNACE&&RotateThings.EnFurn)||((Blocktype == Material.DISPENSER)&&(RotateThings.EnDisp))){
				if (RL){
					return; 
					}
				else{
					switch (data){
					case 2:setD(b, 5); break;
					case 4:setD(b, 2); break;
					case 3:setD(b, 4); break;
					case 5:setD(b, 3); break;
					default:
						log.info("Error during rotation");
						log.info("Furnace Data = "+ data);
						break;
				}
			}
				return;
			}

			//Levers
			if (Blocktype == Material.LEVER&&RotateThings.EnLever){
					switch (data){
					
					case 5:setD(b, 6); break;
					case 6:setD(b, 13); break;
					case 13:setD(b, 14); break;
					case 14:setD(b, 5); break;
					case 9: break;
					case 1: break;
					default:
						log.info("Error during Lever rotation");
						log.info("Lever Data = "+ data); 

						break;
					}
				return;
			}
			//Dispensers
			if ((Blocktype == Material.DISPENSER)&&(RotateThings.EnDisp)){
				if (RL){
					return; 
					}
				else{
					switch (data){
					case 2:setD(b, 5); break;
					case 4:setD(b, 2); break;
					case 3:setD(b, 4); break;
					case 5:setD(b, 3); break;
					default:
						log.info("Error during Dispenser rotation");
						log.info("Dispenser Data = "+ data);
						break;
				}
			}
				return;
			}
			
		
			//Chests
			if (Blocktype == Material.CHEST&&RotateThings.EnChest){
				if (RL){
					return; 
					}
				else{
					if ((b.getRelative(BlockFace.NORTH).getType()==Material.CHEST)||(b.getRelative(BlockFace.SOUTH).getType()==Material.CHEST)||(b.getRelative(BlockFace.EAST).getType()==Material.CHEST)||(b.getRelative(BlockFace.WEST).getType()==Material.CHEST)){
						if (b.getRelative(BlockFace.NORTH).getType()==Material.CHEST) chd=BlockFace.NORTH;
						else if (b.getRelative(BlockFace.SOUTH).getType()==Material.CHEST) chd=BlockFace.SOUTH;
						else if (b.getRelative(BlockFace.EAST).getType()==Material.CHEST) chd=BlockFace.EAST;
						else if (b.getRelative(BlockFace.WEST).getType()==Material.CHEST) chd=BlockFace.WEST;
					
						switch (data){
						case 2:
							setD(b, 3);
							b.getRelative(chd).setData((byte) 3); break;
						case 3:
							setD(b, 2);
							b.getRelative(chd).setData((byte) 2); break;
						case 4:
							setD(b, 5);
							b.getRelative(chd).setData((byte) 5); break;
						case 5:
							setD(b, 4);
							b.getRelative(chd).setData((byte) 4); break;
						}
					}
					else{
						switch (data){
						case 2:setD(b, 5); break;
						case 4:setD(b, 2); break;
						case 3:setD(b, 4); break;
						case 5:setD(b, 3); break;
						default:
							log.info("Error during Chest rotation");
							log.info("Chest Data = "+ data);
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
			default: RotateThings.log.info("enum default chose"); return BlockFace.DOWN;
		}
	}

	private void Rotate(Block b, byte pdata, byte dirsetto) {
		b.setTypeId(0);
		switch (pdata) {
			case 1: {
				b.setTypeIdAndData(33, dirsetto, false);
			}
				break;
			case 2: {
				b.setTypeIdAndData(33, (byte) (dirsetto + 8), false);
				b.getRelative(convertdirtoface(dirsetto)).setTypeIdAndData(34, dirsetto, false);
			}
				break;
			case 3: {
				b.setTypeIdAndData(29, dirsetto, false);
			}
				break;
			case 6: {
				b.setTypeIdAndData(29, (byte) (dirsetto + 8), false);
				b.getRelative(convertdirtoface(dirsetto)).setTypeIdAndData(34, (byte) (dirsetto + 8), false);
			}
				break;
			default:
				RotateThings.log.info("[RotateThings]: something went wrong in Piston rotation!");
				break;
		}
	}
}
