package com.rwtema.luxcraft.block.fluid;

import com.rwtema.luxcraft.LuxcraftCreativeTab;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class BlockStygianBlack extends BlockFluidClassic {
    public static Fluid stygianBlack;

    public BlockStygianBlack() {
        super(registerFluid(), MaterialCustomLiquid.instance);
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
}
