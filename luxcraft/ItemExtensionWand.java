package luxcraft;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemExtensionWand extends Item
{

	public ItemExtensionWand(int par1)
	{
		super(par1);
		this.setCreativeTab(CreativeTabs.tabTools);
		this.maxStackSize = 1;
		this.setTextureFile("/luxcraft/terrain.png");
		this.setIconIndex(61);
	}

	/**
	 * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
	 * the damage on the stack.
	 */
	public boolean hitEntity(ItemStack par1ItemStack, EntityLiving par2EntityLiving, EntityLiving par3EntityLiving)
	{
		par1ItemStack.damageItem(1, par3EntityLiving);
		return true;
	}

	@SideOnly(Side.CLIENT)

	/**
	 * Returns True is the item is renderer in full 3D when hold.
	 */
	public boolean isFull3D()
	{
		return true;
	}

	/**
	 * returns the action that specifies what animation to play when the items is being used
	 */
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return EnumAction.none;
	}

	/**
	 * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
	 * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
	 */
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer player, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
	{
		if(par3World.isRemote)
			return false;

		if(!player.capabilities.allowEdit)
			return false;

		List<ChunkPosition> blocks = this.getPotentialBlocks(player, par3World, par4, par5, par6, par7);

		int blockId = par3World.getBlockId(par4, par5, par6);
		
		if(blockId==0)
			return false;
		
		int data = -1;
		if(Item.itemsList[blockId].getHasSubtypes())
			data=Block.blocksList[blockId].getDamageValue(par3World, par4, par5, par6);



		if(blocks.size()>0){
			int slot = 0;
			for(int i = 0;i < blocks.size();i++){
				ChunkPosition temp = (ChunkPosition)blocks.get(i);

				for(slot=0;slot < player.inventory.getSizeInventory();slot++){
					if(player.inventory.getStackInSlot(slot) != null)
						if(player.inventory.getStackInSlot(slot).itemID==blockId)
							if(data==-1 | data == player.inventory.getStackInSlot(slot).getItemDamage())
								break;					
				}

				if(slot < player.inventory.getSizeInventory()){

					((ItemBlock)player.inventory.getStackInSlot(slot).getItem()).placeBlockAt(par1ItemStack, player, par3World, temp.x, temp.y, temp.z, par7, par8, par9, par10, ((ItemBlock)player.inventory.getStackInSlot(slot).getItem()).getMetadata(player.inventory.getStackInSlot(slot).getItemDamage()));

					if(!player.capabilities.isCreativeMode)
						player.inventory.decrStackSize(slot, 1);

				}else
					break;
			}
			player.inventory.onInventoryChanged();
			if(player instanceof EntityPlayerMP){
				((EntityPlayerMP) player).mcServer.getConfigurationManager().syncPlayerInventory(((EntityPlayerMP) player));
			}
			
		}
		return true;
	}

	public List<ChunkPosition> getPotentialBlocks(EntityPlayer player, World world, int x, int y, int z, int face){
		List blocks = new ArrayList<ChunkPosition>();
		int blockId = world.getBlockId(x, y, z);
		if(blockId==0) return blocks;
		int data = -1;
		if(Item.itemsList[blockId].getHasSubtypes())
			data=Block.blocksList[blockId].getDamageValue(world, x, y, z);

		int numBlocks = 0;
		int maxBlocks = 25;

		ItemStack genericStack = null;


		for(int i = 0; i < player.inventory.getSizeInventory(); i++){
			if(player.inventory.getStackInSlot(i) != null){
				if(player.inventory.getStackInSlot(i).itemID==blockId){
					if(data==-1 | data == player.inventory.getStackInSlot(i).getItemDamage()){
						genericStack = player.inventory.getStackInSlot(i);
						if(player.capabilities.isCreativeMode){
							numBlocks=maxBlocks;
							break;
						}

						numBlocks+=player.inventory.getStackInSlot(i).stackSize;
					}
				}
				if(numBlocks>=maxBlocks){
					numBlocks=maxBlocks;
					break;
				}
			}
		}


		int dx=Facing.offsetsXForSide[face], dy=Facing.offsetsYForSide[face], dz=Facing.offsetsZForSide[face];
		int mx = dx==0 ? 1 : 0, my = dy==0 ? 1 : 0, mz = dz==0 ? 1 : 0;

		if(face>1 & player.isSneaking())
			my=0;	


		if(Block.blocksList[blockId] != null){
			AxisAlignedBB var11 = Block.blocksList[blockId].getCollisionBoundingBoxFromPool(world, x, y, z);

			if(numBlocks > 0 & world.getBlockId(x+dx, y+dy, z+dz) == 0 & Block.blocksList[blockId].canPlaceBlockOnSide(world, x+dx, y+dy, z+dz,face) & y+dy < 255){
				if(checkAAB(world,var11,dx, dy, dz)){
					blocks.add(new ChunkPosition(x+dx, y+dy, z+dz));
					for (int i = 0; i < blocks.size() & blocks.size() < numBlocks; i++){
						for(int ax=-mx;ax<=mx;ax+=1){
							for(int ay=-my;ay<=my;ay+=1){
								for(int az=-mz;az<=mz;az+=1){
									ChunkPosition temp = new ChunkPosition(((ChunkPosition)blocks.get(i)).x+ax,((ChunkPosition)blocks.get(i)).y+ay,((ChunkPosition)blocks.get(i)).z+az);
									if(blocks.size() < numBlocks)
										if(player.canPlayerEdit(temp.x, temp.y, temp.z, face, genericStack))
											if(!blocks.contains(temp))
												if(Block.blocksList[blockId].canPlaceBlockOnSide(world, temp.x, temp.y, temp.z,face) & world.getBlockId(temp.x-dx, temp.y-dy, temp.z-dz) == blockId)
													if(data==-1 | data == Block.blocksList[blockId].getDamageValue(world, temp.x-dx, temp.y-dy, temp.z-dz))
														if(checkAAB(world,var11,temp.x-x, temp.y-y, temp.z-z))
															if(!blocks.contains(temp))
																blocks.add(temp);
								}
							}
						}
					}
				}
			}
		}
		return blocks;

	}


	public boolean checkAAB(World world, AxisAlignedBB bounds, int dx, int dy, int dz){
		if (bounds == null)
			return true;
		else if (world.checkIfAABBIsClear(bounds.getOffsetBoundingBox(dx,dy,dz)))
			return true;

		return false;
	}


}
