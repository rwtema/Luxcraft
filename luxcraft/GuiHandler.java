package luxcraft;

import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{
	//returns an instance of the Container you made earlier
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world,
			int x, int y, int z)
	{
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

		if (tileEntity instanceof TileEntityLuxGenerator)
		{
			return new ContainerLuxGenerator(player.inventory, (TileEntityLuxGenerator) tileEntity);
		}
		else if (tileEntity instanceof TileEntityLuxStorage)
		{
			return new ContainerLuxStorage((TileEntityLuxStorage) tileEntity);
		}
		else if (tileEntity instanceof TileEntityLuxRitualTable)
		{
			return new ContainerLuxRitualTable(player.inventory, (TileEntityLuxRitualTable) tileEntity);
		}
		else if (tileEntity instanceof TileEntityLuxSynthesizer)
		{
			return new ContainerLuxAssembler(player.inventory, (TileEntityLuxSynthesizer) tileEntity);
		}
		else if (tileEntity instanceof TileEntityLuxDeconstructor)
		{
			return new ContainerLuxDeconstructor(player.inventory, (TileEntityLuxDeconstructor) tileEntity);
		}
		else if (tileEntity instanceof TileEntityLinkingChest)
        {
            return new ContainerInventoryChest(player.inventory, (TileEntityLinkingChest) tileEntity);
        }

		return null;
	}

	//returns an instance of the Gui you made earlier
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world,
			int x, int y, int z)
	{
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

		if (tileEntity instanceof TileEntityLuxGenerator){
			return new GuiLuxGenerator(player.inventory, (TileEntityLuxGenerator) tileEntity);
		}else  if (tileEntity instanceof TileEntityLuxStorage){
			return new GuiLuxStorage((TileEntityLuxStorage) tileEntity);
		}else  if (tileEntity instanceof TileEntityLuxRitualTable){
			return new GuiLuxRitualTable(player.inventory, (TileEntityLuxRitualTable) tileEntity);
		}else  if (tileEntity instanceof TileEntityLuxSynthesizer){
			return new GuiLuxAssembler(player.inventory, (TileEntityLuxSynthesizer) tileEntity);
		}else  if (tileEntity instanceof TileEntityLuxDeconstructor){
			return new GuiLuxDeconstructor(player.inventory, (TileEntityLuxDeconstructor) tileEntity);
		}
		else if (tileEntity instanceof TileEntityLinkingChest){
            if (world.getPlayerEntityByName(((TileEntityLinkingChest) tileEntity).owner) != null){
                return new GuiInventoryChest(player.inventory, (TileEntityLinkingChest) tileEntity);
            }
        }

		return null;
	}
}
