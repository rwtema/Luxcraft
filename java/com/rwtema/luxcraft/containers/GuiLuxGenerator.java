package com.rwtema.luxcraft.containers;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import com.rwtema.luxcraft.LuxHelper;
import com.rwtema.luxcraft.luxapi.LuxColor;
import com.rwtema.luxcraft.tiles.TileEntityLuxGenerator;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiLuxGenerator extends GuiLuxContainer {
	private LuxColor color;

	public GuiLuxGenerator(InventoryPlayer par1InventoryPlayer, TileEntityLuxGenerator gen) {
		super(new ContainerLuxGenerator(par1InventoryPlayer, gen));
		color = gen.getType();
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of
	 * the items)
	 */
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		this.fontRendererObj.drawString(StatCollector.translateToLocal("tile.luxcraft:luxGenerator." + color.index + ".name"), 60, 6, 4210752);
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
		String temp = color.getLocalizedName() + ": " + getLux().totalLux();
		this.fontRendererObj.drawString(temp, 123 - this.fontRendererObj.getStringWidth(temp) / 2, 28, color.displayColor());

	}

	ResourceLocation background = new ResourceLocation("luxcraft", "textures/gui/luxGenerator.png");

	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {

		GL11.glColor4f(1.0F, 1F, 1F, 1.0F);
		this.mc.renderEngine.bindTexture(background);
		int var5 = (this.width - this.xSize) / 2;
		int var6 = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
		int var7;
		byte i = color.index;
		GL11.glColor4d(color.r, color.g, color.b, 1);
		var7 = (int) (getLux().lux[i] / TileEntityLuxGenerator.maxLevel * 60);

		this.drawTexturedModalRect(var5 + 99, var6 + 40, 176, 31, var7, 6);
	}
}
