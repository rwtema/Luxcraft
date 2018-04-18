package com.rwtema.luxcraft.itemlevels;

import com.rwtema.luxcraft.StackHelper;
import com.rwtema.luxcraft.luxapi.LuxColor;
import com.rwtema.luxcraft.luxapi.LuxStack;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

public class LuxRegister {

    private static HashMap<Material, LuxStack> materialLuxStackHashMap = new HashMap<Material, LuxStack>();

    static {
        materialLuxStackHashMap.put(Material.air, new LuxStack());
        materialLuxStackHashMap.put(Material.rock, new LuxStack(LuxColor.White, 1));
        materialLuxStackHashMap.put(Material.wood, new LuxStack(LuxColor.White, 0.25, LuxColor.Green, 1));
        materialLuxStackHashMap.put(Material.water, new LuxStack(LuxColor.Blue, 1));
        materialLuxStackHashMap.put(Material.cloth, new LuxStack(LuxColor.White, 1, new LuxStack(LuxColor.Blue, 1)));
    }

    public LuxStack toSynthesize(ItemStack item) {
        if (StackHelper.isNull(item))
            return null;
        return null;
    }

    public LuxStack burn(ItemStack item) {
        return null;
    }

    public LuxStack addBaseLux(ItemStack item) {
        if (StackHelper.isNull(item))
            return null;

        LuxStack t = new LuxStack();

        if (item.getItem() instanceof ItemBlock) {
            ((ItemBlock) item.getItem()).field_150939_a.getMaterial();
        }
        //t.add(burn(TileEntityFurnace.getItemBurnTime(item)));

        return t;
    }

    public LuxStack addMaterialLux(Material material) {

        return new LuxStack();
    }


}
