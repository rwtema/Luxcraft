package com.rwtema.luxcraft;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class LuxEventHandler {

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
