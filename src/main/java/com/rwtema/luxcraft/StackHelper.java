package com.rwtema.luxcraft;

import net.minecraft.item.ItemStack;

public class StackHelper {

	public static int getStackSize(ItemStack stack){
		return stack.stackSize;
	}

	public static void setStackSize(ItemStack stack, int size){
		stack.stackSize = size;
	}

	public static boolean isNull(ItemStack stack){
		return stack == null;
	}
}
