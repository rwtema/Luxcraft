package com.rwtema.luxcraft.tiles;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.IPlantable;

import com.rwtema.luxcraft.LuxHelper;
import com.rwtema.luxcraft.PosIterator;
import com.rwtema.luxcraft.luxapi.ILuxTransmitter;
import com.rwtema.luxcraft.luxapi.LuxColor;
import com.rwtema.luxcraft.luxapi.LuxStack;
import com.rwtema.luxcraft.luxapi.Transfer;

public class TileEntityLuxGenerator extends TileEntityLuxTransmitterBaseInventory implements ILuxTransmitter {

	public TileEntityLuxGenerator() {
		super(null);
	}

	float level = 0;
	public static float maxLevel = 12 * 256;

	LuxColor type = null;

	public LuxColor getType() {
		if (type == null)
			type = LuxColor.col(this.getBlockMetadata());
		return type;
	}

	public Boolean gen = null;

	public void updateEntity() {
		if (!LuxHelper.shouldProcess(worldObj))
			return;

		if (gen == null || this.worldObj.getTotalWorldTime() % 160 == 0) {
			gen = checkGen();
		}

		if (gen && level <= maxLevel - 1)
			level += 1;
		super.updateEntity();
	}

	@Override
	public LuxStack getLuxContents() {
		return new LuxStack(getType(), level);
	}

	@Override
	public float MaxLuxLevel(LuxColor color) {
		return color == getType() ? maxLevel : 0;
	}

	@Override
	public LuxStack insertLux(LuxStack lux, Transfer simulate) {
		float p = lux.lux[getType().index];
		p = Math.min(p, this.maxLevel - this.level);

		if (simulate.perform)
			this.level += p;

		return new LuxStack(getType(), p);
	}

	@Override
	public LuxStack extractLux(LuxStack lux, Transfer simulate) {
		float p = lux.lux[getType().index];
		p = Math.min(p, this.level);

		if (simulate.perform)
			this.level -= p;

		return new LuxStack(getType(), p);
	}

	InventoryBasic inv = new InventoryBasic("Lux Generator", true, 9);

	@Override
	public IInventory getInv() {
		return inv;
	}

	public boolean checkGen() {
		switch (this.getType()) {
		case Black:
			for (Pos p : new PosIterator(xCoord, yCoord, zCoord, 1)) {
				if (this.worldObj.getBlock(p.x, p.y, p.z) == Blocks.skull) {
					TileEntity tile = this.worldObj.getTileEntity(p.x, p.y, p.z);
					if (tile instanceof TileEntitySkull)
						if (((TileEntitySkull) tile).func_145904_a() == 1)
							return true;
				}
			}

			return false;
		case Blue:
			int w = 0;
			for (Pos p : new PosIterator(xCoord, yCoord, zCoord, 1)) {
				Block b = this.worldObj.getBlock(p.x, p.y, p.z);
				if (b == Blocks.water || b == Blocks.flowing_water)
					w++;
				if (w == 2)
					return true;
			}
			return false;
		case Cyan:
			if (!this.worldObj.getEntitiesWithinAABB(EntityLiving.class,
					(AxisAlignedBB.getAABBPool().getAABB(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord).addCoord(0.5, 0.5, 0.5)).expand(1.5, 1.5, 1.5)).isEmpty())
				return true;
			return false;

		case Green:
			for (Pos p : new PosIterator(xCoord, yCoord, zCoord, 1)) {
				if (this.worldObj.getBlock(p.x, p.y, p.z) instanceof IPlantable)
					return true;
			}

			return false;
		case Red:
			for (Pos p : new PosIterator(xCoord, yCoord, zCoord, 1)) {
				if (this.worldObj.getBlock(p.x, p.y, p.z) == Blocks.fire)
					return true;
			}

			return false;
		case Violet:
			for (Pos p : new PosIterator(xCoord, yCoord, zCoord, 1)) {
				if (this.worldObj.getBlock(p.x, p.y, p.z) == Blocks.end_stone)
					return true;
			}

			return false;
		case White:
			boolean w1 = false,
			l = false;
			for (Pos p : new PosIterator(xCoord, yCoord, zCoord, 1)) {
				Block b = this.worldObj.getBlock(p.x, p.y, p.z);
				if (!w1 && b == Blocks.water || b == Blocks.flowing_water)
					w1 = true;
				if (!l && b == Blocks.lava || b == Blocks.flowing_lava)
					l = true;
				if (w1 && l)
					return true;
			}

			return false;
		case Yellow:

			if (this.worldObj.canBlockSeeTheSky(xCoord, yCoord + 1, zCoord)) {
				float f1 = this.worldObj.getCelestialAngle(1);
				float f2 = 1.0F - (MathHelper.cos(f1 * (float) Math.PI * 2.0F) * 2.0F + 0.2F);

				if (f2 < 0.0F) {
					f2 = 0.0F;
				}

				if (f2 > 1.0F) {
					f2 = 1.0F;
				}

				f2 = 1.0F - f2;
				f2 = (float) ((double) f2 * (1.0D - (double) (this.worldObj.getRainStrength(1) * 5.0F) / 16.0D));
				f2 = (float) ((double) f2 * (1.0D - (double) (this.worldObj.getWeightedThunderStrength(1) * 5.0F) / 16.0D));
				f2 = f2 * 0.8F + 0.2F;

				if (f2 > 0.5F)
					return true;
			}
			return false;
		default:
			return false;
		}
	}

	public void onNeighbourChange() {
		super.onNeighbourChange();
		gen = checkGen();
	}

}
