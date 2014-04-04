package com.rwtema.luxcraft;

import com.rwtema.luxcraft.luxapi.LuxStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LuxLevels {
    public static LuxLevels instance = new LuxLevels();
    private List<LuxLevelsEntry> luxEntries = new ArrayList<LuxLevelsEntry>();
    private Random cols = new Random(0);

    public void initLevels() {

    }

    public void addLuxEntry(ItemStack par1, LuxStack lux, boolean deconstructable) {
        luxEntries.add(new LuxLevelsEntry(par1, lux, deconstructable));
    }

    public void addLuxEntry(Item itemId, LuxStack lux, boolean deconstructable) {
        luxEntries.add(new LuxLevelsEntry(itemId, lux, deconstructable));
    }

    public void addLuxEntry(Item itemId, int metadata, LuxStack lux, boolean deconstructable) {
        luxEntries.add(new LuxLevelsEntry(itemId, metadata, lux, deconstructable));
    }

    public void addLuxEntry(LuxLevelsEntry par1) {
        luxEntries.add(par1);
    }

    public LuxStack GetLuxPacket(ItemStack par1) {
        return null;
    }

}
