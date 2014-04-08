package com.rwtema.luxcraft.block.fluid;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.MaterialLiquid;

public class MaterialCustomLiquid extends MaterialLiquid {
    public static MaterialCustomLiquid instance = new MaterialCustomLiquid();

    public MaterialCustomLiquid() {
        super(MapColor.waterColor);
    }

    public boolean isLiquid()
    {
        return true;
    }
}
