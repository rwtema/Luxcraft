package luxcraft;

import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.WorldServer;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class TileEntityLuxContainer extends TileEntity implements ILuxContainer{
	LuxPacket luxStorage = new LuxPacket();
	LuxPacket luxSimulated = new LuxPacket();
	private static int numUpdates=0;
	private boolean sendFullUpdate=false;
	private boolean sendContainerUpdate=false;
	private boolean luxUpdate=false;

	private int flow=0, prev_flow=0;
	private int absflow=0, prev_absflow=0;

	public int GetLuxLevel(byte color) {
		return this.GetLuxLevel(color, false);
	}

	public boolean canInsert(){
		return true;
	}

	public boolean canExtract(){
		return true;
	}

	public int GetLuxLevel(byte color, boolean simulate) {
		if(simulate)
			return luxSimulated.luxLevel[color];
		return luxStorage.luxLevel[color];
	}

	public int MaxLuxLevel(byte color) {
		return 0;
	}

	public void SetLux(int amount, byte color) {
		if(luxStorage.luxLevel[color]!=amount){
			luxStorage.luxLevel[color]=amount;
			onInventoryChanged();
		}
	}

	public void updateLasers(){
		updateLaser(-1,0,0);
		updateLaser(1,0,0);
		updateLaser(0,-1,0);
		updateLaser(0,1,0);
		updateLaser(0,0,1);
		updateLaser(0,0,-1);
	}

	public void updateLaser(int dx, int dy, int dz){
		TileEntity v = this.worldObj.getBlockTileEntity(xCoord+dx, yCoord+dy, zCoord+dz);
		if(v != null){
			if(v instanceof TileEntityLaser){
				//System.out.println("test"+((TileEntityLuxLaser) v).packet.totalLux());
				((TileEntityLaser) v).calcTransmissionLuxPacket();
			}
		}
	}



	public LuxPacket insertLux(LuxPacket packet, boolean simulate) {
		updateLasers();
		if(packet!=null){
			boolean change=false;
			boolean absChange=false;
			for(byte c = 0; c<luxStorage.luxLevel.length;c++){
				int a;
				if(!simulate)
					a = Math.min(packet.luxLevel[c], MaxLuxLevel(c) - luxStorage.luxLevel[c]);
				else
					a = Math.min(packet.luxLevel[c], MaxLuxLevel(c) - luxSimulated.luxLevel[c]);

				if(!simulate & a!=0){
					change=true;

					if(luxStorage.luxLevel[c]==0){
						if(this instanceof TileEntityLuxGenerator){
							if(!((TileEntityLuxGenerator)this).generating)
								absChange=true;
						}else
							absChange=true;
					}
					else if(luxStorage.luxLevel[c] + a == this.MaxLuxLevel(c))
						absChange=true;
				}

				if(!simulate)
					luxStorage.luxLevel[c]+=a;
				else
					luxSimulated.luxLevel[c]+=a;

				flow+=a;absflow+=a;

				packet.luxLevel[c]-=a;
			}

			//			if(absChange) updateAllUsers();
			//			else
			if(change) sendContainerUpdate=true;
		}

		return packet;
	}

	public LuxPacket extractLux(LuxPacket packet, boolean simulate) {
		if(packet!=null){
			boolean change=false;
			boolean absChange=false;
			for(byte c = 0; c<luxStorage.luxLevel.length;c++){
				int a;

				if(!simulate)
					a = Math.min(packet.luxLevel[c], luxStorage.luxLevel[c]);
				else
					a = Math.min(packet.luxLevel[c], luxSimulated.luxLevel[c]);

				if(!simulate & a!=0){
					change=true;

					if(luxStorage.luxLevel[c]==a){
						if(this instanceof TileEntityLuxGenerator){
							if(!((TileEntityLuxGenerator)this).generating)
								absChange=true;
						}else
							absChange=true;
					}
					else if(luxStorage.luxLevel[c] == this.MaxLuxLevel(c))
						absChange=true;
				}
				if(!simulate)
					luxStorage.luxLevel[c]-=a;
				else
					luxSimulated.luxLevel[c]-=a;

				flow-=a;absflow+=a;

				packet.luxLevel[c]=a;
			}
			//			if(absChange) updateAllUsers();
			//			else
			if(change) sendContainerUpdate=true;

		}
		return packet;
	}

	public void updateEntity(){
		if(this.worldObj.isRemote){
			for(byte c = 0;c<7;c++)
				luxSimulated.luxLevel[c] =luxStorage.luxLevel[c];
		}else{
			if(this.worldObj.rand.nextInt(20*60*2)==0)
				this.updateAllUsers();
		}


		if(this.getWorldObj().getWorldTime() % 20 == 1){

//			if(this.worldObj != null){
//				int var1 = 5;
//				List var2 = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)this.xCoord - var1), (double)((float)this.yCoord - var1), (double)((float)this.zCoord - var1), (double)((float)(this.xCoord + 1) + var1), (double)((float)(this.yCoord + 1) + var1), (double)((float)(this.zCoord + 1) + var1)));
//				Iterator var3 = var2.iterator();
//
//				while (var3.hasNext())
//				{
//					EntityPlayer var4 = (EntityPlayer)var3.next();
//
//					if (var4.openContainer instanceof ContainerLuxContainer)
//					{					
//						//((ContainerLuxContainer)var4.openContainer).container;
//						TileEntity var5 = ((ContainerLuxContainer)var4.openContainer).container;
//
//						if(var5.xCoord==xCoord & var5.yCoord==yCoord & var5.zCoord==zCoord){
//							System.out.println("PrevFlow " + prev_flow + " " + prev_absflow);
//							System.out.println("Flow " + flow + " " + absflow);
//						}
//
//					}
//				}
//			}




			if(flow!=prev_flow | prev_absflow!=absflow) this.updateAllUsers();		
			prev_flow=flow;
			prev_absflow=absflow;
			flow=0;
			absflow=0;
		}

		if(sendContainerUpdate){
			updateContainerUsers();
			if(this.worldObj.rand.nextInt(20*10)==0)
				this.updateAllUsers();
			sendContainerUpdate=false;
		}

		if(this.worldObj.getWorldTime()%10 == 0){
			if(numUpdates!=0) System.out.println(numUpdates);
			numUpdates=0;
		}
		
		
		
		
	}


	public void onInventoryChanged()
	{
		updateContainerUsers();
		super.onInventoryChanged();

	}

	public void updateContainerUsers(){
		if(this.worldObj != null){
			int var1 = 5;
			List var2 = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)this.xCoord - var1), (double)((float)this.yCoord - var1), (double)((float)this.zCoord - var1), (double)((float)(this.xCoord + 1) + var1), (double)((float)(this.yCoord + 1) + var1), (double)((float)(this.zCoord + 1) + var1)));
			Iterator var3 = var2.iterator();

			while (var3.hasNext())
			{
				EntityPlayer var4 = (EntityPlayer)var3.next();

				if (var4.openContainer instanceof ContainerLuxContainer)
				{					
					//((ContainerLuxContainer)var4.openContainer).container;
					TileEntity var5 = ((ContainerLuxContainer)var4.openContainer).container;

					if(var5.xCoord==xCoord & var5.yCoord==yCoord & var5.zCoord==zCoord){
						PacketDispatcher.sendPacketToPlayer(getDescriptionPacket(), (Player) var4);
					}

				}
			}
		}
	}

	public void updateAllUsers(){
//		PacketDispatcher.sendPacketToAllInDimension(getDescriptionPacket(), this.worldObj.getWorldInfo().getDimension());
				this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		//PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 32, this.worldObj.getWorldInfo().getDimension(), getDescriptionPacket());

//		if(this.worldObj instanceof WorldServer){
//			int x = this.worldObj.getChunkFromBlockCoords(xCoord, zCoord).xPosition;
//			int z = this.worldObj.getChunkFromBlockCoords(xCoord, zCoord).zPosition;
//			Iterator p = this.worldObj.playerEntities.iterator();
//			while(p.hasNext()){
//				EntityPlayer t =(EntityPlayer)p.next();
//				if(t instanceof EntityPlayerMP){
//					if(((WorldServer) this.worldObj).getPlayerManager().isPlayerWatchingChunk((EntityPlayerMP) t, x, z)){
//						PacketDispatcher.sendPacketToPlayer(getDescriptionPacket(), (Player)t);	
//					}
//
//				}else{
//					System.out.println("err");
//				}
//			}
//		}

	}

	/**
	 * Reads a tile entity from NBT.
	 */
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);
		for (int color = 0; color < luxStorage.luxLevel.length; ++color)
		{
			if(par1NBTTagCompound.hasKey("Lux"+LuxHelper.color_abb[color]))
				luxStorage.luxLevel[color]=par1NBTTagCompound.getInteger("Lux"+LuxHelper.color_abb[color]);
			else
				luxStorage.luxLevel[color]=0;
		}

		for(byte c = 0;c<7;c++)
			luxSimulated.luxLevel[c] = luxStorage.luxLevel[c];
	}


	/**
	 * Writes a tile entity to NBT.
	 */
	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);

		for (int color = 0; color < luxStorage.luxLevel.length; ++color)
		{
			if(luxStorage.luxLevel[color]!=0)
				par1NBTTagCompound.setInteger("Lux"+LuxHelper.color_abb[color],luxStorage.luxLevel[color]);
		}
	}


	public Packet getDescriptionPacket(){
		NBTTagCompound tags = new NBTTagCompound();
		this.writeToNBT(tags);
		return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 0, tags);
	}

	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt)
	{
		numUpdates++;
		this.readFromNBT(pkt.customParam1);
	}



}
