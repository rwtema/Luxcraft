package luxcraft;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiLuxRitualTable extends GuiContainer
{
	private TileEntityLuxRitualTable ritualtableInventory;
	private int displayLuxLevel;

	public GuiLuxRitualTable(InventoryPlayer par1InventoryPlayer, TileEntityLuxRitualTable par2TileEntityLuxRitualTable)
	{
		super(new ContainerLuxRitualTable(par1InventoryPlayer, par2TileEntityLuxRitualTable));
		this.ritualtableInventory = par2TileEntityLuxRitualTable;

	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 */
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);

		ritualtableInventory.getRitualStrength();
		ritualtableInventory.getMaxRitualStrength();
		String temp = "Ritual Table";

		this.fontRenderer.drawString(temp, (this.xSize-this.fontRenderer.getStringWidth(temp))/2, 16, 4210752);

		if(ritualtableInventory.getMaxRitualStrength()>0){
			temp = "Ritual Square Strength " + ritualtableInventory.getRitualStrength() + "/" + ritualtableInventory.getMaxRitualStrength();
			this.fontRenderer.drawString(temp, (this.xSize-this.fontRenderer.getStringWidth(temp))/2, 60, 4210752);
		}
	}

	/**
	 * Draw the background layer for the GuiContainer (everything behind the items)
	 */
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		int var4 = this.mc.renderEngine.getTexture("/luxcraft/gui/luxRitualTable.png");
		GL11.glColor4f(1.0F, 1F, 1F, 1.0F);
		this.mc.renderEngine.bindTexture(var4);
		int var5 = (this.width - this.xSize) / 2;
		int var6 = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);

		if(this.ritualtableInventory.schemaMaxAnalyseTime != 0){
			int w = 2+(this.ritualtableInventory.schemaAnalyseTime * 41)/this.ritualtableInventory.schemaMaxAnalyseTime;
			this.drawTexturedModalRect(var5 + 30, var6 + 37, 176, 14, w, 16);
			this.drawTexturedModalRect(var5 + 103+43-w, var6 + 37, 176+43-w, 31, w, 16);
		}


	}
}
