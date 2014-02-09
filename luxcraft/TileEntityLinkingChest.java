package luxcraft;

import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityLinkingChest extends TileEntity implements IInventory
{
	private ItemStack[] chestContents = new ItemStack[36];

	private int hotBarSize = 9;

	/** Determines if the check for adjacent chests has taken place. */
	//    public boolean adjacentChestChecked = false;

	//    /** Contains the chest tile located adjacent to this one (if any) */
	//    public TileEntityInventoryChest adjacentChestZNeg;
	//
	//    /** Contains the chest tile located adjacent to this one (if any) */
	//    public TileEntityInventoryChest adjacentChestXPos;
	//
	//    /** Contains the chest tile located adjacent to this one (if any) */
	//    public TileEntityInventoryChest adjacentChestXNeg;
	//
	//    /** Contains the chest tile located adjacent to this one (if any) */
	//    public TileEntityInventoryChest adjacentChestZPosition;

	/** The current angle of the lid (between 0 and 1) */
	public float lidAngle;

	/** The angle of the lid last tick */
	public float prevLidAngle;

	/** The number of players currently using this chest */
	public int numUsingPlayers;

	/** Server sync counter (once per 20 ticks) */
	private int ticksSinceSync;

	private boolean playerOnline = false;

	public String owner = "";

	/**
	 * Returns the number of slots in the inventory.
	 */
	public int getSizeInventory()
	{
		if (this.worldObj.getPlayerEntityByName(owner) != null)
		{
			return this.worldObj.getPlayerEntityByName(owner).inventory.getSizeInventory() - hotBarSize;
		}

		return 0;
	}

	/**
	 * Overriden in a sign to provide the text.
	 */
	public Packet getDescriptionPacket()
	{
		NBTTagCompound var1 = new NBTTagCompound();
		this.writeToNBT(var1);
		return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 4, var1);
	}

	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt)
	{
		this.readFromNBT(pkt.customParam1);
	}

	/**
	 * Returns the stack in slot i
	 */
	public ItemStack getStackInSlot(int par1)
	{
		if (this.worldObj.getPlayerEntityByName(owner) != null)
		{
			return this.worldObj.getPlayerEntityByName(owner).inventory.getStackInSlot(par1 + hotBarSize);
		}

		return null;
		//return null;
		//        return this.chestContents[par1];
	}

	/**
	 * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
	 * new stack.
	 */
	public ItemStack decrStackSize(int par1, int par2)
	{
		if (this.worldObj.getPlayerEntityByName(owner) != null)
		{
			ItemStack temp = this.worldObj.getPlayerEntityByName(owner).inventory.decrStackSize(par1 + hotBarSize, par2);
			this.onInventoryChanged();
			return temp;
		}

		return null;
	}

	/**
	 * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
	 * like when you close a workbench GUI.
	 */
	public ItemStack getStackInSlotOnClosing(int par1)
	{
		if (this.worldObj.getPlayerEntityByName(owner) != null)
		{
			return this.worldObj.getPlayerEntityByName(owner).inventory.getStackInSlotOnClosing(par1 + hotBarSize);
		}

		return null;
	}

	/**
	 * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
	 */
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		if (this.worldObj.getPlayerEntityByName(owner) != null)
		{
			this.worldObj.getPlayerEntityByName(owner).inventory.setInventorySlotContents(par1 + hotBarSize, par2ItemStack);
		}

		this.onInventoryChanged();
	}

	/**
	 * Returns the name of the inventory.
	 */
	public String getInvName()
	{
		return "container.invchest";
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);
		this.owner = par1NBTTagCompound.getString("Owner");
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setString("Owner", this.owner);
	}

	/**
	 * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
	 * this more of a set than a get?*
	 */
	public int getInventoryStackLimit()
	{
		if (this.worldObj.getPlayerEntityByName(owner) != null)
		{
			return this.worldObj.getPlayerEntityByName(owner).inventory.getInventoryStackLimit();
		}

		return 64;
	}

	/**
	 * Do not make give this method the name canInteractWith because it clashes with Container
	 */
	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
	{
		if (this.worldObj.getPlayerEntityByName(owner) != null)
			return false;
		return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
	}

	/**
	 * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
	 * ticks and creates a new spawn inside its implementation.
	 */
	public void updateEntity()
	{
		super.updateEntity();
		//        this.checkForAdjacentChests();
		++this.ticksSinceSync;
		float var1;

		if (playerOnline != (worldObj.getPlayerEntityByName(owner) != null))
		{
			worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, worldObj.getBlockId(xCoord, yCoord, zCoord));
			playerOnline = (worldObj.getPlayerEntityByName(owner) != null);
		}

		if (!this.worldObj.isRemote && this.numUsingPlayers != 0 && (this.ticksSinceSync + this.xCoord + this.yCoord + this.zCoord) % 200 == 0)
		{
			this.numUsingPlayers = 0;
			var1 = 5.0F;
			List var2 = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)this.xCoord - var1), (double)((float)this.yCoord - var1), (double)((float)this.zCoord - var1), (double)((float)(this.xCoord + 1) + var1), (double)((float)(this.yCoord + 1) + var1), (double)((float)(this.zCoord + 1) + var1)));
			Iterator var3 = var2.iterator();

			while (var3.hasNext())
			{
				EntityPlayer var4 = (EntityPlayer)var3.next();

				if (var4.openContainer instanceof ContainerInventoryChest)
				{
					IInventory var5 = ((ContainerInventoryChest)var4.openContainer).func_85151_d();

					if (var5 == this)
					{
						if (this.worldObj.getPlayerEntityByName(owner) == null)
						{
							var4.closeScreen();
						}
						else
						{
							++this.numUsingPlayers;
						}
					}
				}
			}
		}

		this.prevLidAngle = this.lidAngle;
		var1 = 0.1F;
		double var11;

		if (this.numUsingPlayers > 0 && this.lidAngle == 0.0F)
		{
			double var8 = (double)this.xCoord + 0.5D;
			var11 = (double)this.zCoord + 0.5D;
			this.worldObj.playSoundEffect(var8, (double)this.yCoord + 0.5D, var11, "random.chestopen", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
		}

		if (this.numUsingPlayers == 0 && this.lidAngle > 0.0F || this.numUsingPlayers > 0 && this.lidAngle < 1.0F)
		{
			float var9 = this.lidAngle;

			if (this.numUsingPlayers > 0)
			{
				this.lidAngle += var1;
			}
			else
			{
				this.lidAngle -= var1;
			}

			if (this.lidAngle > 1.0F)
			{
				this.lidAngle = 1.0F;
			}

			float var10 = 0.5F;

			if (this.lidAngle < var10 && var9 >= var10)
			{
				var11 = (double)this.xCoord + 0.5D;
				double var6 = (double)this.zCoord + 0.5D;
				this.worldObj.playSoundEffect(var11, (double)this.yCoord + 0.5D, var6, "random.chestclosed", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
			}

			if (this.lidAngle < 0.0F)
			{
				this.lidAngle = 0.0F;
			}
		}
	}

	/**
	 * Called when a client event is received with the event number and argument, see World.sendClientEvent
	 */
	public void receiveClientEvent(int par1, int par2)
	{
		if (par1 == 1)
		{
			this.numUsingPlayers = par2;
		}
	}

	public void openChest()
	{
		++this.numUsingPlayers;
		this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, Luxcraft.invChest.blockID, 1, this.numUsingPlayers);
	}

	public void closeChest()
	{
		--this.numUsingPlayers;
		this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, Luxcraft.invChest.blockID, 1, this.numUsingPlayers);
	}

	/**
	 * invalidates a tile entity
	 */
	public void invalidate()
	{
		super.invalidate();
		this.updateContainingBlockInfo();
	}
}
