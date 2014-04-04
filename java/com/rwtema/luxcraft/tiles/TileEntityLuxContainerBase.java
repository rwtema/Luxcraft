package com.rwtema.luxcraft.tiles;

import com.rwtema.luxcraft.luxapi.ILuxContainer;
import com.rwtema.luxcraft.luxapi.LuxColor;
import com.rwtema.luxcraft.luxapi.LuxStack;
import com.rwtema.luxcraft.luxapi.Transfer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public abstract class TileEntityLuxContainerBase extends TileEntity implements ILuxContainer {
    protected final LuxStack maxLevels;
    protected LuxStack contents = new LuxStack();
    protected String customName = "";

    public TileEntityLuxContainerBase(LuxStack maxLevels) {
        this.maxLevels = maxLevels;
    }

    public void setMaxLux(LuxStack newMax) {
        for (byte c = 0; c < LuxColor.n; c++) {
            maxLevels.lux[c] = newMax.lux[c];
            if (contents.lux[c] > newMax.lux[c])
                contents.lux[c] = newMax.lux[c];
        }
    }

    public void setCustomName(String s) {
        customName = s;
    }

    public void readFromNBT(NBTTagCompound tags) {
        super.readFromNBT(tags);
        contents.readFromNBT(tags);
        customName = tags.getString("Custom Name");
    }

    public void writeToNBT(NBTTagCompound tags) {
        super.writeToNBT(tags);
        contents.writeToNBT(tags);
        if (!"".equals(customName))
            tags.setString("Custom Name", customName);
    }

    @Override
    public LuxStack getLuxContents() {
        return contents;
    }

    public void setLuxContents(LuxStack other) {
        contents = other;
    }

    @Override
    public float MaxLuxLevel(LuxColor col) {
        return maxLevels.luxLevel(col);
    }

    @Override
    public LuxStack insertLux(LuxStack lux, Transfer simulate) {
        LuxStack ret = new LuxStack();
        for (byte c = 0; c < 8; c++) {
            ret.lux[c] = Math.min(lux.lux[c], maxLevels.lux[c] - contents.lux[c]);
            if (simulate.perform)
                contents.lux[c] += ret.lux[c];
        }
        this.markDirty();
        return ret;
    }

    public String getInventoryName() {
        return hasCustomInventoryName() ? customName : this.getBlockType()
                .getPickBlock(new MovingObjectPosition(xCoord, yCoord, zCoord, 0, Vec3.createVectorHelper(xCoord, yCoord, zCoord)), worldObj, xCoord, yCoord, zCoord).getUnlocalizedName()
                + ".name";
    }

    public boolean hasCustomInventoryName() {
        return !"".equals(customName);
    }

    public void onNeighbourChange() {

    }
}
