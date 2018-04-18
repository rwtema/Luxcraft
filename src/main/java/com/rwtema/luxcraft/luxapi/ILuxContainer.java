package com.rwtema.luxcraft.luxapi;

public interface ILuxContainer {

    LuxStack getLuxContents();

    float MaxLuxLevel(LuxColor color);

    LuxStack insertLux(LuxStack lux, Transfer simulate);

}
