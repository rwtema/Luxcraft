package com.rwtema.luxcraft;

import com.rwtema.luxcraft.luxapi.LuxStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class LuxLevelsEntry {
    private static Random cols = new Random();
    public Item item;
    public int metadata;
    public LuxStack lux;
    public boolean constructable;
    public int color;

    public LuxLevelsEntry(ItemStack par1, LuxStack par2, boolean constructable) {
        this(par1.getItem(), par1.getItemDamage(), par2, constructable);

    }

    public LuxLevelsEntry(Item par1, LuxStack par2, boolean constructable) {
        this(par1, -1, par2, constructable);
    }

    public LuxLevelsEntry(Item itemId, int meta_data, LuxStack par2,
                          boolean constructable) {
        item = itemId;
        metadata = meta_data;
        lux = par2;
        this.constructable = constructable;
        color = cols.nextInt(256 * 256 * 256);
    }

    public boolean itemMatches(ItemStack otherItem) {
        if (item == otherItem.getItem()) {
            if (item.getHasSubtypes() & otherItem.getItemDamage() != -1)
                return otherItem.getItemDamage() == metadata;
            else
                return true;
        }
        return false;
    }

    public LuxStack getLuxPacket() {
        return lux;
    }

}
