package com.rwtema.luxcraft.block_base;

import net.minecraft.block.material.Material;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class BlockMultiBlockSelection extends BlockMultiBlock {
	public BlockMultiBlockSelection(Material xMaterial) {
		super(xMaterial);
	}

	public Box boundsOveride = null;

	@Override
	public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 start, Vec3 end) {
		MovingObjectPosition result = null;
		for (Box box : this.getWorldModel(world, x, y, z)) {
			boundsOveride = box;
			MovingObjectPosition r = super.collisionRayTrace(world, x, y, z, start, end);
			if (r != null) {
				if (result == null || start.distanceTo(r.hitVec) < start.distanceTo(result.hitVec))
					result = r;
			}
		}

		boundsOveride = null;
		return result;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int x, int y, int z) {
		Box bounds;
		if (boundsOveride != null)
			bounds = boundsOveride;
		else
			bounds = BoxModel.boundingBox(this.getWorldModel(par1IBlockAccess, x, y, z));

		if (bounds != null) {
			this.setBlockBounds(bounds.minX, bounds.minY, bounds.minZ, bounds.maxX, bounds.maxY, bounds.maxZ);
		}
	}

}
