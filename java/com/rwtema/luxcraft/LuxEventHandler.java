package com.rwtema.luxcraft;

import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class LuxEventHandler {
	// @ForgeSubscribe
	// public void oreRegistered(OreRegisterEvent event){
	// System.out.println(event.Name+" "+event.Ore.getItemName());
	//
	// }

	// @ForgeSubscribe
	// public void soundPlay(PlaySoundAtEntityEvent event)
	// {
	// Iterator var18 =
	// event.entity.worldObj.getChunkFromBlockCoords((int)event.entity.posX,
	// (int)event.entity.posZ).chunkTileEntityMap.values().iterator();
	//
	// while (var18.hasNext())
	// {
	// TileEntity var22 = (TileEntity)var18.next();
	// if(var22 instanceof TileEntityMuffler){
	//
	// }
	// }
	// }

	//
	@SubscribeEvent
	public void playerUse(PlayerInteractEvent event) {
//
//		if (event.action == event.action.RIGHT_CLICK_BLOCK || event.action == event.action.RIGHT_CLICK_AIR) {
//			ItemStack curItem = event.entityPlayer.getCurrentEquippedItem();
//			if (curItem != null) {
//
//				if (event.entityPlayer.getCurrentEquippedItem().getItem() == Items.stick) {
//
//					if (event.entityPlayer.worldObj.getBlock(event.x, event.y, event.z) == Luxcraft.luxReflector) {
//
//						Luxcraft.luxReflector.onBlockActivated(event.entityPlayer.worldObj, event.x, event.y, event.z, event.entityPlayer, 0, 0, 0, 0);
//
//						if (!event.entityPlayer.worldObj.isRemote) {
//							Packet packet = new C08PacketPlayerBlockPlacement(event.x, event.y, event.z, event.face, event.entityPlayer.inventory.getCurrentItem(), 0.5F, 0.5F, 0.5F);
//							((NetHandlerPlayClient) FMLClientHandler.instance().getClientPlayHandler()).addToSendQueue(packet);
//						}
//
//						// Send shift-click to prisms with sticks
//						event.setCanceled(true);
//					}
//				}
//			}
//		}

		// // dont double place string on dark sheen blocks
		// if (event.action == event.action.RIGHT_CLICK_BLOCK &
		// !event.entityPlayer.isSneaking()) {
		// if (event.entityPlayer.worldObj.getBlock(event.x, event.y - 1,
		// event.z) == Luxcraft.darkBlock) {
		// ItemStack curItem = event.entityPlayer.getCurrentEquippedItem();
		// if (curItem != null) {
		// if (event.entityPlayer.getCurrentEquippedItem().getItem() ==
		// Items.string) {
		// if (event.entityPlayer.worldObj.getBlock(event.x, event.y, event.z)
		// == Blocks.tripwire) {
		// event.setCanceled(true);
		// }
		// if (event.entityPlayer.worldObj.getBlock(event.x, event.y, event.z)
		// == Blocks.redstone_wire) {
		// event.setCanceled(true);
		// }
		// }
		// }
		//
		// }
		// }
	}
}
