package com.rwtema.luxcraft.item;

import com.rwtema.luxcraft.LuxcraftCreativeTab;
import com.rwtema.luxcraft.luxapi.LuxColor;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class ItemLuxGem extends Item {
    IIcon[] icons = new IIcon[2];

    public ItemLuxGem() {
        this.setCreativeTab(LuxcraftCreativeTab.instance);
        this.setUnlocalizedName("luxcraft:luxGem");
        this.setHasSubtypes(true);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIconFromDamageForRenderPass(int par1, int par2) {
        return icons[par2 % 2];
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getColorFromItemStack(ItemStack item, int par2) {
        return par2 == 1 ? super.getColorFromItemStack(item, 0) : LuxColor.col(item.getItemDamage() % 8).displayColor();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_) {
        for (LuxColor c : LuxColor.values())
            p_150895_3_.add(new ItemStack(p_150895_1_, 1, c.index));
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        icons[0] = register.registerIcon("luxcraft:luxGem");
        icons[1] = register.registerIcon("luxcraft:luxGem_exterior");
    }

    @Override
    public int getMetadata(int par1) {
        return par1;
    }

    @Override
    public String getUnlocalizedName(ItemStack par1ItemStack) {
        return super.getUnlocalizedName() + "." + par1ItemStack.getItemDamage();
    }

}
