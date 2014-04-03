package com.rwtema.luxcraft.textures;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.client.resources.data.TextureMetadataSection;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class TextureAnimationOffset extends TextureAtlasSprite {

	public static String iconName(String name, int offset) {
		return name + "_" + offset;
	}

	public int offset = 0;
	public int animNo = 0;

	public TextureAnimationOffset(String par1Str, int offset) {
		super(par1Str);
		this.offset = offset;
	}

	public String getIconName() {
		return iconName(super.getIconName(), offset);
	}

	public void loadSprite(BufferedImage[] p_147964_1_, AnimationMetadataSection p_147964_2_, boolean p_147964_3_) {
		super.loadSprite(p_147964_1_, p_147964_2_, p_147964_3_);
		this.frameCounter = offset;
	}

	public boolean hasCustomLoader(IResourceManager manager, ResourceLocation location) {
		return true;
	}

	private ResourceLocation completeResourceLocation(ResourceLocation p_147634_1_, int p_147634_2_) {
		return p_147634_2_ == 0 ? new ResourceLocation(p_147634_1_.getResourceDomain(), String.format("%s/%s%s", new Object[] { "textures/blocks", p_147634_1_.getResourcePath(), ".png" }))
				: new ResourceLocation(p_147634_1_.getResourceDomain(), String.format("%s/mipmaps/%s.%d%s",
						new Object[] { "textures/blocks", p_147634_1_.getResourcePath(), Integer.valueOf(p_147634_2_), ".png" }));
	}

	public boolean load(IResourceManager par1ResourceManager, ResourceLocation location) {

		ResourceLocation resourcelocation = new ResourceLocation(super.getIconName());

		ResourceLocation resourcelocation1 = this.completeResourceLocation(resourcelocation, 0);

		try {
			IResource iresource = par1ResourceManager.getResource(resourcelocation1);
			BufferedImage[] abufferedimage = new BufferedImage[1 + Minecraft.getMinecraft().gameSettings.mipmapLevels];
			abufferedimage[0] = ImageIO.read(iresource.getInputStream());
			TextureMetadataSection texturemetadatasection = (TextureMetadataSection) iresource.getMetadata("texture");

			if (texturemetadatasection != null) {
				List list = texturemetadatasection.getListMipmaps();
				int l;

				if (!list.isEmpty()) {
					int k = abufferedimage[0].getWidth();
					l = abufferedimage[0].getHeight();

					if (MathHelper.roundUpToPowerOfTwo(k) != k || MathHelper.roundUpToPowerOfTwo(l) != l) {
						throw new RuntimeException("Unable to load extra miplevels, source-texture is not power of two");
					}
				}

				Iterator iterator3 = list.iterator();

				while (iterator3.hasNext()) {
					l = ((Integer) iterator3.next()).intValue();

					if (l > 0 && l < abufferedimage.length - 1 && abufferedimage[l] == null) {
						ResourceLocation resourcelocation2 = this.completeResourceLocation(resourcelocation, l);

						try {
							abufferedimage[l] = ImageIO.read(par1ResourceManager.getResource(resourcelocation2).getInputStream());
						} catch (IOException ioexception) {
							logger.error("Unable to load miplevel {} from: {}", new Object[] { Integer.valueOf(l), resourcelocation2, ioexception });
						}
					}
				}
			}

			AnimationMetadataSection animationmetadatasection = (AnimationMetadataSection) iresource.getMetadata("animation");
			this.loadSprite(abufferedimage, animationmetadatasection, (float) Minecraft.getMinecraft().gameSettings.anisotropicFiltering > 1.0F);
		} catch (RuntimeException runtimeexception) {
			logger.error("Unable to parse metadata from " + resourcelocation1, runtimeexception);
			return true;
		} catch (IOException ioexception1) {
			logger.error("Using missing texture, unable to load " + resourcelocation1, ioexception1);
			return true;
		}

		return false;
	}

	private static final Logger logger = LogManager.getLogger();
}
