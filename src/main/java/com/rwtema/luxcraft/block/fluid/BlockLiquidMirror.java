package com.rwtema.luxcraft.block.fluid;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.rwtema.luxcraft.LuxcraftCreativeTab;
import com.rwtema.luxcraft.LuxcraftProxy;
import com.rwtema.luxcraft.textures.connectedtextures.ConnectedTexturesHelper;
import com.rwtema.luxcraft.textures.connectedtextures.IconConnected;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import java.util.UUID;

public class BlockLiquidMirror extends BlockFluidClassic {
    public static Fluid liquidMirror;

    public static boolean searching = false;
    private static UUID uuid = UUID.fromString("153e93a0-b53d-2333-ab19-643ee3217532");

    public BlockLiquidMirror() {
        super(registerFluid(), MaterialCustomLiquid.instance);
        this.setCreativeTab(LuxcraftCreativeTab.instance);
        this.setBlockName("luxcraft:liquidMirror");
        this.setBlockTextureName("luxcraft:liquidMirror");
        this.setLightLevel(0.35F);
        this.setQuantaPerBlock(4);

    }

    public static Fluid registerFluid() {
        liquidMirror = new Fluid("liquidMirror");
        FluidRegistry.registerFluid(liquidMirror);
        return liquidMirror;
    }

    @Override
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        IIcon icon = super.getIcon(world, x, y, z, side);
        if (icon instanceof IconConnected) {
//            if (side >= 2)
//                return new IconCorrecting(((IconConnected) icon).getWorldIcon(world, x, y, z, EnumFacing.getOrientation(side)));

            return ((IconConnected) icon).getWorldIcon(world, x, y, z, EnumFacing.getOrientation(side));
        }
        return icon;
    }

    @Override
    public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
//        if (p_149691_1_ < 2)
        return liquidMirror.getStillIcon();
//        else
//            return liquidMirror.getFlowingIcon();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        if (world.isRemote)
            return;
        if (!(entity instanceof EntityLivingBase))
            return;

        if(((EntityLivingBase) entity).getActivePotionEffect(Potion.fireResistance) != null)
            return;

        if (((EntityLivingBase) entity).getActivePotionEffect(Potion.poison) != null) {
            double l = 0;

            AttributeModifier attr = ((EntityLivingBase) entity).getAttributeMap().getAttributeInstanceByName(SharedMonsterAttributes.maxHealth.getAttributeUnlocalizedName()).getModifier(uuid);
            if (attr != null)
                l = attr.getAmount();

            if (l == -6)
                return;

            if (l == 0) {
                if (entity instanceof EntityPlayer) {
                    ((EntityPlayer) entity).addChatComponentMessage(new ChatComponentText("You have fallen into Mercury, get out or your max health will drain."));
                }
            }

            l -= 0.005;

             if (l - 0.005 < Math.floor(l)) {
                if (entity instanceof EntityPlayer) {
                    ((EntityPlayer) entity).addChatComponentMessage(new ChatComponentText("Mercury Poisoning: Your max health has decreased by 1/2"));
                }
            }



            if (l < -6)
                l = -6;

            Multimap multimap = HashMultimap.create();
            multimap.put(SharedMonsterAttributes.maxHealth.getAttributeUnlocalizedName(), new AttributeModifier(uuid, "Mercury Poisonning", l, 0));
            ((EntityLivingBase) entity).getAttributeMap().applyAttributeModifiers(multimap);
        }
        ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.poison.id, 20 * 15, 0));
        ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.blindness.id, 20 * 2, 0));

    }

    @Override
    public Vec3 getFlowVector(IBlockAccess world, int x, int y, int z) {
        return world.getWorldVec3Pool().getVecFromPool(0.0D, 0.0D, 0.0D);
    }

    @Override
    public int getRenderType() {
        return LuxcraftProxy.fluidRenderingID;
    }

    @Override
    public void registerBlockIcons(IIconRegister register) {
        this.blockIcon = ConnectedTexturesHelper.registerIcon("luxcraft:mirror2", register);
        liquidMirror.setIcons(blockIcon);
    }
}
