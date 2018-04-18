package com.rwtema.luxcraft.containers;

import com.rwtema.luxcraft.luxapi.LuxColor;
import com.rwtema.luxcraft.tiles.TileEntityLuxGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiLuxGenerator extends GuiLuxContainer {
    ResourceLocation background = new ResourceLocation("luxcraft", "textures/gui/luxGenerator.png");
    private LuxColor color;
    private IInventory gen;

    public GuiLuxGenerator(InventoryPlayer player, TileEntityLuxGenerator gen) {
        super(player, new ContainerLuxGenerator(player, gen));
        this.gen = gen;
        color = gen.getType();
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of
     * the items)
     */
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        this.fontRendererObj.drawString(StatCollector.translateToLocal(gen.getInventoryName()), 8, 6, 4210752);
        this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
        String temp = color.getLocalizedName() + ": " + formatLux(getLux().totalLux());
        this.fontRendererObj.drawString(temp, 123 - this.fontRendererObj.getStringWidth(temp) / 2, 28, color.displayColor());

    }

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
