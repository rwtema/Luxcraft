package luxcraft;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.liquids.LiquidContainerRegistry;

public class TileEntityLuxDeconstructor extends TileEntityLuxContainer implements ILuxContainer, IInventory {
	private static final int invSize = 33;
	private ItemStack[] invContents = new ItemStack[invSize];
	private ItemStack itemAssembling = null;
	private boolean accurateMax = false;
	public int[] progress = new int[15];
	public int type;


	public LuxPacket getMaxLuxPacket(ItemStack item){
		if(item==null)
			return null;

		for(int i = 15; i < 33; i++){
			ItemStack inv = invContents[i];
			if(inv != null){
				if(inv.itemID == Luxcraft.schema.itemID)
					if(ItemLuxSchema.createdItem(inv).isItemEqual(item)){
						return LuxLevels.instance.GetLuxPacket(item);
					}

				if(inv.itemID == Luxcraft.deconsRecipe.itemID){
					if(item.itemID == inv.getItemDamage()){
						if(ItemLuxDeconsRecipe.createdItem(inv).isItemEqual(item)){
							return ItemLuxDeconsRecipe.getLuxData(inv);
						}
					}
				}
			}

		}

		return null;
	}

	public int getMaxTotalLux(ItemStack item){
		return totalLux(getMaxLuxPacket(item));
	}

	public int getMaxTotalLux(int i){
		return totalLux(getMaxLuxPacket(invContents[i]));
	}

	public int getProgress(int i){
		return progress[i];
	}


	private static int totalLux(LuxPacket t){if(t==null) return 0; else return t.totalLux();}

	public void updateEntity(){
		super.updateEntity();
		if(!this.worldObj.isRemote){
			for(int i =0;i<15;i++){
				LuxPacket packet = getMaxLuxPacket(invContents[i]);
				if(totalLux(packet)>0){
					progress[i]+=12;
					if(progress[i]>totalLux(packet)){
						progress[i]=totalLux(packet);
						
						boolean synth=true;
						for(byte c=0;c<7;c++)
							if(this.GetLuxLevel(c,false)>0)
								if(this.GetLuxLevel(c,false)+packet.luxLevel[c]>this.MaxLuxLevel(c))
									synth=false;
						
						if(synth){
							this.insertLux(packet, false);
							this.decrStackSize(i, 1);
							progress[i]=0;
						}
					}
				}else
					progress[i]=0;
			}

		}

	}

	public boolean canInsert(){
		return false;
	}

	public int getSizeInventory() {
		return invSize;
	}


	public ItemStack getStackInSlot(int i) {
		return invContents[i];
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

	@Override
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
		this.invContents[par1] = par2ItemStack;

		if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
		{
			par2ItemStack.stackSize = this.getInventoryStackLimit();
		}

		this.onInventoryChanged();		
	}


	public String getInvName() {
		return "synthesizer.name";
	}

	public int getInventoryStackLimit() {
		return 64;
	}

	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
		return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
	}

	public void openChest() {
	}

	public void closeChest() {}


	public int MaxLuxLevel(byte color) {
		return 1024*4*8;
	}	

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


		for(int i =0;i < 15;i++){
			if(par1NBTTagCompound.hasKey("Pr"+i))
				progress[i] = par1NBTTagCompound.getInteger("Progress"+i);
			else
				progress[i]=0;
		}

		//insertLimit=getLuxInsertLimit();
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

		for(int i =0;i < 15;i++){
			if(progress[i]!=0)
				par1NBTTagCompound.setInteger("Pr"+i, progress[i]);
		}
	}



}
