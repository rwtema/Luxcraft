package com.rwtema.luxcraft.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockMetadata extends ItemBlock {

    public ItemBlockMetadata(Block p_i45328_1_) {
        super(p_i45328_1_);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }


    @Override
    public int getMetadata(int par1) {
        return par1;
    }

    @Override
    public String getUnlocalizedName(ItemStack par1ItemStack) {
        return super.getUnlocalizedName() + "." + par1ItemStack.getItemDamage();
    }
}
