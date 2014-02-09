package luxcraft;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

public class TileEntityLuxDetector extends TileEntity {
	public int luxDetectedTimer = 0;

	public void updateEntity(){
		if(luxDetectedTimer>0){
			if(!this.worldObj.isRemote & this.worldObj.getWorldTime() % 20 == 1){
				luxDetectedTimer-=1;
				if(luxDetectedTimer == 0){
					this.worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, this.getBlockType().blockID);
				}
			}
		}
	}

	public void activate() {
		if(luxDetectedTimer==0){
			luxDetectedTimer=2;
			this.worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, this.getBlockType().blockID);
		}else
			luxDetectedTimer=2;
	}

	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);
		luxDetectedTimer = par1NBTTagCompound.getByte("Timer");
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setByte("Timer", (byte)luxDetectedTimer);
	}

	public Packet getDescriptionPacket(){return null;}
	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt){}


}
