package luxcraft;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

public class LaserBeam {
	World world;
	int xstart, ystart, zstart;
	List<Double> pathx = new ArrayList<Double>();
	List<Double> pathy = new ArrayList<Double>();
	List<Double> pathz = new ArrayList<Double>();

	public int x, y, z, direction, dir;
	public float r, g, b, alpha, beamsize, noise_size, noise_momentum;
	public boolean started, simulating;
	public List collidedEntities;

	public LaserBeam(World world, int xstart, int ystart, int zstart, int direction, float r, float g, float b, float alpha, float beamsize, float noise_size, float noise_momentum){
		this.world=world;
		this.xstart=xstart;
		this.ystart=ystart;
		this.zstart=zstart;
		this.direction=direction;
		this.r=r;this.g=g;this.b=b;
		this.alpha=alpha;
		this.beamsize=beamsize;
		this.noise_momentum=noise_momentum;
		this.noise_size=noise_size;

		this.simulating=false;


		this.clear();
	}
	
	public int size(){
		return pathx.size();
	}

	public void clear(){
		pathx.clear();pathy.clear();pathz.clear();
		pathx.add(xstart+0.5-Facing.offsetsXForSide[direction]*0.5);
		pathy.add(ystart+0.5-Facing.offsetsYForSide[direction]*0.5);
		pathz.add(zstart+0.5-Facing.offsetsZForSide[direction]*0.5);
		collidedEntities = null;

		x=xstart;y=ystart;z=zstart;dir=direction;
		started=false;
	}

	public void addDetourPoint(int dx, int dy, int dz){
		started=true;
		pathx.add(dx+0.5);pathy.add(dy+0.5);pathz.add(dz+0.5);
		//pathx.add(x+0.5);pathy.add(y+0.5);pathz.add(z+0.5);
	}

	public boolean advance(boolean force){
		started=true;

		if(!(x==xstart & y == ystart & z == zstart) & !force & this.world.getBlockLightOpacity(x, y, z) >= 2){
			return false;
		}

		if(this.world.getBlockId(x, y, z) != 0){
			if(Block.blocksList[this.world.getBlockId(x, y, z)] instanceof IReflector){
				dir = ((IReflector)Block.blocksList[this.world.getBlockId(x, y, z)]).newDir(dir, this.world.getBlockMetadata(x, y, z));
			}
		}

	//	collidedEntities=null;

		double h = 0.02;
		AxisAlignedBB bb = AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(x-h,y-h,z-h,x+h,y+h,z+h).addCoord(Facing.offsetsXForSide[dir], Facing.offsetsYForSide[dir], Facing.offsetsZForSide[dir]);
		bb.offset(0.5, 0.5, 0.5);

		collidedEntities = world.getEntitiesWithinAABB(EntityLiving.class, bb); 


		x+=Facing.offsetsXForSide[dir];
		y+=Facing.offsetsYForSide[dir];
		z+=Facing.offsetsZForSide[dir];

		pathx.add(x+0.5);
		pathy.add(y+0.5);
		pathz.add(z+0.5);



		return true;

	}
}
