package com.rwtema.luxcraft.luxapi;

import java.util.List;

import com.rwtema.luxcraft.tiles.Pos;

public interface ILaser {
	public int getLength();
	
	public List<Pos> getPath();
	
	public Pos getStart();
	
	public int getMaxLength();
}
