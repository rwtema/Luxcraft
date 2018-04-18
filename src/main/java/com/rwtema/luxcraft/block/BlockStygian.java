package com.rwtema.luxcraft.block;

import com.rwtema.luxcraft.LuxcraftCreativeTab;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockStygian extends Block {


    public BlockStygian() {
        super(Material.rock);
        this.setBlockName("luxcraft:stygian");
        this.setBlockTextureName("luxcraft:stygian");
        this.setCreativeTab(LuxcraftCreativeTab.instance);
        this.setHardness(1);
    }

    @Override
    public int damageDropped(int p_149692_1_) {
        return p_149692_1_;
    }
}
