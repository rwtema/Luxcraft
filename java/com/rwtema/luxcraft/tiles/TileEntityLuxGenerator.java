package com.rwtema.luxcraft.tiles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import com.rwtema.luxcraft.luxapi.ILuxTransmitter;
import com.rwtema.luxcraft.luxapi.LuxColor;
import com.rwtema.luxcraft.luxapi.LuxStack;
import com.rwtema.luxcraft.luxapi.Transfer;

public class TileEntityLuxGenerator extends TileEntityLuxContainerBaseInventory implements ILuxTransmitter {

	public TileEntityLuxGenerator() {
		super(null);
	}

	float level = 0;
	public static float maxLevel = 12 * 256;

	LuxColor type = null;

	public LuxColor getType() {
		if (type == null)
			type = LuxColor.col(this.getBlockMetadata());
		return type;
	}

	public void updateEntity() {
		if (level <= maxLevel - 1)
			level += 1;
		super.updateEntity();
	}
	


	@Override
	public LuxStack getLuxContents() {
		return new LuxStack(getType(), level);
	}

	@Override
	public float MaxLuxLevel(LuxColor color) {
		return color == getType() ? maxLevel : 0;
	}

	@Override
	public LuxStack insertLux(LuxStack lux, Transfer simulate) {
		float p = lux.lux[getType().index];
		p = Math.min(p, this.maxLevel - this.level);

		if (simulate.perform)
			this.level += p;

		return new LuxStack(getType(), p);
	}

	@Override
	public LuxStack extractLux(LuxStack lux, Transfer simulate) {
		float p = lux.lux[getType().index];
		p = Math.min(p, this.level);

		if (simulate.perform)
			this.level -= p;

		return new LuxStack(getType(), p);
	}

	InventoryBasic inv = new InventoryBasic("Lux Generator", true, 9);

	@Override
	public IInventory getInv() {
		return inv;
	}



}
