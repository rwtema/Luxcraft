package luxcraft;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class TileEntityLuxContainerWInventory extends TileEntityLuxContainer implements ILuxContainer, IInventory{
	private static final int invSize = 2;
	private ItemStack[] invContents = new ItemStack[invSize];

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

	public String getInvName(){
		return "container";
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
	}




}
