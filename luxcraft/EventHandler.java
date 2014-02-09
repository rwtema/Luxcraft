package luxcraft;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.ChunkPosition;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import org.lwjgl.opengl.GL11;

public class EventHandler {
	//	@ForgeSubscribe
	//	public void oreRegistered(OreRegisterEvent event){
	//		System.out.println(event.Name+" "+event.Ore.getItemName());
	//		
	//	}

	//	@ForgeSubscribe
	//    public void soundPlay(PlaySoundAtEntityEvent event)
	//    {
	//		Iterator var18 = event.entity.worldObj.getChunkFromBlockCoords((int)event.entity.posX, (int)event.entity.posZ).chunkTileEntityMap.values().iterator();
	//
	//        while (var18.hasNext())
	//        {
	//        	TileEntity var22 = (TileEntity)var18.next();
	//        	if(var22 instanceof TileEntityMuffler){
	//        		
	//        	}
	//        }
	//    }

	@ForgeSubscribe
	public void cursedEarth_EntityDeath(LivingDeathEvent event){
		int x = (int) event.entityLiving.posX;
		int y = (int) event.entityLiving.boundingBox.minY;
		int z = (int) event.entityLiving.posZ;

		int[] ax = {0,0,1,-1};
		int[] az = {1,-1,0,0};
		
		if(event.source.damageType!="player")
			return;
		
		EntityPlayer player = (EntityPlayer)event.source.getSourceOfDamage();

		if(event.entityLiving.worldObj.isRemote)
			return;

		long time = event.entityLiving.worldObj.getWorldTime() % 24000;
		if(time < 17000 | time > 19000)
			return;
		
		if(event.entity.posY<70)
			return;

		boolean found = false;

		for(int dx = -2;!found & dx<=2;dx++){
			for(int dy = -2;!found & dy<=0;dy++){
				for(int dz = -2;!found & dz<=2;dz++){
					if(event.entityLiving.worldObj.getBlockId(x+dx, y+dy, z+dz)==Block.enchantmentTable.blockID){
						found=true;
						x=x+dx;
						y=y+dy;
						z=z+dz;
					}
				}
			}
		}

		if(!found)
			return;

		if(event.entityLiving.worldObj.getBlockId(x,y,z)!=Block.enchantmentTable.blockID)
			return;

		
		if(!event.entityLiving.worldObj.canBlockSeeTheSky(x, y, z)){
			player.sendChatToPlayer("Sky Obstructed");
			return;		
		}
		
		for(int i = 0; i<4; i++)
			if(event.entityLiving.worldObj.getBlockLightValue(x+ax[i],y,z+az[i])>9){
				player.sendChatToPlayer("Too Bright");
				return;
			}

		if(event.entityLiving.worldObj.getBlockLightValue(x, y+1, z)>9){
			player.sendChatToPlayer("Too Bright");
			return;
		}



		boolean hasDirt=false;

		for(int dx = -1; dx <= 1; dx++){
			for(int dz = -1; dz <= 1; dz++){
				if(dx != 0 | dz != 0){
					if(event.entityLiving.worldObj.getBlockId(x+dx, y, z+dz)!=Block.redstoneWire.blockID)
						return;
				}	
				if(!hasDirt)
					if(event.entityLiving.worldObj.getBlockId(x+dx, y-1, z+dz)==Block.dirt.blockID ||
					event.entityLiving.worldObj.getBlockId(x+dx, y-1, z+dz)==Block.grass.blockID
							){
						hasDirt=true;
					}

			}
		}

		if(!hasDirt)
			return;

		event.entityLiving.worldObj.addWeatherEffect(new EntityLightningBolt(event.entityLiving.worldObj, (double)x, (double)y, (double)z));

		BlockCursedGround.powered=true;

		int max_range = 8;

		for(int dx = -8; dx<=8;dx++)
			for(int dz = -8; dz<=8;dz++)
				for(int dy = 8; dy>-8;dy--){
					if(dx*dx+dy*dy+dz*dz<=max_range*max_range){
						int id =  event.entityLiving.worldObj.getBlockId(x+dx, y+dy, z+dz);
						if(id == Block.dirt.blockID || id==Block.grass.blockID){
							event.entityLiving.worldObj.setBlockWithNotify(x+dx, y+dy, z+dz, Luxcraft.cursedEarthBlockID);
							event.entityLiving.worldObj.notifyBlocksOfNeighborChange(x+dx, y+dy, z+dz, Luxcraft.cursedEarthBlockID);
							break;
						}
						else if(id > 0 & Block.blocksList[id] instanceof BlockLeaves){
							Block.blocksList[id].dropBlockAsItem(event.entityLiving.worldObj, x, y, z, event.entityLiving.worldObj.getBlockMetadata(x, y, z), 0);
							event.entityLiving.worldObj.setBlockWithNotify(x+dx, y+dy, z+dz, 0);
						}
//						else if(event.entityLiving.worldObj.getBlockLightOpacity(x+dx, y+dy, z+dz) > 2){
//							break;
//						}
					}else if (dy < 0)
						break;
				}


		//		int max_size=64;
		//		int[] ax = {0,0,1,-1};
		//		int[] az = {1,-1,0,0};
		//		List<ChunkPosition> redstone = new ArrayList<ChunkPosition>();
		//		redstone.add(new ChunkPosition(x,y,z));
		//		for (int ri = 0; ri < redstone.size(); ri += 1)
		//			for (int i = 0; i < 4; i += 1)
		//			{
		//				ChunkPosition temp = new ChunkPosition(((ChunkPosition) redstone.get(ri)).x + ax[i], y, ((ChunkPosition) redstone.get(ri)).z + az[i]);
		//
		//				if (Math.abs(temp.x - x) <= max_size & Math.abs(temp.z - z) <= max_size)
		//					if(event.entityLiving.worldObj.getBlockId(temp.x, temp.y, temp.z) == Block.redstoneWire.blockID)
		//						if (!redstone.contains(temp))
		//						{
		//							redstone.add(temp);
		//							if(event.entityLiving.worldObj.getBlockId(temp.x, temp.y-1, temp.z) == Block.dirt.blockID){
		//								event.entityLiving.worldObj.setBlockWithNotify(temp.x, temp.y-1, temp.z, Luxcraft.cursedEarthBlockID);
		//								event.entityLiving.worldObj.notifyBlocksOfNeighborChange(temp.x, temp.y, temp.z, Luxcraft.cursedEarthBlockID);
		//							}
		//						}
		//			}		
		BlockCursedGround.powered=false;

	}



	@ForgeSubscribe
	public void drawBlockHighlight(DrawBlockHighlightEvent event){
		if(event.currentItem!=null){
			if(event.currentItem.itemID == Luxcraft.extendWand.itemID){
				List<ChunkPosition> blocks = ((ItemExtensionWand)Luxcraft.extendWand).getPotentialBlocks(event.player, event.context.theWorld, event.target.blockX, event.target.blockY, event.target.blockZ, event.target.sideHit);
				int blockId = event.context.theWorld.getBlockId(event.target.blockX, event.target.blockY, event.target.blockZ);

				if(Block.blocksList[blockId] != null & blocks.size() > 0){
					GL11.glEnable(GL11.GL_BLEND);
					GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.35F);
					GL11.glLineWidth(3.0F);
					GL11.glDisable(GL11.GL_TEXTURE_2D);
					GL11.glDepthMask(false);			

					double px = event.player.lastTickPosX + (event.player.posX - event.player.lastTickPosX) * (double)event.partialTicks;
					double py = event.player.lastTickPosY + (event.player.posY - event.player.lastTickPosY) * (double)event.partialTicks;
					double pz = event.player.lastTickPosZ + (event.player.posZ - event.player.lastTickPosZ) * (double)event.partialTicks;

					GL11.glTranslated(-px,-py,-pz);

					//Tessellator.instance.startDrawingQuads();

					for(int i = 0; i < blocks.size();i+=1){
						ChunkPosition temp = (ChunkPosition)blocks.get(i);
						this.drawOutlinedBoundingBox(AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(temp.x,temp.y,temp.z,temp.x+1,temp.y+1,temp.z+1));
					}

					GL11.glDepthMask(true);
					GL11.glEnable(GL11.GL_TEXTURE_2D);
					GL11.glDisable(GL11.GL_BLEND);
					//event.context.globalRenderBlocks.renderBlockByRenderType(Block.pumpkin, event.target.blockX, event.target.blockY+2, event.target.blockZ);
					//Tessellator.instance.draw();

					GL11.glTranslated(px,py,pz);



				}
				return;
			}
		}

		//		event.context.drawBlockBreaking(event.player, event.target, event.subID, event.currentItem, event.partialTicks);
		//		event.context.drawSelectionBox(event.player, event.target, event.subID, event.currentItem, event.partialTicks);		
	}

	private void drawOutlinedBoundingBox(AxisAlignedBB par1AxisAlignedBB)
	{
		Tessellator var2 = Tessellator.instance;
		var2.startDrawing(3);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		var2.draw();
		var2.startDrawing(3);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.draw();
		var2.startDrawing(1);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		var2.draw();
	}

	@ForgeSubscribe
	public void playerUse(PlayerInteractEvent event)
	{

		if(!event.entityPlayer.worldObj.isRemote)
			//Send shift-click to prisms with sticks
			if(event.action==event.action.RIGHT_CLICK_BLOCK | event.action==event.action.RIGHT_CLICK_AIR)
			{
				ItemStack curItem = event.entityPlayer.getCurrentEquippedItem();
				if(curItem != null){

					if(event.entityPlayer.getCurrentEquippedItem().itemID == Item.stick.itemID){

						if(event.entityPlayer.worldObj.getBlockId(event.x, event.y, event.z) == Luxcraft.prism.blockID){

							Luxcraft.prism.onBlockActivated(event.entityPlayer.worldObj, event.x, event.y, event.z, event.entityPlayer, 0, 0, 0, 0);
							event.setCanceled(true);
						}
					}
				}
			}

		//dont double place string on dark sheen blocks
		if(event.action==event.action.RIGHT_CLICK_BLOCK & !event.entityPlayer.isSneaking()){	
			if(event.entityPlayer.worldObj.getBlockId(event.x, event.y-1, event.z)==Luxcraft.darkBlock.blockID){
				ItemStack curItem = event.entityPlayer.getCurrentEquippedItem();
				if(curItem != null){
					if(event.entityPlayer.getCurrentEquippedItem().itemID == Item.silk.itemID){
						if(event.entityPlayer.worldObj.getBlockId(event.x, event.y, event.z) == Block.tripWire.blockID){
							event.setCanceled(true);
						}
						if(event.entityPlayer.worldObj.getBlockId(event.x, event.y, event.z) == Block.redstoneWire.blockID){
							event.setCanceled(true);
						}
					}
				}

			}
		}
	}

}
