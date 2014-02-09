package luxcraft;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ContainerLuxStorage extends ContainerLuxContainer
{
    public TileEntityLuxStorage storage;

    public ContainerLuxStorage(TileEntityLuxStorage par2TileEntityLuxStorage)
    {
        this.storage = par2TileEntityLuxStorage;
        container = storage;
        
    }

    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return this.storage.isUseableByPlayer(par1EntityPlayer);
    }
    
    /**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     */
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
    {
    	return null;
    }
}
