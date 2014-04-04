package com.rwtema.luxcraft.block.fluid;

import com.rwtema.luxcraft.LuxcraftCreativeTab;
import com.rwtema.luxcraft.particles.EntityVoidFX;
import com.rwtema.luxcraft.particles.ParticleHandler;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidFinite;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import java.util.Random;

public class BlockVoidGas extends BlockFluidFinite {
    public static IIcon voidGas_particle = null;
    public static Fluid voidGas;

    public BlockVoidGas() {
        super(createFluid(), Material.rock);
        this.setCreativeTab(LuxcraftCreativeTab.instance);
        this.setRenderPass(1);
        this.setBlockName("luxcraft:voidGas");
    }

    public static Fluid createFluid() {
        voidGas = new Fluid("voidGas");
        voidGas.setGaseous(true);
        voidGas.setDensity(10);
        FluidRegistry.registerFluid(voidGas);
        return voidGas;
    }

    @Override
    public boolean isReplaceable(IBlockAccess world, int x, int y, int z) {
        return true;
    }

    @Override
    public int getMobilityFlag() {
        return 1;
    }

    @Override
    public void registerBlockIcons(IIconRegister register) {
        this.blockIcon = register.registerIcon("luxcraft:voidGas");
        voidGas.setIcons(this.blockIcon);
        this.voidGas_particle = register.registerIcon("luxcraft:voidGas_particle");
    }

    @Override
    public void randomDisplayTick(World world, int x, int y, int z, Random random) {
        super.randomDisplayTick(world, x, y, z, random);
        float maxY = getFluidHeightForRender(world, x, y, z);
        for (int i = 0; i < 5; i++) ;
        ParticleHandler.spawnParticle(new EntityVoidFX(world, x + random.nextDouble(), y + random.nextDouble() * maxY, z + random.nextDouble()));
    }

    public float getFluidHeightForRender(IBlockAccess world, int x, int y, int z) {
        if (world.getBlock(x, y, z) == this) {
            if (world.getBlock(x, y - this.densityDir, z).getMaterial().isLiquid()) {
                return 1;
            }

            if (world.getBlockMetadata(x, y, z) == this.getMaxRenderHeightMeta()) {
                return 0.875F;
            }
        }
        return !world.getBlock(x, y, z).getMaterial().isSolid() && world.getBlock(x, y - this.densityDir, z) == this ? 1 : this.getQuantaPercentage(world, x, y, z) * 0.875F;
    }
}
