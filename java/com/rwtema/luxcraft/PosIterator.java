package com.rwtema.luxcraft;

import java.util.Iterator;

import com.rwtema.luxcraft.tiles.Pos;

public class PosIterator implements Iterator<Pos>, Iterable<Pos> {
	private final int size;
	private final Pos center;
	private int dx, dy, dz;

	public PosIterator(int x, int y, int z, int size) {
		this(new Pos(x, y, z), size);
	}

	public PosIterator(Pos center, int size) {
		super();
		this.size = size;
		this.center = center;
		this.dx = -size;
		this.dy = -size;
		this.dz = -size;
	}

	@Override
	public boolean hasNext() {
		return dy < size;
	}

	@Override
	public Pos next() {
		dx++;
		if (dx > size) {
			dx = -size;
			dz++;
			if (dz > size) {
				dz = -size;
				dy++;
			}
		}

		return new Pos(center.x + dx, center.y + dy, center.z + dz);
	}

	@Override
	public void remove() {

	}

	@Override
	public Iterator<Pos> iterator() {
		return this;
	}

}
