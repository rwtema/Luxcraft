package com.rwtema.luxcraft.block_base;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.rwtema.luxcraft.LuxcraftProxy;
import com.rwtema.luxcraft.luxapi.LuxColor;
import com.rwtema.luxcraft.particles.EntityLaserFX;
import com.rwtema.luxcraft.particles.ParticleHandler;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class BlockMultiBlock extends Block implements IMultiBoxBlock {
	public static final float dh = 0.0625F;

	@SideOnly(Side.CLIENT)
	public abstract void registerBlockIcons(IIconRegister registry);

	public BlockMultiBlock(Material xMaterial) {
		super(xMaterial);
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	/**
	 * Is this block (a) opaque and (b) a full 1m cube? This determines whether
	 * or not to render the shared face of two adjacent blocks and also whether
	 * the player can attach torches, redstone wire, etc to this block.
	 */
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	/**
	 * The type of render function that is called for this block
	 */
	@Override
	public int getRenderType() {
		return LuxcraftProxy.multiBoxRenderingID;
	}

	/**
	 * Adds all intersecting collision boxes to a list. (Be sure to only add
	 * boxes to the list if they intersect the mask.) Parameters: World, X, Y,
	 * Z, mask, list, colliding entity
	 */
	@Override
	public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity) {
		List models = this.getWorldModel(par1World, par2, par3, par4);

		if (models == null || models.size() == 0) {
			return;
		}

		for (int i = 0; i < models.size(); i++) {
			Box b = ((Box) models.get(i));
			AxisAlignedBB axisalignedbb1 = AxisAlignedBB.getAABBPool().getAABB(par2 + b.offsetx + b.minX, par3 + b.offsety + b.minY, par4 + b.offsetz + b.minZ, par2 + b.offsetx + b.maxX,
					par3 + b.offsety + b.maxY, par4 + b.offsetz + b.maxZ);

			if (axisalignedbb1 != null && par5AxisAlignedBB.intersectsWith(axisalignedbb1)) {
				par6List.add(axisalignedbb1);
			}
		}
	}

	/**
	 * Updates the blocks bounds based on its current state. Args: world, x, y,
	 * z
	 */
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int x, int y, int z) {
		Box bounds = BoxModel.boundingBox(this.getWorldModel(par1IBlockAccess, x, y, z));

		if (bounds != null) {
			this.setBlockBounds(bounds.minX, bounds.minY, bounds.minZ, bounds.maxX, bounds.maxY, bounds.maxZ);
		}
	}


}
