package com.rwtema.luxcraft.item;

import com.rwtema.luxcraft.LuxcraftCreativeTab;
import com.rwtema.luxcraft.luxapi.LuxColor;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;

import java.util.List;

public class ItemLuxSaber extends ItemSword {

    public static IIcon[] icons = new IIcon[16];
    public static IIcon laserBeam = null;

    public ItemLuxSaber() {
        super(EnumHelper.addToolMaterial("LuxLazer", 1, 2048, 3, 8, 0));

        this.setCreativeTab(LuxcraftCreativeTab.instance);
        this.setUnlocalizedName("luxcraft:luxSaber");
        this.setHasSubtypes(true);
    }

    public static LuxColor getColor(ItemStack item) {
        if (item.hasTagCompound()) {
            int c = item.getTagCompound().getByte("color");
            if (c != 0)
                return LuxColor.col(c);
        }
        return LuxColor.Blue;
    }

    public static ItemStack setColor(ItemStack item, LuxColor col) {
        NBTTagCompound tag = item.getTagCompound();
        if (tag == null)
            tag = new NBTTagCompound();

        tag.setByte("color", col.index);

        item.setTagCompound(tag);

        return item;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean hasEffect(ItemStack par1ItemStack, int pass) {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        laserBeam = register.registerIcon("luxcraft:SaberLaser");
        this.itemIcon = register.registerIcon("luxcraft:SaberBase1");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_) {
        for (LuxColor c : LuxColor.values())
            p_150895_3_.add(setColor(new ItemStack(p_150895_1_, 1), c));
    }

    public void onUpdate(ItemStack item, World world, Entity ent, int par4, boolean par5) {

    }

}
