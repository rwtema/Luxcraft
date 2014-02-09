package luxcraft;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerLuxRitualTable extends Container
{
	public TileEntityLuxRitualTable ritualtable;
	private int lastSchemaAnalyseTime = 0;
	private int lastSchemaMaxAnalyseTime = 0;

	private int lastRitualStrength = -1;
	private int lastRitualMaxStrength = -1;
	

	public ContainerLuxRitualTable(InventoryPlayer par1InventoryPlayer, TileEntityLuxRitualTable par2TileEntityLuxRitualTable)
	{
		this.ritualtable = par2TileEntityLuxRitualTable;

		this.addSlotToContainer(new SlotLuxItem(par2TileEntityLuxRitualTable, 0, 80, 38));

		int var3;

		for (var3 = 0; var3 < 3; ++var3)
		{
			for (int var4 = 0; var4 < 9; ++var4)
			{
				this.addSlotToContainer(new Slot(par1InventoryPlayer, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
			}
		}

		for (var3 = 0; var3 < 9; ++var3)
		{
			this.addSlotToContainer(new Slot(par1InventoryPlayer, var3, 8 + var3 * 18, 142));
		}
	}


	public boolean canInteractWith(EntityPlayer par1EntityPlayer)
	{
		return this.ritualtable.isUseableByPlayer(par1EntityPlayer);
	}

	/**
	 * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
	 */
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{
		ItemStack var3 = null;
		Slot var4 = (Slot)this.inventorySlots.get(par2);

		if (var4 != null && var4.getHasStack())
		{
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();

			if (par2 == 0)
			{
				if (!this.mergeItemStack(var5, 1, 37, true))
				{
					return null;
				}

				var4.onSlotChange(var5, var3);
			}
			else 
			{
				if (!this.mergeItemStack(var5, 0, 1, false))
				{
					return null;
				}
				else if (par2 >= 1 && par2 < 28 && !this.mergeItemStack(var5, 28, 37, false))
				{
					return null;
				}
				else if (par2 >= 28 && par2 < 37 && !this.mergeItemStack(var5, 1, 28, false))
				{
					return null;
				}

			}

			if (var5.stackSize == 0)
			{
				var4.putStack((ItemStack)null);
			}
			else
			{
				var4.onSlotChanged();
			}

			if (var5.stackSize == var3.stackSize)
			{
				return null;
			}

			var4.onPickupFromSlot(par1EntityPlayer, var5);
		}

		return var3;
	}
	
    public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, this.ritualtable.schemaAnalyseTime);
        par1ICrafting.sendProgressBarUpdate(this, 1, this.ritualtable.schemaMaxAnalyseTime);
    }

    /**
     * Updates crafting matrix; called from onCraftMatrixChanged. Args: none
     */
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int var1 = 0; var1 < this.crafters.size(); ++var1)
        {
            ICrafting var2 = (ICrafting)this.crafters.get(var1);

            if (this.lastSchemaAnalyseTime != this.ritualtable.schemaAnalyseTime)
            {
                var2.sendProgressBarUpdate(this, 0, this.ritualtable.schemaAnalyseTime);
            }
            
            if (this.lastSchemaMaxAnalyseTime  != this.ritualtable.schemaMaxAnalyseTime)
            {
                var2.sendProgressBarUpdate(this, 1, this.ritualtable.schemaMaxAnalyseTime);
            }
        }

        this.lastSchemaAnalyseTime = this.ritualtable.schemaAnalyseTime;
        this.lastSchemaMaxAnalyseTime = this.ritualtable.schemaAnalyseTime;
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2)
    {
        if (par1 == 0)
        	this.ritualtable.schemaAnalyseTime = par2;
        else if (par1 == 1)
        	this.ritualtable.schemaMaxAnalyseTime = par2;

    }
	
	
}
