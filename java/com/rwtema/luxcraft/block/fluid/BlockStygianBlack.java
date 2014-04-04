package com.rwtema.luxcraft.block.fluid;

import com.rwtema.luxcraft.LuxcraftCreativeTab;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class BlockStygianBlack extends BlockFluidClassic {
    public static Fluid stygianBlack;

    public BlockStygianBlack() {
        super(registerFluid(), Material.rock);
        this.setCreativeTab(LuxcraftCreativeTab.instance);
        this.setBlockName("luxcraft:stygianBlack");
    }

    public static Fluid registerFluid() {
        stygianBlack = new Fluid("stygianBlack");
        FluidRegistry.registerFluid(stygianBlack);
        return stygianBlack;
    }

    @Override
    public void registerBlockIcons(IIconRegister register) {
        this.blockIcon = register.registerIcon("luxcraft:stygianBlack");
        stygianBlack.setIcons(this.blockIcon);
    }

    @Override
    public boolean isReplaceable(IBlockAccess world, int x, int y, int z) {
        return true;
    }

    @Override
    public int getMobilityFlag() {
        return 1;
    }
}
