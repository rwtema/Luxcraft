package luxcraft;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiLuxStorage extends GuiContainer
{
	private TileEntityLuxStorage storageInventory;

	public GuiLuxStorage(TileEntityLuxStorage par2TileEntityLuxStorage)
	{
		super(new ContainerLuxStorage(par2TileEntityLuxStorage));
		this.storageInventory = par2TileEntityLuxStorage;
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 */
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		String temp = StatCollector.translateToLocal("tile.luxStorage.name");
		this.fontRenderer.drawString(temp, (this.xSize-this.fontRenderer.getStringWidth(temp))/2, 6, 4210752);
		
		int var5 = (this.width - this.xSize) / 2;
		int var6 = (this.height - this.ySize) / 2;
		
		int mouse_x = par1-var5;
		int mouse_y = par2-var6;
		
		
		
		if(mouse_y>27 & mouse_y < 27+131){
			int dx = (mouse_x-9)/23;
			if(dx>=0 & dx<7){
				temp = LuxHelper.color_name[dx] + " " + LuxHelper.display(storageInventory.GetLuxLevel((byte)dx));
				this.fontRenderer.drawString(temp, (this.xSize-this.fontRenderer.getStringWidth(temp))/2, 16, LuxHelper.color_int[dx]);
				
			}
		}
	}

	/**
	 * Draw the background layer for the GuiContainer (everything behind the items)
	 */
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		int var4 = this.mc.renderEngine.getTexture("/luxcraft/gui/luxStorage.png");
		GL11.glColor4f(1.0F, 1F, 1F, 1.0F);
		this.mc.renderEngine.bindTexture(var4);
		int var5 = (this.width - this.xSize) / 2;
		int var6 = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
		int var7;

		for(byte i = 0;i< 7;i++){
			GL11.glColor4d(LuxHelper.r[i], LuxHelper.g[i], LuxHelper.b[i], 1);
			var7 = (int)((double)storageInventory.GetLuxLevel(i)/(double)storageInventory.MaxLuxLevel(i)*131);
			this.drawTexturedModalRect(var5 + 9 + 23 * i , var6 + 27 + 131 -var7, 176, 131 - var7, 20, var7);
		}

	}
}
