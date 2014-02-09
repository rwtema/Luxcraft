package luxcraft;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;

public class ContainerLuxContainer extends Container {
	public TileEntity container;

	public boolean canInteractWith(EntityPlayer var1) {
		return false;
	}
}
