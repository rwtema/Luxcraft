package luxcraft;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.ChunkPosition;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;

public class TileEntityLuxRitualTable extends TileEntity implements ISidedInventory
{
	private ItemStack itemsStack = null;

	private final static int ritual_maxn = 8;

	private int ritual_strength = 0;
	private int ritual_maxstrength = 0;


	public int schemaAnalyseTime = 0;
	public int schemaMaxAnalyseTime = 0;

	/**
	 * Returns the number of slots in the inventory.
	 */
	public int getSizeInventory()
	{
		return 1;
	}

	/**
	 * Returns the stack in slot i
	 */
	public ItemStack getStackInSlot(int par1)
	{
		if(par1 == 0)
			return this.itemsStack;
		else
			return null;
	}

	/**
	 * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
	 * new stack.
	 */
	public ItemStack decrStackSize(int par1, int par2)
	{
		if(par1 != 0)
			return null;

		if (this.itemsStack != null)
		{
			ItemStack var3;

			if (this.itemsStack.stackSize <= par2)
			{
				var3 = this.itemsStack;
				this.itemsStack = null;
				return var3;
			}
			else
			{
				var3 = this.itemsStack.splitStack(par2);

				if (this.itemsStack.stackSize == 0)
				{
					this.itemsStack = null;
				}

				return var3;
			}
		}
		else
		{
			return null;
		}
	}

	/**
	 * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
	 * like when you close a workbench GUI.
	 */
	public ItemStack getStackInSlotOnClosing(int par1)
	{
		if (par1 == 0 & this.itemsStack != null)
		{
			ItemStack var2 = this.itemsStack;
			this.itemsStack = null;
			return var2;
		}
		else
		{
			return null;
		}
	}

	/**
	 * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
	 */
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		if(par1==0){
			this.itemsStack = par2ItemStack;

			if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
			{
				par2ItemStack.stackSize = this.getInventoryStackLimit();
			}

			this.schemaAnalyseTime=0;
		}
	}

	/**
	 * Returns the name of the inventory.
	 */
	public String getInvName()
	{
		return "container.ritualTable";
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);


		NBTTagList var2 = par1NBTTagCompound.getTagList("Items");
		this.itemsStack = null;

		for (int var3 = 0; var3 < var2.tagCount(); ++var3)
		{
			NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
			byte var5 = var4.getByte("Slot");

			if (var5 == 0)
			{
				this.itemsStack = ItemStack.loadItemStackFromNBT(var4);
			}
		}


		this.schemaAnalyseTime = par1NBTTagCompound.getShort("CookTime");
		this.ritual_strength = par1NBTTagCompound.getShort("RitualStrength");
		this.ritual_maxstrength = par1NBTTagCompound.getShort("RitualMaxStrength");

		schemaMaxAnalyseTime=0;
		if (this.itemsStack != null)
			if(this.itemsStack.itemID  != Luxcraft.schemaId){
				LuxPacket t = LuxLevels.instance.GetLuxPacket(this.itemsStack);

				if(t!=null)
					schemaMaxAnalyseTime=t.totalLux()*2;
			}
	}

	public Packet getDescriptionPacket(){
		NBTTagCompound tags = new NBTTagCompound();
		writeToNBT(tags);
		return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 0, tags);
	}

	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt)
	{
		readFromNBT(pkt.customParam1);
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);

		par1NBTTagCompound.setShort("CookTime", (short)this.schemaAnalyseTime);
		par1NBTTagCompound.setShort("RitualStrength", (short)this.ritual_strength);
		par1NBTTagCompound.setShort("RitualMaxStrength", (short)this.ritual_maxstrength);

		NBTTagList var2 = new NBTTagList();

		int var3 = 0;

		if (this.itemsStack != null)
		{
			NBTTagCompound var4 = new NBTTagCompound();
			var4.setByte("Slot", (byte)var3);
			this.itemsStack.writeToNBT(var4);
			var2.appendTag(var4);
		}


		par1NBTTagCompound.setTag("Items", var2);
	}

	/**
	 * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
	 * this more of a set than a get?*
	 */
	public int getInventoryStackLimit()
	{
		return 1;
	}

	/**
	 * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
	 * ticks and creates a new spawn inside its implementation.
	 */
	public void updateEntity()
	{
		if (!this.worldObj.isRemote)
		{
			if (this.itemsStack != null)
			{
				if(this.itemsStack.itemID  != Luxcraft.schemaId){
					LuxPacket t = LuxLevels.instance.GetLuxPacket(this.itemsStack);

					if(t!=null){
						schemaMaxAnalyseTime=t.totalLux()*2;

						if(schemaMaxAnalyseTime>0){
							if(schemaAnalyseTime >= schemaMaxAnalyseTime){
								schemaAnalyseTime=0;
								schemaMaxAnalyseTime=0;
								createSchema();
							}else
								schemaAnalyseTime+=getRitualSpeed();
						}
					}
					else{
						schemaAnalyseTime=0;schemaMaxAnalyseTime=0;
					}
				}else{
					schemaAnalyseTime=0;schemaMaxAnalyseTime=0;
				}
			}
			else
			{
				schemaAnalyseTime=0;schemaMaxAnalyseTime=0;
			}


			if(this.worldObj.rand.nextInt(100)==0){
				calcRitualStrength();
			}

		}

	}



	public void createSchema(){
		this.itemsStack = ItemLuxSchema.newSchema(this.itemsStack);
		this.onInventoryChanged();

	}

	/**
	 * Called when an the contents of an Inventory change, usually
	 */
	public void onInventoryChanged()
	{
		super.onInventoryChanged();
		this.schemaAnalyseTime=0;
		this.worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, this.getBlockType().blockID);
	}


	//Ritual Pattern Strength
	public void calcRitualStrength()
	{
		int n = ritual_maxn;
		int[] ax = {1, 0, -1, 0};
		int[] az = {0, 1, 0, -1};
		List<ChunkPosition> redstone_pattern = new ArrayList<ChunkPosition>();
		List<ChunkPosition> string_pattern = new ArrayList<ChunkPosition>();
		redstone_pattern.add(new ChunkPosition(xCoord, yCoord, zCoord));
		string_pattern.add(new ChunkPosition(xCoord, yCoord, zCoord));
		int redstone_id = Block.redstoneWire.blockID;
		int string_id = Block.tripWire.blockID;
		int min_x = 0, max_x = 0, min_z = 0, max_z = 0;

		for (int ri = 0; ri < redstone_pattern.size(); ri += 1)
			for (int i = 0; i < 4; i += 1)
			{
				ChunkPosition temp = new ChunkPosition(((ChunkPosition) redstone_pattern.get(ri)).x + ax[i], yCoord, ((ChunkPosition) redstone_pattern.get(ri)).z + az[i]);

				if (Math.abs(temp.x - xCoord) <= n & Math.abs(temp.z - zCoord) <= n)
					if (this.getWorldObj().getBlockId(temp.x, temp.y, temp.z) == redstone_id)
						if (!redstone_pattern.contains(temp))
						{
							max_x = Math.max(max_x, xCoord - temp.x);
							min_x = Math.min(min_x, xCoord - temp.x);
							max_z = Math.max(max_z, zCoord - temp.z);
							min_z = Math.min(min_z, zCoord - temp.z);
							redstone_pattern.add(temp);
						}
			}

		for (int si = 0; si < string_pattern.size(); si += 1)
			for (int i = 0; i < 4; i += 1)
			{
				ChunkPosition temp = new ChunkPosition(((ChunkPosition) string_pattern.get(si)).x + ax[i], yCoord, ((ChunkPosition) string_pattern.get(si)).z + az[i]);

				if (Math.abs(temp.x - xCoord) <= n & Math.abs(temp.z - zCoord) <= n)
					if (this.getWorldObj().getBlockId(temp.x, temp.y, temp.z) == string_id)
						if (!string_pattern.contains(temp))
						{
							max_x = Math.max(max_x, xCoord - temp.x);
							min_x = Math.min(min_x, xCoord - temp.x);
							max_z = Math.max(max_z, zCoord - temp.z);
							min_z = Math.min(min_z, zCoord - temp.z);
							string_pattern.add(temp);
						}
			}

		List<ChunkPosition> checked = new ArrayList<ChunkPosition>();
		int r_size = 0;

		for (int ri = 1; ri < redstone_pattern.size(); ri += 1)
			for (int i = 0; i < 4; i += 1)
			{
				ChunkPosition temp = new ChunkPosition(((ChunkPosition) redstone_pattern.get(ri)).x + ax[i], yCoord, ((ChunkPosition) redstone_pattern.get(ri)).z + az[i]);

				if (this.getWorldObj().getBlockId(temp.x, temp.y, temp.z) == string_id)
					if (!checked.contains(temp) & string_pattern.contains(temp))
					{
						r_size += 1;
					}
			}

		boolean changed = false;
		if(ritual_strength != r_size) changed=true;
		ritual_strength = r_size;
		//ritual_maxstrength = (max_x - min_x) * (max_z - min_z);
		int m = Math.max(Math.max(Math.abs(max_x), Math.abs(min_x)), Math.max(Math.abs(max_z), Math.abs(min_z)));
		if(ritual_maxstrength != 4 * m * m) changed=true;
		ritual_maxstrength = 4 * m * m;

		if(changed) {
			this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			this.worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, this.getBlockType().blockID);
		}

	}

	public int getRitualStrength()
	{
		return ritual_strength;
	}

	public int getMaxRitualStrength()
	{
		return ritual_maxstrength;
	}    


	public int getRitualSpeed(){
		if (ritual_strength == ritual_maxstrength)
		{
			return 1+((int)Math.sqrt(ritual_strength));
		}
		return 1;
	}

	public boolean isPowered(){
		return ritual_maxstrength>0 & ritual_strength == ritual_maxstrength;
	}

	/**
	 * Do not make give this method the name canInteractWith because it clashes with Container
	 */
	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
	{
		return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
	}

	public void openChest() {}

	public void closeChest() {}

	@Override
	public int getStartInventorySide(ForgeDirection side) {
		return 0;
	}

	@Override
	public int getSizeInventorySide(ForgeDirection side) {		
		if(itemsStack != null){
			if(itemsStack.itemID == Luxcraft.schema.itemID){
				return side == ForgeDirection.DOWN ? 1 : 0;
			}
		}
		
		return side == ForgeDirection.UP? 1 : 0;
	}
}
