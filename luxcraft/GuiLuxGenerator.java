package luxcraft;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiLuxGenerator extends GuiContainer
{
	private byte color;
    private TileEntityLuxGenerator generatorInventory;
    private int displayLuxLevel;

    public GuiLuxGenerator(InventoryPlayer par1InventoryPlayer, TileEntityLuxGenerator par2TileEntityLuxGenerator)
    {
        super(new ContainerLuxGenerator(par1InventoryPlayer, par2TileEntityLuxGenerator));
        this.generatorInventory = par2TileEntityLuxGenerator;
        this.color = generatorInventory.generatingColor();
        displayLuxLevel = this.generatorInventory.GetLuxLevel(this.color);
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.fontRenderer.drawString(StatCollector.translateToLocal("tile.luxGenerator."+LuxHelper.instance.color_name[color]+".name"), 60, 6, 4210752);
        this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
        String temp = LuxHelper.instance.color_abb[color] + " " + LuxHelper.display(generatorInventory.GetLuxLevel(color));
        this.fontRenderer.drawString(temp, 123-this.fontRenderer.getStringWidth(temp)/2, 28,  LuxHelper.instance.color_int[color]);
        
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        int var4 = this.mc.renderEngine.getTexture("/luxcraft/gui/luxGenerator.png");
        GL11.glColor4f(1.0F, 1F, 1F, 1.0F);
        this.mc.renderEngine.bindTexture(var4);
        int var5 = (this.width - this.xSize) / 2;
        int var6 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
        int var7;
        byte i = color;
        GL11.glColor4d(LuxHelper.r[i], LuxHelper.g[i], LuxHelper.b[i], 1);
        var7 = (int)((double)generatorInventory.GetLuxLevel(i)/(double)generatorInventory.MaxLuxLevel(i)*60);

        this.drawTexturedModalRect(var5 + 99, var6 + 40, 176, 31, var7, 6);
    }
}
