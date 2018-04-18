package com.rwtema.luxcraft.item;

import com.rwtema.luxcraft.LuxcraftCreativeTab;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class ItemDecorative extends Item {
    private final String[] items;
    private final IIcon[] icons;

    public ItemDecorative(String... items) {
        this.setCreativeTab(LuxcraftCreativeTab.instance);
        this.items = items;
        this.icons = new IIcon[items.length];
        this.setHasSubtypes(true);
        this.setUnlocalizedName("luxcraft:decorative");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int damage) {
        if (damage < 0 || damage >= items.length)
            damage = 0;
        return icons[damage];
    }

    @SuppressWarnings("unchecked")
    @Override
    public void getSubItems(Item item, CreativeTabs p_150895_2_, List list) {
        for (int i = 1; i < items.length; i++)
            list.add(new ItemStack(item, 1, i));
    }

    @Override
    public void registerIcons(IIconRegister register) {
        for (int i = 0; i < items.length; i++)
            icons[i] = register.registerIcon(items[i]);
        this.itemIcon = icons[0];
    }

    @Override
    public String getUnlocalizedName(ItemStack par1ItemStack) {
        int damage = par1ItemStack.getItemDamage();
        if (damage < 0 || damage >= items.length)
            damage = 0;
        return "item." + items[damage];
    }
}
