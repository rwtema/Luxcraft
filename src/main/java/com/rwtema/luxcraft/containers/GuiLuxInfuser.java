package com.rwtema.luxcraft.containers;

import com.rwtema.luxcraft.luxapi.LuxColor;
import com.rwtema.luxcraft.tiles.TileEntityLuxInfuser;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class GuiLuxInfuser extends GuiLuxContainer {
    public static final float sx = 0.00390625F;
    public static final float sy = 0.00390625F;
    public final static double[] sep = new double[8];
    static {
        for (int i = 0; i < sep.length; i++) {
            sep[i] = i * (2 * Math.PI / sep.length);
        }
    }
    ResourceLocation var4 = new ResourceLocation("luxcraft", "textures/gui/luxInfuser.png");
    private TileEntityLuxInfuser infuser;
    public GuiLuxInfuser(InventoryPlayer player, TileEntityLuxInfuser par2TileEntityLuxStorage) {
        super(player, new ContainerLuxInfuser(player, par2TileEntityLuxStorage));
        infuser = par2TileEntityLuxStorage;
        this.ySize = 215;
    }

    public static double nextPoint(double start, double end) {
        for (double s : sep) {
            if (s > start)
                return s < end ? s : end;
        }
        return end;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of
     * the items)
     */
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        this.fontRendererObj.drawString(StatCollector.translateToLocal(infuser.getInventoryName()), 8, 6, 4210752);
        this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }

    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        GL11.glColor4f(1.0F, 1F, 1F, 1.0F);
        this.mc.renderEngine.bindTexture(var4);
        int var5 = (this.width - this.xSize) / 2;
        int var6 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);

        infuser.checkRecipe();

        double total = 0;
        for (LuxColor col : LuxColor.values()) {
            if (infuser.MaxLuxLevel(col) > 0)
                total += infuser.MaxLuxLevel(col);
        }

        double ang = 0;
        if (total > 0)
            for (LuxColor col : LuxColor.values()) {

                if (infuser.MaxLuxLevel(col) > 0) {
                    GL11.glColor3f(col.r / 2 + 0.25F, col.g / 2 + 0.25F, col.b / 2 + 0.25F);
                    this.drawArc(ang / total * 2 * Math.PI, (ang + infuser.MaxLuxLevel(col)) / total * 2 * Math.PI, this.guiLeft + 88, this.guiTop + 65, 176, 0, 0, 49);

                    GL11.glColor3f(col.r, col.g, col.b);
                    this.drawArc(ang / total * 2 * Math.PI, (ang + getLux().luxLevel(col)) / total * 2 * Math.PI, this.guiLeft + 88, this.guiTop + 65, 176, 0, 0, 49);

                    GL11.glColor3f(col.r, col.g, col.b);
                    this.drawArc(ang / total * 2 * Math.PI, (ang + getLux().luxLevel(col)) / total * 2 * Math.PI, this.guiLeft + 88, this.guiTop + 65, 176, 98,
                            41 - (int) ((getLux().luxLevel(col) * 41) / infuser.MaxLuxLevel(col)), 41);

                    ang += infuser.MaxLuxLevel(col);
                }
            }

    }

    public void drawArc(double start, double end, int x, int y, int x0, int y0, int r0, int r1) {
        if (start > end) {
            drawArc(end, start, x, y, x0, y0, r0, r1);
            return;
        }

        for (double i = start; i < end; i = nextPoint(i, end)) {
            drawStepArc(i, nextPoint(i, end), x, y, x0, y0, r0, r1);
        }

    }

    public void drawStepArc(double start, double end, int x, int y, int x0, int y0, int r0, int r1) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        this.addPoint(end, x, y, x0, y0, r0, r1);
        this.addPoint(end, x, y, x0, y0, r1 * 1.415, r1);
        this.addPoint(start, x, y, x0, y0, r1 * 1.415, r1);
        this.addPoint(start, x, y, x0, y0, r0, r1);
        tessellator.draw();
    }

    public void addPoint(double ang, int x, int y, int x0, int y0, double r0, int r1) {
        double ca = Math.cos(ang) * r0, sa = Math.sin(ang) * r0;
        if (Math.abs(ca) > r1) {
            sa *= r1 / Math.abs(ca);
            ca *= r1 / Math.abs(ca);
        }
        if (Math.abs(sa) > r1) {
            ca *= r1 / Math.abs(sa);
            sa *= r1 / Math.abs(sa);
        }

        Tessellator.instance.addVertexWithUV(x + ca, y + sa, (double) this.zLevel, (x0 + r1 - Math.abs(ca)) * sx, (y0 + r1 - Math.abs(sa)) * sy);
    }
}
