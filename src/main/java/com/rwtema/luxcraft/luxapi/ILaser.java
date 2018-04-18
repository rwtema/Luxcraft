package com.rwtema.luxcraft.luxapi;

import com.rwtema.luxcraft.tiles.Pos;

import java.util.List;

public interface ILaser {


    int getLength();

    List<Pos> getPath();

    Pos getStart();

    int getMaxLength();
}
