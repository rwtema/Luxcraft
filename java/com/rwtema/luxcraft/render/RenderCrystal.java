package com.rwtema.luxcraft.render;

import com.rwtema.luxcraft.util.MathHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

import java.util.Random;

public class RenderCrystal implements IItemRenderer {

    public final static double Pi2 = Math.PI * 2;

    public static IIcon crystal_quad;
    public static IIcon crystal_tri;
    public static IIcon crystal_interior;
    private static Random rand = new Random();

    private static double[][] points = null;

    public static void renderCrystal(long seed, boolean normals, IIcon crystal_tri, IIcon crystal_quad) {
        int k = 7;

        rand.setSeed(seed);


        if (points == null) {
            double r = 0.25;

            double dr = 0.0;

            points = new double[2 * k + 2][3];

            points[0] = clamp(add(new double[]{0.5, 1, 0.5}, randOffset(dr)));
            points[1] = clamp(add(new double[]{0.5, 0, 0.5}, randOffset(dr)));


            for (int i = 0; i < k; i += 1) {
                points[2 + i] = clamp(add(new double[]{0.5 + MathHelper.cos((i * Math.PI * 2) / k) * r, 0.7, 0.5 + MathHelper.sin((i * Math.PI * 2) / k) * r}, randOffset(dr)));
                points[2 + k + i] = clamp(add(new double[]{0.5 + MathHelper.cos((i * Math.PI * 2) / k) * r, 0.3, 0.5 + MathHelper.sin((i * Math.PI * 2) / k) * r}, randOffset(dr)));
            }
        }

        for (int i = 0; i < k; i += 1) {
            drawTri(points[2 + i], points[0], points[2 + ((i + 1) % k)], normals, rand, crystal_tri);
            drawTri(points[1], points[2 + k + i], points[2 + k + ((i + 1) % k)], normals, rand, crystal_tri);

            drawQuad(points[2 + i], points[2 + ((i + 1) % k)], points[2 + k + ((i + 1) % k)], points[2 + k + i], normals, rand, crystal_quad);
        }
    }

    public static double[] clamp(double[] a) {
        a[0] = a[0] > 1 ? 1 : a[0] < 0 ? 0 : a[0];
        a[1] = a[1] > 1 ? 1 : a[1] < 0 ? 0 : a[1];
        a[2] = a[2] > 1 ? 1 : a[2] < 0 ? 0 : a[2];
        return a;
    }

    public static double[] randOffset(double r) {
        return new double[]{rand.nextGaussian() * r, rand.nextGaussian() * r, rand.nextGaussian() * r};
    }

    public static double[] add(double[] a, double[] b) {
        double[] c = new double[3];
        for (int i = 0; i < 3; i++)
            c[i] = a[i] + b[i];
        return c;
    }

    public static double[] sub(double[] a, double[] b) {
        double[] c = new double[3];
        for (int i = 0; i < 3; i++)
            c[i] = a[i] - b[i];
        return c;
    }

    public static void drawTri(double[] a, double[] b, double[] c, boolean normals, Random rand, IIcon crystal_tri) {
        int i = rand.nextInt(3);
        if (i == 0)
            drawTri(a, b, c, normals, crystal_tri);
        else if (i == 1)
            drawTri(b, c, a, normals, crystal_tri);
        else
            drawTri(c, a, b, normals, crystal_tri);
    }

    public static void drawTri(double[] a, double[] b, double[] c, boolean normals, IIcon crystal_tri) {
        Tessellator tes = Tessellator.instance;

        if (normals) {
            double[] u = sub(a, b);
            double[] v = sub(c, b);

            tes.setNormal((float) (u[1] * v[2] - u[2] - v[1]), (float) (u[2] * v[0] - u[0] - v[2]), (float) (u[0] * v[1] - u[1] - v[0]));
        }


        tes.addVertexWithUV(a[0], a[1], a[2], crystal_tri.getInterpolatedU(8), crystal_tri.getInterpolatedV(0));
        tes.addVertexWithUV(a[0], a[1], a[2], crystal_tri.getInterpolatedU(8), crystal_tri.getInterpolatedV(0));
        tes.addVertexWithUV(b[0], b[1], b[2], crystal_tri.getInterpolatedU(16), crystal_tri.getInterpolatedV(16));
        tes.addVertexWithUV(c[0], c[1], c[2], crystal_tri.getInterpolatedU(0), crystal_tri.getInterpolatedV(16));
    }


    public static void drawQuad(double[] a, double[] b, double[] c, double[] d, boolean normals, Random rand, IIcon crystal_quad) {
        int i = rand.nextInt(4);
        if (i == 0)
            drawQuad(a, b, c, d, normals, crystal_quad);
        else if (i == 1)
            drawQuad(b, c, d, a, normals, crystal_quad);
        else if (i == 2)
            drawQuad(c, d, a, b, normals, crystal_quad);
        else
            drawQuad(d, a, b, c, normals, crystal_quad);
    }

    public static void setNormals(double[] a, double[] b, double[] c) {
        double[] u = sub(a, b);
        double[] v = sub(c, b);

        float x = (float) (u[1] * v[2] - u[2] * v[1]), y = ((float) (u[2] * v[0] - u[0] * v[2])), z = (float) (u[0] * v[1] - u[1] * v[0]);
        float l = (float) Math.sqrt(x * x + y * y + z * z);
        if (l > 0) {
            x /= l;
            y /= l;
            z /= l;
        }
        
        Tessellator.instance.setNormal(x, y, z);
    }

    public static void drawQuad(double[] a, double[] b, double[] c, double[] d, boolean normals, IIcon crystal_quad) {
        Tessellator tes = Tessellator.instance;


        tes.addVertexWithUV(a[0], a[1], a[2], crystal_quad.getInterpolatedU(0), crystal_quad.getInterpolatedV(0));
        tes.addVertexWithUV(b[0], b[1], b[2], crystal_quad.getInterpolatedU(16), crystal_quad.getInterpolatedV(0));
        tes.addVertexWithUV(c[0], c[1], c[2], crystal_quad.getInterpolatedU(16), crystal_quad.getInterpolatedV(16));
        tes.addVertexWithUV(d[0], d[1], d[2], crystal_quad.getInterpolatedU(0), crystal_quad.getInterpolatedV(16));
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;

    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        double offset = -0.5;

        IIcon texture = item.getIconIndex();

        if (texture == null) {
            return;
        }

        if (type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
            offset = 0;
        } else if (type == ItemRenderType.ENTITY) {
            GL11.glScalef(0.5F, 0.5F, 0.5F);
        }

        GL11.glTranslated(offset, offset, offset);

        if (type == ItemRenderType.EQUIPPED) {
            GL11.glTranslated(0.5F, 0.5F, 0.5F);
            GL11.glRotated(45, 0, 1, 0);
            GL11.glTranslated(-0.5F, -0.5F, -0.5F);
        }


        Tessellator tes = Tessellator.instance;
        GL11.glPushMatrix();
        {
            GL11.glTranslated(0.5F, 0.5F, 0.5F);
            GL11.glScaled(0.8, 0.8, 0.8);
            GL11.glTranslated(-0.5F, -0.5F, -0.5F);

            tes.startDrawingQuads();
            renderCrystal(0, true, crystal_interior, crystal_interior);
            tes.draw();
        }
        GL11.glPopMatrix();


        tes.startDrawingQuads();
        renderCrystal(0, true, crystal_tri, crystal_quad);
        tes.draw();


    }

}
