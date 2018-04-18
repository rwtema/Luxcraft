package com.rwtema.luxcraft.tiles;

import com.rwtema.luxcraft.PosIteratorCube;
import com.rwtema.luxcraft.luxapi.ILuxContainer;
import com.rwtema.luxcraft.luxapi.LuxColor;
import com.rwtema.luxcraft.luxapi.LuxStack;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Random;

public class TileEntityLuxTorch extends TileEntityLuxContainerBase implements ILuxContainer {
    public static int max = 100;
    public static Random rand = new Random();

    public TileEntityLuxTorch() {
        super(new LuxStack());

    }

    @Override
    public void updateEntity() {
        LuxColor col = LuxColor.col(getBlockMetadata());

        if (worldObj.isRemote)
            return;

        switch (col) {
            case Black:
                break;
            case Blue:
                break;
            case Cyan:
                break;
            case Green:
                for (Pos p : new PosIteratorCube(xCoord, yCoord, zCoord, 5)) {
                    Block b = worldObj.getBlock(p.x, p.y, p.z);
                    if (b.getTickRandomly()) {
                        worldObj.scheduleBlockUpdate(p.x, p.y, p.z, b, (1 + rand.nextInt(10)) * 20);
                    }
                }
                break;
            case Red:
                for (Pos p : new PosIteratorCube(xCoord, yCoord, zCoord, 5)) {
                    if (worldObj.isAirBlock(p.x, p.y, p.z) && !worldObj.isAirBlock(p.x, p.y - 1, p.z))
                        worldObj.setBlock(p.x, p.y, p.z, Blocks.fire);
                }
                break;
            case Violet:
                break;
            case White:
                break;
            case Yellow:
                break;
            default:
                break;
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tags) {
        super.readFromNBT(tags);

    }

    @Override
    public void writeToNBT(NBTTagCompound tags) {

        super.writeToNBT(tags);

    }

}
