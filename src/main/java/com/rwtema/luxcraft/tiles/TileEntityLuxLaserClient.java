package com.rwtema.luxcraft.tiles;

import com.rwtema.luxcraft.block.BlockLuxLaser;
import com.rwtema.luxcraft.luxapi.LuxColor;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;

@SideOnly(Side.CLIENT)
public class TileEntityLuxLaserClient extends TileEntity {

    public int[] laserLengths = null;
    private LaserBeamClient path = null;

    @Override
    public boolean canUpdate() {
        return false;
    }

    public LaserBeamClient getClientPath() {
        if (path == null && laserLengths != null) {
            path = new LaserBeamClient(worldObj, xCoord, yCoord, zCoord, BlockLuxLaser.getDirection(this.getBlockMetadata()), BlockLuxLaser.getLaser(this.getBlockMetadata()), laserLengths);
            path.preCalcFullPath();
        }
        return path;
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        laserLengths = new int[LuxColor.n];
        for (LuxColor col : LuxColor.values())
            laserLengths[col.index] = pkt.func_148857_g().getInteger(col.shortname);

        path = null;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }

    public boolean shouldRenderInPass(int pass) {
        return pass == 1;
    }
}
