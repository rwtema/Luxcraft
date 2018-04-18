package com.rwtema.luxcraft.item;

import com.rwtema.luxcraft.LuxcraftCreativeTab;
import com.rwtema.luxcraft.render.RenderCrystal;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class ItemCrystal extends Item {
    public ItemCrystal() {
        this.setHasSubtypes(true);
        this.setCreativeTab(LuxcraftCreativeTab.instance);
        this.setTextureName("luxcraft:crystal_quad");
        this.setUnlocalizedName("luxcraft:crystal");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getSpriteNumber() {
        return 0;
    }

    @Override
    public void registerIcons(IIconRegister register) {
        super.registerIcons(register);
        RenderCrystal.crystal_tri = register.registerIcon("luxcraft:crystal_tri");
        RenderCrystal.crystal_quad = this.itemIcon;
        RenderCrystal.crystal_interior=register.registerIcon("luxcraft:crystal_interior");
    }
}
