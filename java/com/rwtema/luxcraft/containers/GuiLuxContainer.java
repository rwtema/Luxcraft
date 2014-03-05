package com.rwtema.luxcraft.containers;

import java.util.Locale;

import net.minecraft.client.gui.inventory.GuiContainer;

import com.rwtema.luxcraft.luxapi.LuxColor;
import com.rwtema.luxcraft.luxapi.LuxStack;

public abstract class GuiLuxContainer extends GuiContainer {
	private ContainerLuxContainer luxContainer;

	public GuiLuxContainer(ContainerLuxContainer par1Container) {
		super(par1Container);
		luxContainer = par1Container;

	}

	public static String formatLux(float p) {
		return String.format(Locale.ENGLISH, "%,.3d", p);
	}

	public LuxStack getLux() {
		return luxContainer.getLux();
	}

}
