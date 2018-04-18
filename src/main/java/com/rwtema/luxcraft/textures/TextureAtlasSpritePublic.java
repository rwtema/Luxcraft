package com.rwtema.luxcraft.textures;

import com.google.common.collect.Lists;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.data.AnimationFrame;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;

public class TextureAtlasSpritePublic extends TextureAtlasSprite {
    public TextureAtlasSpritePublic(String par1Str) {
        super(par1Str);
    }

    public List framesTextureData = Lists.newArrayList();
    public AnimationMetadataSection animationMetadata;
    public boolean rotated;
    public boolean useAnisotropicFiltering;
    public int originX;
    public int originY;
    public int width;
    public int height;
    public float minU;
    public float maxU;
    public float minV;
    public float maxV;
    public int frameCounter;
    public int tickCounter;

    public ResourceLocation completeResourceLocation(ResourceLocation p_147634_1_, int p_147634_2_) {
        return p_147634_2_ == 0 ? new ResourceLocation(p_147634_1_.getResourceDomain(), String.format("%s/%s%s", new Object[]{"textures/blocks", p_147634_1_.getResourcePath(), ".png"}))
                : new ResourceLocation(p_147634_1_.getResourceDomain(), String.format("%s/mipmaps/%s.%d%s",
                new Object[]{"textures/blocks", p_147634_1_.getResourcePath(), Integer.valueOf(p_147634_2_), ".png"}));
    }



    public void initSprite(int par1, int par2, int par3, int par4, boolean par5)
    {
        this.originX = par3;
        this.originY = par4;
        this.rotated = par5;
        float f = (float)(0.009999999776482582D / (double)par1);
        float f1 = (float)(0.009999999776482582D / (double)par2);
        this.minU = (float)par3 / (float)((double)par1) + f;
        this.maxU = (float)(par3 + this.width) / (float)((double)par1) - f;
        this.minV = (float)par4 / (float)par2 + f1;
        this.maxV = (float)(par4 + this.height) / (float)par2 - f1;

        if (this.useAnisotropicFiltering)
        {
            float f2 = 8.0F / (float)par1;
            float f3 = 8.0F / (float)par2;
            this.minU += f2;
            this.maxU -= f2;
            this.minV += f3;
            this.maxV -= f3;
        }
    }

    /**
     * Returns the X position of this icon on its texture sheet, in pixels.
     */
    public int getOriginX()
    {
        return this.originX;
    }

    /**
     * Returns the Y position of this icon on its texture sheet, in pixels.
     */
    public int getOriginY()
    {
        return this.originY;
    }

    /**
     * Returns the width of the icon, in pixels.
     */
    public int getIconWidth()
    {
        return this.width;
    }

    /**
     * Returns the height of the icon, in pixels.
     */
    public int getIconHeight()
    {
        return this.height;
    }

    /**
     * Returns the minimum U coordinate to use when rendering with this icon.
     */
    public float getMinU()
    {
        return this.minU;
    }

    /**
     * Returns the maximum U coordinate to use when rendering with this icon.
     */
    public float getMaxU()
    {
        return this.maxU;
    }

    /**
     * Gets a U coordinate on the icon. 0 returns uMin and 16 returns uMax. Other arguments return in-between values.
     */
    public float getInterpolatedU(double par1)
    {
        float f = this.maxU - this.minU;
        return this.minU + f * (float)par1 / 16.0F;
    }

    /**
     * Returns the minimum V coordinate to use when rendering with this icon.
     */
    public float getMinV()
    {
        return this.minV;
    }

    /**
     * Returns the maximum V coordinate to use when rendering with this icon.
     */
    public float getMaxV()
    {
        return this.maxV;
    }

    /**
     * Gets a V coordinate on the icon. 0 returns vMin and 16 returns vMax. Other arguments return in-between values.
     */
    public float getInterpolatedV(double par1)
    {
        float f = this.maxV - this.minV;
        return this.minV + f * ((float)par1 / 16.0F);
    }


    public void updateAnimation()
    {
        ++this.tickCounter;

        if (this.tickCounter >= this.animationMetadata.getFrameTimeSingle(this.frameCounter))
        {
            int i = this.animationMetadata.getFrameIndex(this.frameCounter);
            int j = this.animationMetadata.getFrameCount() == 0 ? this.framesTextureData.size() : this.animationMetadata.getFrameCount();
            this.frameCounter = (this.frameCounter + 1) % j;
            this.tickCounter = 0;
            int k = this.animationMetadata.getFrameIndex(this.frameCounter);

            if (i != k && k >= 0 && k < this.framesTextureData.size())
            {
                TextureUtil.uploadTextureMipmap((int[][]) this.framesTextureData.get(k), this.width, this.height, this.originX, this.originY, false, false);
            }
        }
    }

    public int[][] getFrameTextureData(int p_147965_1_)
    {
        return (int[][])this.framesTextureData.get(p_147965_1_);
    }

    public int getFrameCount()
    {
        return this.framesTextureData.size();
    }

    public void setIconWidth(int par1)
    {
        this.width = par1;
    }

    public void setIconHeight(int par1)
    {
        this.height = par1;
    }

    @SuppressWarnings("unchecked")
    public void loadSprite(BufferedImage[] p_147964_1_, AnimationMetadataSection p_147964_2_, boolean p_147964_3_)
    {
        this.resetSprite();
        this.useAnisotropicFiltering = p_147964_3_;
        int i = p_147964_1_[0].getWidth();
        int j = p_147964_1_[0].getHeight();
        this.width = i;
        this.height = j;

        if (p_147964_3_)
        {
            this.width += 16;
            this.height += 16;
        }

        int[][] aint = new int[p_147964_1_.length][];
        int k;

        for (k = 0; k < p_147964_1_.length; ++k)
        {
            BufferedImage bufferedimage = p_147964_1_[k];

            if (bufferedimage != null)
            {
                if (k > 0 && (bufferedimage.getWidth() != i >> k || bufferedimage.getHeight() != j >> k))
                {
                    throw new RuntimeException(String.format("Unable to load miplevel: %d, image is size: %dx%d, expected %dx%d", new Object[] {Integer.valueOf(k), Integer.valueOf(bufferedimage.getWidth()), Integer.valueOf(bufferedimage.getHeight()), Integer.valueOf(i >> k), Integer.valueOf(j >> k)}));
                }

                aint[k] = new int[bufferedimage.getWidth() * bufferedimage.getHeight()];
                bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), aint[k], 0, bufferedimage.getWidth());
            }
        }

        if (p_147964_2_ == null)
        {
            if (j != i)
            {
                throw new RuntimeException("broken aspect ratio and not an animation");
            }

            this.fixTransparentPixels(aint);
            this.framesTextureData.add(this.prepareAnisotropicFiltering(aint, i, j));
        }
        else
        {
            k = j / i;
            int j1 = i;
            int l = i;
            this.height = this.width;
            int i1;

            if (p_147964_2_.getFrameCount() > 0)
            {
                Iterator iterator = p_147964_2_.getFrameIndexSet().iterator();

                while (iterator.hasNext())
                {
                    i1 = ((Integer)iterator.next()).intValue();

                    if (i1 >= k)
                    {
                        throw new RuntimeException("invalid frameindex " + i1);
                    }

                    this.allocateFrameTextureData(i1);
                    this.framesTextureData.set(i1, this.prepareAnisotropicFiltering(getFrameTextureData(aint, j1, l, i1), j1, l));
                }

                this.animationMetadata = p_147964_2_;
            }
            else
            {
                ArrayList arraylist = Lists.newArrayList();

                for (i1 = 0; i1 < k; ++i1)
                {
                    this.framesTextureData.add(this.prepareAnisotropicFiltering(getFrameTextureData(aint, j1, l, i1), j1, l));
                    arraylist.add(new AnimationFrame(i1, -1));
                }

                this.animationMetadata = new AnimationMetadataSection(arraylist, this.width, this.height, p_147964_2_.getFrameTime());
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void generateMipmaps(int p_147963_1_)
    {
        ArrayList arraylist = Lists.newArrayList();

        for (int j = 0; j < this.framesTextureData.size(); ++j)
        {
            final int[][] aint = (int[][])this.framesTextureData.get(j);

            if (aint != null)
            {
                try
                {
                    arraylist.add(TextureUtil.generateMipmapData(p_147963_1_, this.width, aint));
                }
                catch (Throwable throwable)
                {
                    CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Generating mipmaps for frame");
                    CrashReportCategory crashreportcategory = crashreport.makeCategory("Frame being iterated");
                    crashreportcategory.addCrashSection("Frame index", Integer.valueOf(j));
                    crashreportcategory.addCrashSectionCallable("Frame sizes", new Callable()
                    {

                        public String call()
                        {
                            StringBuilder stringbuilder = new StringBuilder();
                            int[][] aint1 = aint;
                            int k = aint1.length;

                            for (int l = 0; l < k; ++l)
                            {
                                int[] aint2 = aint1[l];

                                if (stringbuilder.length() > 0)
                                {
                                    stringbuilder.append(", ");
                                }

                                stringbuilder.append(aint2 == null ? "null" : Integer.valueOf(aint2.length));
                            }

                            return stringbuilder.toString();
                        }
                    });
                    throw new ReportedException(crashreport);
                }
            }
        }

        this.setFramesTextureData(arraylist);
    }

    public void fixTransparentPixels(int[][] p_147961_1_)
    {
        int[] aint1 = p_147961_1_[0];
        int i = 0;
        int j = 0;
        int k = 0;
        int l = 0;
        int i1;

        for (i1 = 0; i1 < aint1.length; ++i1)
        {
            if ((aint1[i1] & -16777216) != 0)
            {
                j += aint1[i1] >> 16 & 255;
                k += aint1[i1] >> 8 & 255;
                l += aint1[i1] >> 0 & 255;
                ++i;
            }
        }

        if (i != 0)
        {
            j /= i;
            k /= i;
            l /= i;

            for (i1 = 0; i1 < aint1.length; ++i1)
            {
                if ((aint1[i1] & -16777216) == 0)
                {
                    aint1[i1] = j << 16 | k << 8 | l;
                }
            }
        }
    }

    @Override
    public void copyFrom(TextureAtlasSprite par1TextureAtlasSprite) {
        this.originX = par1TextureAtlasSprite.getOriginX();
        this.originY = par1TextureAtlasSprite.getOriginY();
        this.width = par1TextureAtlasSprite.getIconWidth();
        this.height = par1TextureAtlasSprite.getIconHeight();
//        this.rotated = par1TextureAtlasSprite.;
        this.minU = par1TextureAtlasSprite.getMinU();
        this.maxU = par1TextureAtlasSprite.getMaxU();
        this.minV = par1TextureAtlasSprite.getMinV();
        this.maxV = par1TextureAtlasSprite.getMaxV();
    }

    public int[][] prepareAnisotropicFiltering(int[][] p_147960_1_, int p_147960_2_, int p_147960_3_)
    {
        if (!this.useAnisotropicFiltering)
        {
            return p_147960_1_;
        }
        else
        {
            int[][] aint1 = new int[p_147960_1_.length][];

            for (int k = 0; k < p_147960_1_.length; ++k)
            {
                int[] aint2 = p_147960_1_[k];

                if (aint2 != null)
                {
                    int[] aint3 = new int[(p_147960_2_ + 16 >> k) * (p_147960_3_ + 16 >> k)];
                    System.arraycopy(aint2, 0, aint3, 0, aint2.length);
                    aint1[k] = TextureUtil.prepareAnisotropicData(aint3, p_147960_2_ >> k, p_147960_3_ >> k, 8 >> k);
                }
            }

            return aint1;
        }
    }

    @SuppressWarnings("unchecked")
    public void allocateFrameTextureData(int par1)
    {
        if (this.framesTextureData.size() <= par1)
        {
            for (int j = this.framesTextureData.size(); j <= par1; ++j)
            {
                this.framesTextureData.add((Object)null);
            }
        }
    }

    public static int[][] getFrameTextureData(int[][] p_147962_0_, int p_147962_1_, int p_147962_2_, int p_147962_3_)
    {
        int[][] aint1 = new int[p_147962_0_.length][];

        for (int l = 0; l < p_147962_0_.length; ++l)
        {
            int[] aint2 = p_147962_0_[l];

            if (aint2 != null)
            {
                aint1[l] = new int[(p_147962_1_ >> l) * (p_147962_2_ >> l)];
                System.arraycopy(aint2, p_147962_3_ * aint1[l].length, aint1[l], 0, aint1[l].length);
            }
        }

        return aint1;
    }

    public void clearFramesTextureData()
    {
        this.framesTextureData.clear();
    }

    public boolean hasAnimationMetadata()
    {
        return this.animationMetadata != null;
    }

    public void setFramesTextureData(List par1List)
    {
        this.framesTextureData = par1List;
    }

    public void resetSprite()
    {
        this.animationMetadata = null;
        this.setFramesTextureData(Lists.newArrayList());
        this.frameCounter = 0;
        this.tickCounter = 0;
    }

    public String toString()
    {
        return "TextureAtlasSprite{name=\'" + super.getIconName() + '\'' + ", frameCount=" + this.framesTextureData.size() + ", rotated=" + this.rotated + ", x=" + this.originX + ", y=" + this.originY + ", height=" + this.height + ", width=" + this.width + ", u0=" + this.minU + ", u1=" + this.maxU + ", v0=" + this.minV + ", v1=" + this.maxV + '}';
    }


    public boolean hasCustomLoader(IResourceManager manager, ResourceLocation location)
    {
        return false;
    }


    public boolean load(IResourceManager manager, ResourceLocation location)
    {
        return true;
    }
}
