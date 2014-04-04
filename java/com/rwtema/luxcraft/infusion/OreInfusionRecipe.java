package com.rwtema.luxcraft.infusion;

import com.rwtema.luxcraft.item.ItemInfusedItems;
import com.rwtema.luxcraft.luxapi.LuxColor;
import com.rwtema.luxcraft.luxapi.LuxStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;

public class OreInfusionRecipe implements IInfusionRecipe {
    public static final ArrayList<String> oreList = new ArrayList<String>();

    static {
        for (String ore : new String[]{null, "oreIron", "oreGold", "oreLapis", "oreDiamond", "oreRedstone", "oreEmerald", "oreQuartz", "oreCoal", "oreCopper", "oreSilver", "oreLead", "oreTin"}) {
            oreList.add(ore);
        }
    }
    String oreDicName;

    public OreInfusionRecipe(String oreDicName) {
        this.oreDicName = oreDicName;

    }

    public static void registerOres() {
        for (String ore : oreList)
            if (ore != null) {
                InfusionRegistry.registerRecipe(new OreInfusionRecipe(ore));
            }
    }

    @Override
    public LuxStack getLux(ItemStack other) {
        return new LuxStack(LuxColor.Red, 200).add(LuxColor.Green, 200).add(LuxColor.Blue, 200);
    }

    @Override
    public boolean matches(ItemStack other) {
        if (other == null)
            return false;

        return OreDictionary.getOreID(other) == OreDictionary.getOreID(oreDicName);
    }

    @Override
    public ItemStack createOutput(ItemStack other) {
        return ItemInfusedItems.create(other);
    }

    @Override
    public ItemStack[] getInputs() {
        return OreDictionary.getOres(oreDicName).toArray(new ItemStack[0]);
    }
}
