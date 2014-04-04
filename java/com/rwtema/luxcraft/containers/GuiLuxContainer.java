package com.rwtema.luxcraft.containers;

import com.rwtema.luxcraft.luxapi.LuxStack;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;

import java.util.Locale;

public abstract class GuiLuxContainer extends GuiContainer {
    private ContainerLuxContainer luxContainer;

    public GuiLuxContainer(InventoryPlayer player, ContainerLuxContainer par1Container) {
        super(par1Container);
        luxContainer = par1Container;

    }

    public static String formatLux(float p) {
        return String.format(Locale.ENGLISH, "%,.3f", p);
    }

    public LuxStack getLux() {
        return luxContainer.getLux();
    }

}
