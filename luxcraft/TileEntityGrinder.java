package luxcraft;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

public class TileEntityGrinder  extends TileEntity
{
    public float spin_angle = 0, prevspin_angle = 0, prevtime = 0;

    public void updateEntity()
    {
        super.updateEntity();

        if (prevtime == 0)
        {
            prevtime = this.getWorldObj().getWorldTime();
        }
        else
        {
            prevspin_angle = spin_angle;

            if (!this.getWorldObj().isBlockSolidOnSide(xCoord, yCoord - 1, zCoord, ForgeDirection.UP))
            {
                spin_angle += Math.max(this.getWorldObj().getWorldTime() - prevtime, 0);
            }

            prevtime = this.getWorldObj().getWorldTime();
        }
    }
}
