package com.rwtema.luxcraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import com.rwtema.luxcraft.LuxcraftCreativeTab;
import com.rwtema.luxcraft.textures.TextureAnimationOffset;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDecorativeOffset extends Block {

	public final int numTex;
	public IIcon[] icons;

	public BlockDecorativeOffset(String texture, int numTex) {
		super(Material.rock);
		this.setBlockName(texture + "_" + numTex);
		this.setBlockTextureName(texture);
		this.setCreativeTab(LuxcraftCreativeTab.instance);
		this.numTex = numTex;
		icons = new IIcon[numTex];
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		if (!(register instanceof TextureMap))
			return;
		TextureMap map = (TextureMap) register;

		for (int i = 0; i < numTex; i++) {
			TextureAtlasSprite tex = map.getTextureExtry(TextureAnimationOffset.iconName(textureName, i));
			if (tex == null) {
				tex = new TextureAnimationOffset(textureName, i);
				map.setTextureEntry(tex.getIconName(), tex);
			}
			icons[i] = tex;
		}

		this.blockIcon = icons[0];
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		int meta = (numTex * 5 + (x % numTex) + (y % numTex) + (z % numTex)) % numTex;
		return icons[meta];
	}
}
