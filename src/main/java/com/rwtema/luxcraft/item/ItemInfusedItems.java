package com.rwtema.luxcraft.item;

import com.rwtema.luxcraft.Luxcraft;
import com.rwtema.luxcraft.LuxcraftCreativeTab;
import com.rwtema.luxcraft.infusion.OreInfusionRecipe;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

public class ItemInfusedItems extends Item {

    public static IIcon glow;

    public ItemInfusedItems() {
        this.setCreativeTab(LuxcraftCreativeTab.instance);
        this.setUnlocalizedName("luxcraft:luxInfused");
        this.setTextureName("luxcraft:unknown");
        this.setHasSubtypes(true);
    }

    public static ItemStack create(int damage, int stacksize, ItemStack renderitem) {
        ItemStack item = new ItemStack(Luxcraft.luxInfusedItem, stacksize, damage);
        NBTTagCompound tag = new NBTTagCompound();
        renderitem.writeToNBT(tag);
        NBTTagCompound tag2 = new NBTTagCompound();
        tag2.setTag("render_item", tag);
        item.setTagCompound(tag2);
        return item;
    }

    public static ItemStack create(ItemStack baseitem) {
        int id = OreInfusionRecipe.oreList.indexOf(OreDictionary.getOreName(OreDictionary.getOreID(baseitem)));
        if (id < 0)
            id = 0;

        return create(id, 1, baseitem);
    }

    public static ItemStack getRenderItem(ItemStack item) {
        if (item.hasTagCompound()) {
            return ItemStack.loadItemStackFromNBT(item.getTagCompound().getCompoundTag("render_item"));
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_) {
        p_150895_3_.add(new ItemStack(p_150895_1_, 1, 0));
        for (int i = 1; i < OreInfusionRecipe.oreList.size(); i++) {
            List<ItemStack> items = OreDictionary.getOres(OreInfusionRecipe.oreList.get(i));
            if (items.size() > 0) {
                p_150895_3_.add(create(items.get(0)));
            }
        }

    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister) {
        super.registerIcons(par1IconRegister);
        glow = par1IconRegister.registerIcon("luxcraft:infusionglow");
    }

}
