package luxcraft;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class TileEntityLuxStorage extends TileEntityLuxContainer implements ILuxContainer{
	private static final int[] dx = {0,0,1,-1,0,0};
	private static final int[] dy = {1,-1,0,0,0,0};
	private static final int[] dz = {0,0,0,0,1,-1};
	public int MaxLuxLevel(byte color) {
		return 1024*12*1;
	}

	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
		return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
	}

	public void updateEntity(){
		super.updateEntity();

		if(!this.worldObj.isRemote){
		for(int i = 0; i < 6;i++){
			TileEntity t = this.worldObj.getBlockTileEntity(xCoord+dx[i], yCoord+dy[i], zCoord+dz[i]);
			if(t instanceof TileEntityLuxStorage){
				
				LuxPacket packet = new LuxPacket();
				for(byte c = 0; c < 7;c++){
					int d = this.GetLuxLevel(c, false) - ((TileEntityLuxStorage) t).GetLuxLevel(c, false);
					if(d>8*12) d=8*12;
					if(d>0){
						packet.luxLevel[c]=d/2;
					}
				}

				if(!packet.isEmpty()){
					LuxHelper.Transfer(packet, this, t, false, false);
				}
			}			

		}
		}
	}

}
