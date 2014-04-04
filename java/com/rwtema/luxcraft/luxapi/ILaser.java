package com.rwtema.luxcraft.luxapi;

import com.rwtema.luxcraft.tiles.Pos;

import java.util.List;

public interface ILaser {


    public int getLength();

    public List<Pos> getPath();

    public Pos getStart();

    public int getMaxLength();
}
