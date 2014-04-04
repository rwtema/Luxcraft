package com.rwtema.luxcraft.luxapi;

public interface ILuxContainer {

    public LuxStack getLuxContents();

    public float MaxLuxLevel(LuxColor color);

    public LuxStack insertLux(LuxStack lux, Transfer simulate);

}
