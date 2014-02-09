package luxcraft;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Facing;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;

public class TileEntityItemLaser extends TileEntityLaser {
	protected int range = 12;
	protected int maxLux = 12;
	protected boolean hurtsEntities = false;
	protected ItemStack packet = null;
	int inputSlot = 0;
	private boolean calcPacket=false;
	private int curLaserRange=0;
	private int prevLaserRange=0;


	//public boolean[] beaming = {false,false,false,false,false,false,false};
	private TileEntity inputInv = null;

	public List<LaserBeam> laserBeams = new ArrayList<LaserBeam>();


	boolean init = false;

	private final static int[] dx = {0,0,0,0,1,-1};
	private final static int[] dy = {1,-1,0,0,0,0};
	private final static int[] dz = {0,0,1,-1,0,0};

	public void calcTransmissionLuxPacket(){
		if(!calcPacket)
			packet=getTransmissionItemPacket();
	}

	public ItemStack getTransmissionItemPacket (){
		calcPacket=true;
		int side = Facing.faceToSide[this.worldObj.getBlockMetadata(xCoord, yCoord, zCoord)];
		inputInv = this.worldObj.getBlockTileEntity(xCoord+Facing.offsetsXForSide[side], yCoord+Facing.offsetsYForSide[side], zCoord+Facing.offsetsZForSide[side]);
		if(inputInv != null){
			if (inputInv instanceof IInventory){
				ItemStack packet = null;
				int a = 0, b = ((IInventory) inputInv).getSizeInventory();
				if(inputInv instanceof ISidedInventory){
					a = ((ISidedInventory) inputInv).getStartInventorySide(ForgeDirection.getOrientation(side).getOpposite());
					b = ((ISidedInventory) inputInv).getSizeInventorySide(ForgeDirection.getOrientation(side).getOpposite());
				}
				if(b>0){
					int k = this.worldObj.rand.nextInt(b);
					for(int j = 0; j <= b; j++){
						int i = a + (j + k) % b;
						ItemStack temp = ((IInventory) inputInv).getStackInSlot(i);
						if(temp != null){
							packet = temp.copy();
							packet.stackSize = 1;
							inputSlot = i;
							return packet;
						}
					}
				}
				return packet;
			}else
				return null;
		}
		return null;
	}

	public  List<LaserBeam> getBeams() {
		return laserBeams;
	}

	public void calcLaserPath(){


		//init beams
		if(laserBeams.size()==0){
			laserBeams.add(new LaserBeam(this.worldObj, xCoord, yCoord, zCoord, this.worldObj.getBlockMetadata(xCoord, yCoord, zCoord), 0.9F, 0.4F, 1.0F, 1.0F, 1.0F, 0.1F, 0.8F));
		}

		boolean simulate = this.worldObj.isRemote;

		//clear laser
		laserBeams.get(0).clear();
		laserBeams.get(0).simulating = simulate;

		if(this.worldObj.isRemote){
			if(curLaserRange>1){
				for(int i = 0;i < curLaserRange-1;i++){
					laserBeams.get(0).advance(false);
				}
			}
		}else{
			if(!calcPacket) packet=getTransmissionItemPacket();

			if(packet != null & inputInv != null){
				boolean collission = false;

				for(int strength=0; strength<range; strength++){

					if(!laserBeams.get(0).advance(false)){
						collission=true;
						break;
					}else{
						//check tileentity
						if(this.worldObj.getBlockTileEntity(laserBeams.get(0).x, laserBeams.get(0).y, laserBeams.get(0).z) instanceof IInventory){
							if(!simulate){
								updateLasers(laserBeams.get(0).x, laserBeams.get(0).y, laserBeams.get(0).z);
								if(packet != null)
									packet = this.transfer(packet, (IInventory)inputInv, inputSlot, (IInventory)this.worldObj.getBlockTileEntity(laserBeams.get(0).x, laserBeams.get(0).y, laserBeams.get(0).z), laserBeams.get(0).direction);
							}
							break;
						}

						if (this.worldObj.getBlockTileEntity(laserBeams.get(0).x, laserBeams.get(0).y, laserBeams.get(0).z) instanceof TileEntityLaserDetector){
							((TileEntityLaserDetector)this.worldObj.getBlockTileEntity(laserBeams.get(0).x, laserBeams.get(0).y, laserBeams.get(0).z)).activate();
						}


						if(!simulate){

							//check insertor
							if(this.worldObj.getBlockId(laserBeams.get(0).x, laserBeams.get(0).y, laserBeams.get(0).z) == Luxcraft.luxInsertor.blockID){

								for(int i = 0; i < 6;i+=1){

									TileEntity t = this.worldObj.getBlockTileEntity(laserBeams.get(0).x+dx[i], laserBeams.get(0).y+dy[i], laserBeams.get(0).z+dz[i]);
									if(t instanceof IInventory){

										updateLasers(laserBeams.get(0).x+dx[i], laserBeams.get(0).y+dy[i], laserBeams.get(0).z+dz[i]);
										if(packet != null)
											packet = this.transfer(packet, (IInventory)inputInv, inputSlot, (IInventory)t, Facing.faceToSide[i]);
									}
								}

							}

							if(packet == null) break;
						}
					}
				}

				//drop remaining item stack
				if(!simulate & packet != null & laserBeams.get(0).size() >= 2){
					EntityItem drop;
					int i = laserBeams.get(0).size()-1;
					if(collission)
						drop = new EntityItem(worldObj, (laserBeams.get(0).pathx.get(i)+laserBeams.get(0).pathx.get(i-1)*2)/3, (laserBeams.get(0).pathy.get(i)+laserBeams.get(0).pathy.get(i-1)*2)/3, (laserBeams.get(0).pathz.get(i)+laserBeams.get(0).pathz.get(i-1)*2)/3, packet);
					else
						drop = new EntityItem(worldObj, laserBeams.get(0).pathx.get(i), laserBeams.get(0).pathy.get(i), laserBeams.get(0).pathz.get(i), packet);
					float var15 = 0.01F;
					drop.motionX = (double)((float)this.worldObj.rand.nextGaussian() * var15);
					drop.motionY = (double)((float)this.worldObj.rand.nextGaussian() * var15);
					drop.motionZ = (double)((float)this.worldObj.rand.nextGaussian() * var15);

					if (packet.hasTagCompound())
					{
						drop.getEntityItem().setTagCompound((NBTTagCompound)packet.getTagCompound().copy());
					}
					worldObj.spawnEntityInWorld(drop);

					if(inputInv instanceof IInventory)
						((IInventory)inputInv).decrStackSize(inputSlot, packet.stackSize);

				}
			}

			if(curLaserRange!=laserBeams.get(0).size()){
				curLaserRange=laserBeams.get(0).size();
				this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}

		}
	}

	public ItemStack transfer(ItemStack packet, IInventory inputInv, int inputSlot, IInventory outputInv, int outputSide){
		//inserting
		//ItemStack packet = inputInv.getStackInSlot(inputSlot);
		if(this.worldObj.isRemote)
			return null;

		if(inputInv == outputInv)
			return null;

		if(inputInv != null  & packet != null & outputInv != null){
			int a = 0, b = outputInv.getSizeInventory();
			if(outputInv instanceof ISidedInventory){
				a = ((ISidedInventory) outputInv).getStartInventorySide(ForgeDirection.getOrientation(outputSide).getOpposite());
				b = a + ((ISidedInventory) outputInv).getSizeInventorySide(ForgeDirection.getOrientation(outputSide).getOpposite());
			}

			for(int i = a; i < b; i++){
				ItemStack target = outputInv.getStackInSlot(i);

				int prevStackSize = 0; boolean reduce=false;

				if(target==null){
					outputInv.setInventorySlotContents(i, packet);
					if(outputInv.getStackInSlot(i) != null){
						if(outputInv.getStackInSlot(i).stackSize>0)
							inputInv.decrStackSize(inputSlot, outputInv.getStackInSlot(i).stackSize);
						return null;
					}
				}else{
					if(packet.isItemEqual(target)){
						int m = Math.min(Math.min(outputInv.getInventoryStackLimit(), target.getMaxStackSize()) - target.stackSize, packet.stackSize);
						if(m>0){
							prevStackSize = target.stackSize;
							reduce=true;
							target.stackSize+=m;

							outputInv.setInventorySlotContents(i, target);
							
							if(outputInv.getStackInSlot(i)!=null){
								int n = outputInv.getStackInSlot(i).stackSize - prevStackSize;
								if(n>0){
									inputInv.decrStackSize(inputSlot, n);
									packet.stackSize-=n;
								}
							}
						}
					}

				
				}

				if(packet.stackSize<=0) return null;
			}
		}
		return packet;
	}

	public void updateLasers(int x, int y, int z){
		updateLaser(x-1,y,z);
		updateLaser(x+1,y,z);
		updateLaser(x,y-1,z);
		updateLaser(x,y+1,z);
		updateLaser(x,y,z+1);
		updateLaser(x,y,z-1);
	}

	public void updateLaser(int x, int y, int z){
		TileEntity v = this.worldObj.getBlockTileEntity(x, y, z);
		if(v != null){
			if(v instanceof TileEntityLaser){
				((TileEntityLaser) v).calcTransmissionLuxPacket();
			}
		}
	}

	public void updateEntity() {
		if(!init){
			init = true;
			if(this.worldObj.isRemote)
				calcLaserPath();
		}

		if(this.worldObj.getWorldTime() % 20==0){
			calcLaserPath();
			init = true;
		}else{
			calcPacket=false;
		}
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);

		if(par1NBTTagCompound.hasKey("r"))
			curLaserRange=par1NBTTagCompound.getByte("r");
	}


	/**
	 * Writes a tile entity to NBT.
	 */
	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setByte("r", (byte)curLaserRange);

	}


	public Packet getDescriptionPacket(){
		NBTTagCompound tags = new NBTTagCompound();
		this.writeToNBT(tags);
		return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 0, tags);
	}

	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt)
	{
		this.readFromNBT(pkt.customParam1);
	}
}
