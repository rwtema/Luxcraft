package luxcraft;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiLuxAssembler extends GuiContainer
{
	private TileEntityLuxSynthesizer AssemblerInventory;

	public GuiLuxAssembler(InventoryPlayer par1InventoryPlayer, TileEntityLuxSynthesizer par2TileEntityLuxAssembler)
	{
		super(new ContainerLuxAssembler(par1InventoryPlayer, par2TileEntityLuxAssembler));
		this.AssemblerInventory = par2TileEntityLuxAssembler;
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 */
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		
		for(byte c = 0;c < 7;c++){
			String temp = LuxHelper.display(AssemblerInventory.GetLuxLevel(c))+"/"+LuxHelper.display(AssemblerInventory.curItemCost(c));
			this.fontRenderer.drawString(temp, 105-this.fontRenderer.getStringWidth(temp), 6 + 11 * c, LuxHelper.color_int[c]);			
		}
		
//		String temp = StatCollector.translateToLocal("tile.luxAssembler.name");
//		this.fontRenderer.drawString(temp, (this.xSize-this.fontRenderer.getStringWidth(temp))/2, 6, 4210752);

//		int var5 = (this.width - this.xSize) / 2;
//		int var6 = (this.height - this.ySize) / 2;

//		int mouse_x = par1-var5;
//		int mouse_y = par2-var6;

//		if(mouse_y>27 & mouse_y < 27+131){
//			int dx = (mouse_x-9)/23;
//			if(dx>=0 & dx<7){
//				temp = LuxHelper.color_name[dx] + " " + LuxHelper.display(AssemblerInventory.GetLuxLevel((byte)dx));
//				this.fontRenderer.drawString(temp, (this.xSize-this.fontRenderer.getStringWidth(temp))/2, 16, LuxHelper.color_int[dx]);
//
//			}
//		}
		
		
	}

	/**
	 * Draw the background layer for the GuiContainer (everything behind the items)
	 */
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		int var4 = this.mc.renderEngine.getTexture("/luxcraft/gui/luxSynthesizer.png");
		GL11.glColor4f(1.0F, 1F, 1F, 1.0F);
		this.mc.renderEngine.bindTexture(var4);
		int var5 = (this.width - this.xSize) / 2;
		int var6 = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
		int var7;

		for(byte i = 0;i< 7;i++){
			
			if(AssemblerInventory.curItemCost(i) > 0){
				GL11.glColor4d(1, 1, 1, 1);
				this.drawTexturedModalRect(var5 + 107, var6 + 6 + 11 * i, 176, 37, 62, 8);
				
				GL11.glColor4d(LuxHelper.r[i], LuxHelper.g[i], LuxHelper.b[i], 1);
				var7 = (int)(Math.min((double)AssemblerInventory.GetLuxLevel(i)/(double)AssemblerInventory.curItemCost(i),1)*60);
				this.drawTexturedModalRect(var5 + 108, var6 + 7 + 11 * i, 176, 31, var7, 6);
			}
		}

	}
}
