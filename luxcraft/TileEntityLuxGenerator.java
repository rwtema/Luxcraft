package luxcraft;

import net.minecraft.block.Block;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.IPlantable;

public class TileEntityLuxGenerator extends TileEntityLuxContainer implements IInventory, ILuxContainer {
	private ItemStack[] invContents = new ItemStack[9];
	int genLevel = 0;
	boolean generating = false;

	public int MaxLuxLevel(byte color) {
		if(color==generatingColor())
			return 128*12;
		return 0;
	}

	public byte generatingColor(){
		return (byte)(this.worldObj.getBlockMetadata(xCoord, yCoord, zCoord) & 7);
	}

	public void updateEntity(){
		super.updateEntity();
		//		if(!this.worldObj.isRemote){
		if(this.worldObj.getWorldTime() % 20==1){
			byte c = generatingColor();

			if(this.GetLuxLevel(c)<this.MaxLuxLevel(c)){
				for(int i = 0;i < this.invContents.length;i++){
					if(this.invContents[i] != null){
						LuxPacket temp = LuxLevels.instance.GetLuxPacket(this.invContents[i]);
						if(temp != null){
							if(temp.luxLevel[this.generatingColor()]>0 & !temp.isEmpty()){
								if(this.GetLuxLevel(this.generatingColor())==0 | this.GetLuxLevel(this.generatingColor())+temp.luxLevel[this.generatingColor()]<=this.MaxLuxLevel(this.generatingColor())){
									this.decrStackSize(i, 1);
									this.insertLux(new LuxPacket(c, temp.luxLevel[this.generatingColor()]), this.worldObj.isRemote);
									this.onInventoryChanged();
								}
							}

						}
					}
				}
			}
			GeneratePassiveLux();
		}

	}

	public int getSizeInventory() {
		return invContents.length;
	}


	public ItemStack getStackInSlot(int var1) {
		return this.invContents[var1];
	}


	public ItemStack decrStackSize(int par1, int par2) {
		if (this.invContents[par1] != null)
		{
			ItemStack var3;

			if (this.invContents[par1].stackSize <= par2)
			{
				var3 = this.invContents[par1];
				this.invContents[par1] = null;
				this.onInventoryChanged();
				return var3;
			}
			else
			{
				var3 = this.invContents[par1].splitStack(par2);

				if (this.invContents[par1].stackSize == 0)
				{
					this.invContents[par1] = null;
				}

				this.onInventoryChanged();
				return var3;
			}
		}
		else
		{
			return null;
		}
	}


	public ItemStack getStackInSlotOnClosing(int par1) {
		if (this.invContents[par1] != null)
		{
			ItemStack var2 = this.invContents[par1];
			this.invContents[par1] = null;
			return var2;
		}
		else
		{
			return null;
		}
	}


	public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
		this.invContents[par1] = par2ItemStack;

		if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
		{
			par2ItemStack.stackSize = this.getInventoryStackLimit();
		}

		this.onInventoryChanged();
	}


	public String getInvName() {
		return "Lux.Generator";
	}


	public int getInventoryStackLimit() {
		return 64;
	}


	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
		return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
	}

	public void openChest() {}

	public void closeChest() {}

	/**
	 * Reads a tile entity from NBT.
	 */
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);
		NBTTagList var2 = par1NBTTagCompound.getTagList("Items");
		this.invContents = new ItemStack[this.getSizeInventory()];

		for (int var3 = 0; var3 < var2.tagCount(); ++var3)
		{
			NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
			int var5 = var4.getByte("Slot") & 255;

			if (var5 >= 0 && var5 < this.invContents.length)
			{
				this.invContents[var5] = ItemStack.loadItemStackFromNBT(var4);
			}
		}
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
		NBTTagList var2 = new NBTTagList();

		for (int var3 = 0; var3 < this.invContents.length; ++var3)
		{
			if (this.invContents[var3] != null)
			{
				NBTTagCompound var4 = new NBTTagCompound();
				var4.setByte("Slot", (byte)var3);
				this.invContents[var3].writeToNBT(var4);
				var2.appendTag(var4);
			}
		}

		par1NBTTagCompound.setTag("Items", var2);
	}


	public void GeneratePassiveLux(){
		genLevel=0;
		generating=false;
		boolean simulate = this.worldObj.isRemote;
		
		if(generatingColor()==0){	//White
			boolean hasLava=false, hasWater=false;
			for(int dx=-1;dx<=1&(!hasWater|!hasLava);dx++){
				for(int dy=-1;dy<=1&(!hasWater|!hasLava);dy++){
					for(int dz=-1;dz<=1&(!hasWater|!hasLava);dz++){
						if(this.worldObj.getBlockId(xCoord+dx, yCoord+dy, zCoord+dz) == Block.lavaStill.blockID)
							hasLava=true;
						if(this.worldObj.getBlockId(xCoord+dx, yCoord+dy, zCoord+dz) == Block.waterStill.blockID)
							hasWater=true;
					}
				}
			}


			if(hasWater & hasLava)
				genLevel=12;
		}

		if(generatingColor()==1){	//Red
			for(int dx=-1;dx<=1;dx++){
				for(int dy=-1;dy<=1;dy++){
					for(int dz=-1;dz<=1;dz++){
						if(this.worldObj.getBlockId(xCoord+dx, yCoord+dy, zCoord+dz) == Block.fire.blockID){
							genLevel=12;
						}
					}
				}
			}
			
		}		

		if(generatingColor()==2){	//Green
			for(int dx=-1;dx<=1;dx++){
				for(int dy=-1;dy<=1;dy++){
					for(int dz=-1;dz<=1;dz++){
						if(Block.blocksList[this.worldObj.getBlockId(xCoord+dx, yCoord+dy, zCoord+dz)] instanceof IPlantable){
							genLevel=12;
						}
					}
				}
			}
			
		}	

		if(generatingColor()==3){	//Blue
			int j = 0;
			for(int dx=-1;dx<=1;dx++){
				for(int dy=-1;dy<=1;dy++){
					for(int dz=-1;dz<=1;dz++){
						if(this.worldObj.getBlockId(xCoord+dx, yCoord+dy, zCoord+dz) == Block.waterStill.blockID){
							j=j+1;
							if(j<2){
								genLevel=12;
								break;
							}
						}
					}
				}
			}
		}

		if(generatingColor()==4){	//Cyan
			AxisAlignedBB  t = AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(xCoord, yCoord, zCoord, xCoord+1, yCoord+1, zCoord+1).expand(5, 5, 5);
			if(this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, t).size()>0)
				genLevel=12;
			else if(this.worldObj.getEntitiesWithinAABB(EntityWolf.class, t).size()>0)
				genLevel=12;
			else if(this.worldObj.getEntitiesWithinAABB(EntityOcelot.class, t).size()>0)
				genLevel=12;
			
		}

		if(generatingColor()==5){	//Yellow
//			if(this.worldObj.getWorldTime() % 24000 < 12000 & !this.worldObj.isRaining()){
			if(this.worldObj.isDaytime() & !this.worldObj.isRaining()){
				if(getTopBlock(xCoord,zCoord)==yCoord){
					genLevel=12;
				}
			}
		}

		if(generatingColor()==6)	//Violet
			for(int dx=-1;dx<=1;dx++)
				for(int dy=-1;dy<=1;dy++)
					for(int dz=-1;dz<=1;dz++)
						if(this.worldObj.getBlockId(xCoord+dx, yCoord+dy, zCoord+dz) == Block.mobSpawner.blockID){
							genLevel=12;
						}


		if(genLevel!=0){
			generating=true;
			this.insertLux(new LuxPacket(generatingColor(), genLevel), simulate);
		}

	}

	/**
	 * Finds the highest block on the x, z coordinate that is solid and returns its y coord. Args x, z
	 */
	public int getTopBlock(int par1, int par2)
	{
		Chunk var3 = worldObj.getChunkFromBlockCoords(par1, par2);
		int var4 = var3.getTopFilledSegment() + 15;
		par1 &= 15;

		for (par2 &= 15; var4 > 0; --var4)
		{
			int var5 = var3.getBlockID(par1, var4, par2);

			if (var5 != 0)
			{
				return var4;
			}
		}

		return -1;
	}
}
