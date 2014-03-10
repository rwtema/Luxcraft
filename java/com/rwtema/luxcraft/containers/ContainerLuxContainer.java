package com.rwtema.luxcraft.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.item.ItemStack;

import com.rwtema.luxcraft.luxapi.ILuxContainer;
import com.rwtema.luxcraft.luxapi.LuxStack;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerLuxContainer extends Container {
	public ILuxContainer container;

	private LuxStack lux = new LuxStack();

	public ContainerLuxContainer(InventoryPlayer player, ILuxContainer container) {
		this.container = container;
	}

	public LuxStack getLux() {
		return lux;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
		return null;
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		LuxStack newLux = container.getLuxContents();

		for (Object c : this.crafters) {
			ICrafting crafter = ((ICrafting) c);
			for (byte i = 0; i < 8; i++)
				for (byte j = 0; j < 3; j++)
					if (convToShort(newLux.lux[i], j) != convToShort(lux.lux[i], j)) {
						crafter.sendProgressBarUpdate(this, i * 3 + j, convToShort(newLux.lux[i], j));
					}
		}

		lux.copyFrom(newLux);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int par1, int par2) {
		int k = par1 % 3;
		int l = par1 / 3;
		lux.lux[l] = changeShort(lux.lux[l], (short) par2, k);
	}

	public static short convToShort(float t, int level) {
		switch (level) {
		case 0:
			return (short) Math.floor((t - Math.floor(t)) * 32768);
		case 1:
			return (short) (Math.floor(t) % 32768);
		case 2:
			return (short) (Math.floor(t / 32768));
		default:
			return 0;
			// throw new
			// RuntimeException("Invalid level, something has gone wrong");
		}
	}

	public static float changeShort(float t, short k, int level) {
		short[] v = new short[3];

		for (int i = 0; i < 3; i++)
			if (i == level)
				v[i] = k;
			else
				v[i] = convToShort(t, i);

		return v[2] * 32768 + v[1] + v[0] / 32768.F;
	}

	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		return true;
	}
}
