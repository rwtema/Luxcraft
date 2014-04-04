package com.rwtema.luxcraft.tiles;

import com.rwtema.luxcraft.luxapi.ILaser;
import com.rwtema.luxcraft.luxapi.IReflector;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LaserBeam implements Iterator<Pos>, ILaser, java.lang.Iterable<Pos> {

    public final Pos start;
    private final ArrayList<Pos> path = new ArrayList<Pos>();
    public List collidedEntities = null;
    public Pos pos = new Pos();
    public float beamsize;
    public boolean started, simulating;
    public float damage;
    public World world;
    public int maxLength;
    public int curPos;
    public boolean changed = false;
    ForgeDirection initdir, dir = null;
    private boolean finished;

    public LaserBeam(World world, int xstart, int ystart, int zstart, ForgeDirection direction, LaserType type) {
        this.world = world;

        this.start = new Pos(xstart, ystart, zstart);

        this.initdir = direction;

        this.maxLength = type.range;

        path.ensureCapacity(this.maxLength);

        while (path.size() < this.maxLength)
            path.add(null);

        curPos = 0;

        this.beamsize = type.size;

        this.damage = type.damage;

        this.finished = false;

        startProjection();
    }

    public boolean advance() {

        return true;
    }

    public LaserBeam startProjection() {
        finished = false;
        collidedEntities = null;

        dir = initdir;
        pos.x = start.x;
        pos.y = start.y;
        pos.z = start.z;
        changed = false;
        path.set(0, pos);
        curPos = 1;

        return this;
    }

    @Override
    public boolean hasNext() {

        if (finished) {
            checkFinished();
            return false;
        }

        pos = pos.copy().advance(dir);
        //
        // if (pos.equals(start)) {
        // setFinished();
        // return true;
        // }

        if (curPos >= this.getMaxLength()) {
            setFinished();
            checkFinished();
            return false;
        }
//
//		if (!world.blockExists(pos.x, pos.y, pos.z)) {
//			setFinished();
//			checkFinished();
//			return false;
//		}

        if (world.getBlockLightOpacity(pos.x, pos.y, pos.z) >= 2) {
            setFinished();
            return true;
        }

        return true;
    }

    public boolean isChanged() {
        return changed;
    }

    public void setFinished() {
        finished = true;
    }

    public void checkFinished() {
        if (curPos < this.getMaxLength() && path.get(curPos) != null) {
            changed = true;
            for (int i = curPos; i < path.size(); i++) {
                path.set(i, null);
            }
        }
    }

    @Override
    public Pos next() {
        Block b;
        if ((b = world.getBlock(pos.x, pos.y, pos.z)) instanceof IReflector)
            dir = ((IReflector) b).newDir(dir, world, pos.x, pos.y, pos.z, this);
        Pos t = path.get(curPos);
        if (t == null || !pos.equals(t))
            changed = true;

        path.set(curPos, pos);
        curPos++;

        return pos;
    }

    public Pos getPos() {
        return pos;
    }

    @Override
    public void remove() {
        setFinished();
        checkFinished();
    }

    @Override
    public int getLength() {
        return curPos;
    }

    public int getEffectiveLength() {
        int l = curPos;
        return l >= 2 ? l : 0;
    }

    @Override
    public int getMaxLength() {
        return this.maxLength;
    }

    public void setMaxLength(int length) {
        this.maxLength = length;
    }

    @Override
    public List<Pos> getPath() {
        return path.subList(0, curPos);
    }

    public void preCalcFullPath() {
        LaserBeam laser = this.startProjection();
        while (laser.hasNext())
            laser.next();
    }

    @Override
    public Pos getStart() {
        return start;
    }

    @Override
    public Iterator<Pos> iterator() {
        return this.startProjection();
    }

}
