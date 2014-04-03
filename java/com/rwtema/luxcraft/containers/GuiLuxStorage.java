package com.rwtema.luxcraft.containers;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import com.rwtema.luxcraft.luxapi.LuxColor;
import com.rwtema.luxcraft.tiles.TileEntityLuxStorage;

public class GuiLuxStorage extends GuiLuxContainer {
	private TileEntityLuxStorage storageInventory;

	public GuiLuxStorage(InventoryPlayer player, TileEntityLuxStorage par2TileEntityLuxStorage) {
		super(player, new ContainerLuxStorage(player, par2TileEntityLuxStorage));
		storageInventory = par2TileEntityLuxStorage;
		this.xSize = 198;
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of
	 * the items)
	 */
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		String temp = StatCollector.translateToLocal("tile.luxStorage.name");
		this.fontRendererObj.drawString(temp, (this.xSize - this.fontRendererObj.getStringWidth(temp)) / 2, 6, 4210752);

		int var5 = (this.width - this.xSize) / 2;
		int var6 = (this.height - this.ySize) / 2;

		int mouse_x = par1 - var5;
		int mouse_y = par2 - var6;

		if (mouse_y > 27 & mouse_y < 27 + 131) {
			int dx = (mouse_x - 9) / 23;
			if (dx >= 0 & dx < 7) {
				LuxColor c = LuxColor.col(dx);
				temp = c.getLocalizedName() + ": " + formatLux(getLux().luxLevel(c));
				this.fontRendererObj.drawString(temp, (this.xSize - this.fontRendererObj.getStringWidth(temp)) / 2, 16, c.displayColor());

			}
		}
	}

	ResourceLocation var4 = new ResourceLocation("luxcraft", "textures/gui/luxStorage.png");

	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		GL11.glColor4f(1.0F, 1F, 1F, 1.0F);
		this.mc.renderEngine.bindTexture(var4);
		int var5 = (this.width - this.xSize) / 2;
		int var6 = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
		int h;

		for (byte i = 0; i < 8; i++) {
			LuxColor c = LuxColor.col(i);
			GL11.glColor4d(c.r, c.g, c.b, 1);
			h = (int) (getLux().luxLevel(c) / storageInventory.MaxLuxLevel(c) * 131);
			this.drawTexturedModalRect(var5 + 9 + 23 * i, var6 + 27 + 131 - h, 199, 131 - h, 20, h);
		}

	}
}
