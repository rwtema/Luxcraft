package luxcraft;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.tileentity.TileEntity;

public abstract class TileEntityLaser extends TileEntity {
	public List<LaserBeam> laserBeams = new ArrayList<LaserBeam>();
	public abstract void calcTransmissionLuxPacket();
	
	public  List<LaserBeam> getBeams() {
		return laserBeams;
	}
}
