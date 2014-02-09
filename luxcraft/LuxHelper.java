package luxcraft;

import net.minecraft.block.BlockFurnace;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.DamageSource;

public class LuxHelper {

	public final static String[] color_name = {"White","Red","Green","Blue","Cyan","Yellow","Violet"};
	public final static String[] color_abb = {"W","R","G","B","C","Y","V"};
	public final static String[] color_str = {"\u00a7o","\u00a74","\u00a72","\u00a71","\u00a73","\u00a76","\u00a75"};
	public final static int[] color_int = {0xffffff,0xff0000,0x00ff00,0x0000ff,0x00ffff,0xffff00,0xff00ff};
	public final static double[] r = {1,1,0,0,0,1,1};
	public final static double[] g = {1,0,1,0,1,1,0};
	public final static double[] b = {1,0,0,1,1,0,1};
	private final static int[] dx = {0,0,0,0,1,-1};
	private final  static int[] dy = {1,-1,0,0,0,0};
	private final  static int[] dz = {0,0,1,-1,0,0};
	public static LuxHelper instance = new LuxHelper();
	
	public static DamageSource[] luxDamage = {
			DamageSource.cactus,
			DamageSource.inFire,
			DamageSource.cactus,
			DamageSource.cactus,
			DamageSource.cactus,
			DamageSource.inFire,
			DamageSource.wither
			};
	
	public final static int[][] convRate = {
		{ 1, 3, 3, 3, 3, 3, 3},
		{ 3, 1, 3, 3, 2, 2, 3},
		{ 3, 3, 1, 3, 3, 2, 2},
		{ 3, 3, 3, 1, 2, 3, 2},
		{ 3, 2, 3, 3, 1, 3, 3},
		{ 3, 3, 2, 3, 3, 1, 3},
		{ 3, 3, 3, 2, 3, 3, 1}}; 
	
	public final static byte[][] convOrder = {
		{ 1, 2, 3, 4, 5, 6, 7},
		{ 2, 1, 5, 6, 3, 4, 7},
		{ 3, 1, 6, 7, 2, 4, 5},
		{ 4, 1, 5, 7, 2, 3, 6},
		{ 5, 2, 1, 3, 4, 6, 7},
		{ 6, 3, 1, 2, 4, 5, 7},
		{ 7, 4, 1, 2, 3, 5, 6}}; 	

	public static String display(int amount){
		if (amount % 12 == 0){
			return Integer.toString(amount / 12);
		}else{
			if (amount % 6 == 0)
				return String.format("%.1f", ((float)amount)/12);
			else if (amount % 3 == 0)
				return String.format("%.2f", ((float)amount)/12);
			else
				return String.format("%.3f", ((float)amount)/12);
		}

	}



	public static int extractLux(int amount, byte c, ILuxContainer sender, boolean simulate){
		return sender.extractLux(new LuxPacket(c, amount), simulate).luxLevel[c];
	}

	public static LuxPacket Transfer(LuxPacket packet, ILuxContainer sender, ILuxContainer reciever, boolean simulate){
		LuxPacket insert = new LuxPacket();
		LuxPacket extract = new LuxPacket();
		for(byte c = 0; c < 7; c++){
			insert.luxLevel[c] = Math.min(reciever.MaxLuxLevel(c)-reciever.GetLuxLevel(c, simulate),packet.luxLevel[c]);
			extract.luxLevel[c] = packet.luxLevel[c] - insert.luxLevel[c];
		}

		return reciever.insertLux(sender.extractLux(insert, simulate), simulate).add(extract);
	}	

	//	public static LuxPacket Transfer(LuxPacket packet, ILuxContainer sender, ILuxContainer reciever, boolean simulate){
	//		packet = reciever.insertLux(sender.extractLux(packet, simulate), simulate);
	//		sender.insertLux(packet, simulate);
	//
	//		return packet;
	//	}

	public static LuxPacket Transfer(LuxPacket packet, TileEntity sender, TileEntity reciever, boolean simulate, boolean reDirected){
		LuxPacket newpacket = new LuxPacket();
		if(sender == null | sender instanceof ILuxContainer){
			newpacket = TransferToLuxTileEntity(packet, (ILuxContainer)sender, reciever, simulate, reDirected);
		}
		return newpacket;
	}

	public boolean validTileEntity(TileEntity target){
		return true;
	}

	public static LuxPacket TransferToLuxTileEntity(LuxPacket packet, ILuxContainer sender, TileEntity reciever, boolean simulate, boolean reDirected) {
		if(sender != null){
			if(reciever != null){
				if(reciever == sender)
					return new LuxPacket();
				
				reciever = reciever.worldObj.getBlockTileEntity(reciever.xCoord,reciever.yCoord,reciever.zCoord);

				if (reciever instanceof TileEntityFurnace){
					if(((TileEntityFurnace) reciever).getStackInSlot(0) != null){
						int furnaceMax = 200;
						boolean burning = (((TileEntityFurnace) reciever).furnaceBurnTime > 0); 
						if(((TileEntityFurnace) reciever).furnaceBurnTime < furnaceMax){
							int a = extractLux((furnaceMax-((TileEntityFurnace) reciever).furnaceBurnTime)/6, (byte)1, sender, simulate);
							if(a>0){
								packet.luxLevel[1]-=a;
								((TileEntityFurnace) reciever).furnaceBurnTime+=a*6;
							}else{
								a = extractLux((furnaceMax-((TileEntityFurnace) reciever).furnaceBurnTime)/3, (byte)5, sender, simulate);
								if(a>0){
									packet.luxLevel[5]-=a;
									((TileEntityFurnace) reciever).furnaceBurnTime+=a*3;
								}else{

									a = extractLux((furnaceMax-((TileEntityFurnace) reciever).furnaceBurnTime), (byte)0, sender, simulate);
									packet.luxLevel[0]-=a;
									((TileEntityFurnace) reciever).furnaceBurnTime+=a;
								}
							}
						}

						if(burning != (((TileEntityFurnace) reciever).furnaceBurnTime > 0))
							BlockFurnace.updateFurnaceBlockState(((TileEntityFurnace) reciever).furnaceBurnTime > 0, reciever.worldObj, reciever.xCoord, reciever.yCoord, reciever.zCoord);
					}

				}else if (reciever instanceof TileEntityChest){
					
				}
				else if (reciever instanceof TileEntityLaserDetector){
					((TileEntityLaserDetector)reciever).activate();
				}
				else if (reciever instanceof ILuxContainer){
					if(((ILuxContainer) reciever).canInsert())
						packet = Transfer(packet, (ILuxContainer)sender, (ILuxContainer)reciever, simulate);				
				}
			}else{
				sender.extractLux(packet, simulate);
				packet = new LuxPacket();
			}

		}
		return packet;
	}

	public static boolean validTileEntity(TileEntity t, boolean inserter){
		if(t==null)
			return false;

		if (t instanceof TileEntityFurnace |  t instanceof ILuxContainer)
			return true;

		if(inserter & t instanceof TileEntityLaserDetector)
			return false;


		return false;
	}

}

