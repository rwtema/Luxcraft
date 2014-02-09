package luxcraft;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Facing;
import net.minecraft.world.ChunkPosition;

public class TileEntityLuxLaser extends TileEntityLaser {
	protected int range = 12;
	protected int maxLux = 12;
	protected boolean hurtsEntities = false;
	protected LuxPacket packet = null;
	protected int damage = 0;

	//public boolean[] beaming = {false,false,false,false,false,false,false};
	private TileEntity inputInv = null;

	boolean init = false;

	private final static int[] dx = {0,0,0,0,1,-1};
	private final static int[] dy = {1,-1,0,0,0,0};
	private final static int[] dz = {0,0,1,-1,0,0};


	public TileEntityLuxLaser(){
		super();
		setPropertiesFromType(type());
	}

	public byte type(){
		if(this.worldObj!=null){
			int id = this.worldObj.getBlockId(xCoord, yCoord, zCoord);
			if(id == Luxcraft.luxLaser.blockID)
				return 0;
			else if(id == Luxcraft.luxLaser2.blockID)
				return 1;
		}
		return 0;
	}

	public TileEntityLuxLaser(byte type){
		super();
		setPropertiesFromType(type);
	}

	public void setPropertiesFromType(byte type){
		switch(type){
		case 0:
			range=32;
			maxLux=12;
			hurtsEntities = false;
			break;
		case 1:
			range=16;
			maxLux=12*64;
			hurtsEntities = true;
			break;
		}
	}

	public void calcTransmissionLuxPacket(){
		if(packet==null)
			packet=getTransmissionLuxPacket();
	}

	public LuxPacket getTransmissionLuxPacket (){
		int side = Facing.faceToSide[this.worldObj.getBlockMetadata(xCoord, yCoord, zCoord)];
		inputInv = this.worldObj.getBlockTileEntity(xCoord+Facing.offsetsXForSide[side], yCoord+Facing.offsetsYForSide[side], zCoord+Facing.offsetsZForSide[side]);
		if(inputInv != null){
			if (inputInv instanceof ILuxContainer){
				LuxPacket packet = new LuxPacket();
				if(((ILuxContainer) inputInv).canExtract()){
					for(byte col=0;col<7;col+=1){
						packet.luxLevel[col] = Math.min(((ILuxContainer) inputInv).GetLuxLevel(col, this.worldObj.isRemote), maxLux);
					}
					if(this.worldObj.isRemote)
						if(inputInv instanceof TileEntityLuxGenerator){
							byte c = ((TileEntityLuxGenerator) inputInv).generatingColor();
							if(packet.luxLevel[c] == 0)
								packet.luxLevel[c] = ((TileEntityLuxGenerator) inputInv).genLevel;
							((TileEntityLuxGenerator) inputInv).genLevel=0;
						}
				}
				return packet;
			}else
				return new LuxPacket();
		}
		return new LuxPacket();
	}

	public void calcLaserPath(){
		if(packet==null)
			packet=getTransmissionLuxPacket();

		//init beams
		if(laserBeams.size()==0){
			for(byte c = 0;c < 7;c++)
				switch(this.type()){
				case 1:
					damage=4;
					laserBeams.add(new LaserBeam(this.worldObj, xCoord, yCoord, zCoord, this.worldObj.getBlockMetadata(xCoord, yCoord, zCoord), (float)LuxHelper.r[c], (float)LuxHelper.g[c], (float)LuxHelper.b[c], 0.125F, 3.0F, 0.1F, 0.5F));
					break;
				case 0:
				default:
					damage=0;
					laserBeams.add(new LaserBeam(this.worldObj, xCoord, yCoord, zCoord, this.worldObj.getBlockMetadata(xCoord, yCoord, zCoord), (float)LuxHelper.r[c], (float)LuxHelper.g[c], (float)LuxHelper.b[c], 0.125F, 1.5F, 0.05F, 0.9F));
					break;
				}
		}

		boolean simulate = this.worldObj.isRemote;

		for(byte c = 0;c < 7;c++){
			laserBeams.get(c).clear();
			laserBeams.get(c).simulating = simulate;

			LuxPacket colPacket = packet.col(c);

			if(inputInv != null & !colPacket.isEmpty()){
				laserBeams.get(c).advance(true);

				for(int strength=0; strength<range; strength++){
					//damage entities
					if(!this.worldObj.isRemote & !simulate)
						if(laserBeams.get(c).collidedEntities!=null){
							for(int i = 0;i < laserBeams.get(c).collidedEntities.size();i++){
								((EntityLiving)laserBeams.get(c).collidedEntities.get(i)).attackEntityFrom(LuxHelper.luxDamage[i], damage);
								if(damage != 0 & LuxHelper.luxDamage[i].isFireDamage())
									((EntityLiving)laserBeams.get(c).collidedEntities.get(i)).setFire(1);
								if(damage != 0 & LuxHelper.luxDamage[i]==DamageSource.wither)
									((EntityLiving)laserBeams.get(c).collidedEntities.get(i)).addPotionEffect(new PotionEffect(Potion.wither.id, 50));
							}
						}

					//check tileentity
					if(this.worldObj.getBlockTileEntity(laserBeams.get(c).x, laserBeams.get(c).y, laserBeams.get(c).z)!=null){
						colPacket = LuxHelper.instance.Transfer(colPacket, inputInv, this.worldObj.getBlockTileEntity(laserBeams.get(c).x, laserBeams.get(c).y, laserBeams.get(c).z), simulate, false);
					}

					//check insertor
					if(this.worldObj.getBlockId(laserBeams.get(c).x, laserBeams.get(c).y, laserBeams.get(c).z) == Luxcraft.luxInsertorId){
						boolean[] inserted = new boolean[6];
						int prevLux = 0;
						int j;
						for (j = 0; j < 100 & prevLux != colPacket.totalLux() & colPacket.totalLux()>0;j++){
							prevLux = colPacket.totalLux();
							LuxPacket tempPacket = colPacket.div(6);

							for(int i = 0; i < 6;i+=1){
								TileEntity t = this.worldObj.getBlockTileEntity(laserBeams.get(c).x+dx[i], laserBeams.get(c).y+dy[i], laserBeams.get(c).z+dz[i]);
								if(t != null)
									if(LuxHelper.validTileEntity(t,true)){
										int prevt = colPacket.totalLux();
										colPacket = colPacket.subt_to_zero(tempPacket).add(LuxHelper.instance.Transfer(tempPacket, inputInv, t, simulate, true));
										if(colPacket.totalLux()<prevt)
											inserted[i]=true;
									}
							}

						}
						if(j==100)
							System.out.println("insertion error");
					}

					if(colPacket.isEmpty())
						break;
					else{
						if(!laserBeams.get(c).advance(false))
							break;
					}
				}

				//dump remaining lux
				if(!colPacket.isEmpty()){
					colPacket = LuxHelper.instance.Transfer(colPacket, inputInv, this.worldObj.getBlockTileEntity(laserBeams.get(c).x, laserBeams.get(c).y, laserBeams.get(c).z), simulate, false);
					colPacket = LuxHelper.instance.Transfer(colPacket, inputInv, null, simulate, false);
				}
			}


		}
	}


	public void updateEntity() {
		if(!init){
			this.setPropertiesFromType(type());
			init = true;
			if(this.worldObj.isRemote)
				calcLaserPath();
		}

		if(this.worldObj.getWorldTime() % 20==0){
			calcLaserPath();
			init = true;
		}else{
			//			if(this.worldObj.isRemote & this.worldObj.getWorldTime() % 2 == 0){
			//				calcLaserPath();
			//				packet=null;
			//				init=true;
			//			}
			packet=null;
		}
		//	}
	}


}
