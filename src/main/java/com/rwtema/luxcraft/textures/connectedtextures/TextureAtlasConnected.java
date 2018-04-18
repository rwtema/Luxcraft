package com.rwtema.luxcraft.textures.connectedtextures;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class TextureAtlasConnected extends TextureAtlasSprite {
    private static final Logger logger = LogManager.getLogger();
    public int texId;

    protected TextureAtlasConnected(String par1Str, int texId) {
        super(par1Str);
        this.texId = texId;
    }

    public static String iconName(String name, int offset) {
        return name + "_" + offset;
    }

    public String getIconName() {
        return iconName(super.getIconName(), texId);
    }

    @SuppressWarnings("unchecked")
    public void loadSprite(BufferedImage[] p_147964_1_, AnimationMetadataSection p_147964_2_, boolean p_147964_3_) {
        this.setFramesTextureData(Lists.newArrayList());
        int w = p_147964_1_[0].getWidth();
        int h = p_147964_1_[0].getHeight();
        this.width = w;
        this.height = w;

        if (p_147964_3_) {
            this.width += 16;
            this.height += 16;
        }

        if (w % 2 != 0)
            throw new RuntimeException("Wrong width (must be divisible by 2)");

        if (h != (w * 5))
            throw new RuntimeException("Wrong aspect ratio (must be 1x5)");

        int[][] aint = new int[p_147964_1_.length][];


        BufferedImage bufferedimage = p_147964_1_[0];

        aint[0] = new int[w * w];

        int t = ConnectedTexturesHelper.textureIds[texId];

        int w2 = w / 2;

        int k, ax, ay;

        k = t / 125;
        bufferedimage.getRGB(0, k * w, w2, w2, aint[0], 0, w);
        t -= k * 125;

        k = t / 25;
        bufferedimage.getRGB(0, k * w + w2, w2, w2, aint[0], w2 * w, w);
        t -= k * 25;

        k = t / 5;
        bufferedimage.getRGB(w2, k * w + w2, w2, w2, aint[0], w2 * (w + 1), w);
        t -= k * 5;

        k = t;
        bufferedimage.getRGB(w2, k * w, w2, w2, aint[0], w2, w);

        this.fixTransparentPixels(aint);

        if (p_147964_3_)
            aint = this.prepareAnisotropicFiltering(aint, w, h);

        this.framesTextureData.add(aint);
    }


    private int[][] prepareAnisotropicFiltering(int[][] p_147960_1_, int p_147960_2_, int p_147960_3_) {
        int[][] aint1 = new int[p_147960_1_.length][];

        for (int k = 0; k < p_147960_1_.length; ++k) {
            int[] aint2 = p_147960_1_[k];

            if (aint2 != null) {
                int[] aint3 = new int[(p_147960_2_ + 16 >> k) * (p_147960_3_ + 16 >> k)];
                System.arraycopy(aint2, 0, aint3, 0, aint2.length);
                aint1[k] = TextureUtil.prepareAnisotropicData(aint3, p_147960_2_ >> k, p_147960_3_ >> k, 8 >> k);
            }
        }

        return aint1;

    }


    public void fixTransparentPixels(int[][] p_147961_1_) {
        int[] aint1 = p_147961_1_[0];
        int i = 0;
        int j = 0;
        int k = 0;
        int l = 0;
        int i1;

        for (i1 = 0; i1 < aint1.length; ++i1) {
            if ((aint1[i1] & -16777216) != 0) {
                j += aint1[i1] >> 16 & 255;
                k += aint1[i1] >> 8 & 255;
                l += aint1[i1] >> 0 & 255;
                ++i;
            }
        }

        if (i != 0) {
            j /= i;
            k /= i;
            l /= i;

            for (i1 = 0; i1 < aint1.length; ++i1) {
                if ((aint1[i1] & -16777216) == 0) {
                    aint1[i1] = j << 16 | k << 8 | l;
                }
            }
        }
    }

    public boolean hasCustomLoader(IResourceManager manager, ResourceLocation location) {
        return true;
    }

    public ResourceLocation completeResourceLocation(ResourceLocation p_147634_1_, int p_147634_2_) {
        return p_147634_2_ == 0 ? new ResourceLocation(p_147634_1_.getResourceDomain(), String.format("%s/%s%s", new Object[]{"textures/blocks", p_147634_1_.getResourcePath(), ".png"}))
                : new ResourceLocation(p_147634_1_.getResourceDomain(), String.format("%s/mipmaps/%s.%d%s",
                new Object[]{"textures/blocks", p_147634_1_.getResourcePath(), Integer.valueOf(p_147634_2_), ".png"}));
    }


    public boolean load(IResourceManager par1ResourceManager, ResourceLocation location) {

        ResourceLocation resourcelocation = new ResourceLocation(super.getIconName());
        ResourceLocation resourcelocation1 = this.completeResourceLocation(resourcelocation, 0);

        try {
            IResource iresource = par1ResourceManager.getResource(resourcelocation1);
            BufferedImage[] abufferedimage = new BufferedImage[1 + Minecraft.getMinecraft().gameSettings.mipmapLevels];
            abufferedimage[0] = ImageIO.read(iresource.getInputStream());
            this.loadSprite(abufferedimage, null, (float) Minecraft.getMinecraft().gameSettings.anisotropicFiltering > 1.0F);
        } catch (IOException ioexception1) {
            logger.error("Using missing texture, unable to load " + resourcelocation1, ioexception1);
            return true;
        }

        return false;

    }
}
