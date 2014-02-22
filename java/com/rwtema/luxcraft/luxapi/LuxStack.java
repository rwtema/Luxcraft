package com.rwtema.luxcraft.luxapi;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import com.rwtema.luxcraft.LuxHelper;

import cpw.mods.fml.common.registry.GameRegistry;

public class LuxStack {
	public float[] lux = new float[8];

	public LuxStack() {
	}

	public LuxStack(float W, float R, float G, float B, float C, float Y, float V, float K) {
		lux[0] = W;
		lux[1] = R;
		lux[2] = G;
		lux[3] = B;
		lux[4] = C;
		lux[5] = Y;
		lux[6] = V;
		lux[7] = K;
	}

	public LuxStack(LuxColor color, float amount, Object... colors) {
		lux[color.index] = amount;
		LuxColor col = null;

		for (int i = 0; i < colors.length; i++) {
			if (colors[i] instanceof LuxColor)
				col = (LuxColor) colors[i];
			else if (col != null && colors[i] instanceof Float)
				lux[color.index] = (Float) colors[i];
		}
	}

	public LuxStack add(LuxColor color, float amount) {
		lux[color.index] += amount;
		return this;
	}

	public LuxStack add(LuxStack other) {
		for (byte color = 0; color < lux.length; color++)
			lux[color] = lux[color] + other.lux[color];
		return this;
	}

	public LuxStack extract(LuxStack other) {
		return this.extract(other.lux);
	}

	public LuxStack extract(float[] extract) {
		LuxStack temp = new LuxStack();
		for (byte color = 0; color < lux.length; color++) {
			temp.lux[color] = Math.min(lux[color], extract[color]);
			lux[color] -= temp.lux[color];
		}
		return temp;
	}

	public LuxStack mult(double mult) {
		LuxStack temp = new LuxStack();
		for (byte color = 0; color < lux.length; color++)
			temp.lux[color] = (float) Math.floor(lux[color] * mult);
		return temp;
	}

	public LuxStack[] split(int n) {
		if (n == 1)
			return new LuxStack[] { this.copy() };

		LuxStack[] temp = new LuxStack[n];
		for (byte color = 0; color < lux.length; color++) {
			float k = (float) Math.ceil((double) lux[color] / n);

		}
		return temp;
	}

	public boolean isEmpty() {
		for (byte c = 0; c < lux.length; c++)
			if (lux[c] != 0)
				return false;
		return true;
	}

	public String toString() {
		String str = "";
		for (byte c = 0; c < lux.length; c++)
			str = str + LuxHelper.color_abb[c] + lux[c];
		return str;
	}

	public float totalLux() {
		float total = 0;
		for (byte c = 0; c < lux.length; c++)
			total = total + lux[c];
		return total;
	}

	public LuxStack copy() {
		return (new LuxStack()).add(this);
	}

	public NBTTagCompound writeToNBT(NBTTagCompound tags) {
		for (byte i = 0; i < lux.length; i++)
			tags.setFloat("Lux_" + i, lux[i]);

		return tags;
	}

	/**
	 * Read the stack fields from a NBT object.
	 */
	public void readFromNBT(NBTTagCompound tags) {
		for (byte i = 0; i < lux.length; i++)
			lux[i] = tags.getFloat("Lux_" + i);
	}

	public static LuxStack loadFromNBT(NBTTagCompound tags) {
		LuxStack stack = new LuxStack();
		stack.readFromNBT(tags);
		return stack;
	}

}
