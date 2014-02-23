package com.rwtema.luxcraft.containers;

import net.minecraft.client.gui.inventory.GuiContainer;

import com.rwtema.luxcraft.luxapi.LuxStack;

public abstract class GuiLuxContainer extends GuiContainer {
	private ContainerLuxContainer luxContainer;

	public GuiLuxContainer(ContainerLuxContainer par1Container) {
		super(par1Container);
		luxContainer = par1Container;

	}

	public LuxStack getLux() {
		return luxContainer.getLux();
	}

}
