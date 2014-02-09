package luxcraft;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerLuxAssembler extends ContainerLuxContainer
{
    public TileEntityLuxSynthesizer assembler;

    public ContainerLuxAssembler(InventoryPlayer par1InventoryPlayer, TileEntityLuxSynthesizer par2TileEntityLuxAssembler)
    {
        this.assembler = par2TileEntityLuxAssembler;
        this.container = assembler;
        
    	this.addSlotToContainer(new SlotSchema(assembler, 0, 8, 13));
    	this.addSlotToContainer(new SlotNoInsert(assembler, 1, 8, 54));

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
        return this.assembler.isUseableByPlayer(par1EntityPlayer);
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
}
