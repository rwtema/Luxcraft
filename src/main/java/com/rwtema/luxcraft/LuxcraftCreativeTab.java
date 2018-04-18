package com.rwtema.luxcraft;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class LuxcraftCreativeTab extends CreativeTabs {

    public static CreativeTabs instance = new LuxcraftCreativeTab();

    public LuxcraftCreativeTab() {
        super("luxcraft");

    }

    @Override
    @SideOnly(Side.CLIENT)
    public Item getTabIconItem() {
        return Item.getItemFromBlock(Luxcraft.luxGenerator);
    }

}
