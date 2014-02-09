package luxcraft;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;


public class ContainerFake extends Container{
	public boolean canInteractWith(EntityPlayer var1) {return false;}
	public void onCraftMatrixChanged(IInventory par1IInventory){}
}
