package luxcraft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBloodPotion extends Item
{
    /** maps potion damage values to lists of effect names */
    private HashMap effectCache = new HashMap();
    private static final Map field_77835_b = new LinkedHashMap();

    public ItemBloodPotion(int par1)
    {
        super(par1);
        this.setMaxStackSize(1);
        // this.setHasSubtypes(true);
        this.setMaxDamage(256);
        this.setCreativeTab(CreativeTabs.tabMaterials);
    }

    /**
     * Returns a list of potion effects for the specified itemstack.
     */
    public List getEffects(ItemStack par1ItemStack)
    {
        if (par1ItemStack.hasTagCompound() && par1ItemStack.getTagCompound().hasKey("CustomPotionEffects"))
        {
            ArrayList var6 = new ArrayList();
            NBTTagList var3 = par1ItemStack.getTagCompound().getTagList("CustomPotionEffects");

            for (int var4 = 0; var4 < var3.tagCount(); ++var4)
            {
                NBTTagCompound var5 = (NBTTagCompound)var3.tagAt(var4);
                var6.add(PotionEffect.readCustomPotionEffectFromNBT(var5));
            }

            return var6;
        }
        else
        {
            List var2 = (List)this.effectCache.get(Integer.valueOf(par1ItemStack.getItemDamage()));

            if (var2 == null)
            {
                var2 = PotionHelper.getPotionEffects(par1ItemStack.getItemDamage(), false);
                this.effectCache.put(Integer.valueOf(par1ItemStack.getItemDamage()), var2);
            }

            return var2;
        }
    }

    /**
     * Returns a list of effects for the specified potion damage value.
     */
    public List getEffects(int par1)
    {
        List var2 = (List)this.effectCache.get(Integer.valueOf(par1));

        if (var2 == null)
        {
            var2 = PotionHelper.getPotionEffects(par1, false);
            this.effectCache.put(Integer.valueOf(par1), var2);
        }

        return var2;
    }

    /**
     * Returns a list of effects for the specified potion damage value.
     */
    /*    public List getEffects(int par1)
        {
            List var2 = (List)this.effectCache.get(Integer.valueOf(par1));

            if (var2 == null)
            {
                var2 = PotionHelper.getPotionEffects(par1, false);
                this.effectCache.put(Integer.valueOf(par1), var2);
            }

            return var2;
        }*/

    public ItemStack onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        if (!par3EntityPlayer.capabilities.isCreativeMode)
        {
            --par1ItemStack.stackSize;
        }

        if (!par2World.isRemote)
        {
            List var4 = this.getEffects(par1ItemStack);

            if (var4 != null)
            {
                Iterator var5 = var4.iterator();

                while (var5.hasNext())
                {
                    PotionEffect var6 = (PotionEffect)var5.next();
                    par3EntityPlayer.addPotionEffect(new PotionEffect(var6));
                }
            }
        }

        if (!par3EntityPlayer.capabilities.isCreativeMode)
        {
            if (par1ItemStack.stackSize <= 0)
            {
                return new ItemStack(Item.glassBottle);
            }

            par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(Item.glassBottle));
        }

        return par1ItemStack;
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 32;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.drink;
//    	return EnumAction.none;
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        return false;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Gets an icon index based on an item's damage value
     */
    public int getIconFromDamage(int par1)
    {
        return 140;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Gets an icon index based on an item's damage value and the given render pass
     */
    public int getIconFromDamageForRenderPass(int par1, int par2)
    {
        //return 140;
        return par2 == 0 ? 141 : super.getIconFromDamageForRenderPass(par1, par2);
    }

    /**
     * returns wether or not a potion is a throwable splash potion based on damage value
     */
    public static boolean isSplash(int par0)
    {
        return false;
        //return (par0 & 16384) != 0;
    }

    @SideOnly(Side.CLIENT)
    public int getColorFromDamage(int par1)
    {
        return 0xff0000;
    }

    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
    {
        return par2 > 0 ? 16777215 : this.getColorFromDamage(par1ItemStack.getItemDamage());
    }

    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }

    /*   @SideOnly(Side.CLIENT)
       public boolean isEffectInstant(int par1)
       {
           List var2 = this.getEffects(par1);

           if (var2 != null && !var2.isEmpty())
           {
               Iterator var3 = var2.iterator();
               PotionEffect var4;

               do
               {
                   if (!var3.hasNext())
                   {
                       return false;
                   }

                   var4 = (PotionEffect)var3.next();
               }
               while (!Potion.potionTypes[var4.getPotionID()].isInstant());

               return true;
           }
           else
           {
               return false;
           }
       }*/

//    public String getItemDisplayName(ItemStack par1ItemStack)
//    {
//        if (par1ItemStack.getItemDamage() == 0)
//        {
//        	return super.getItemDisplayName(par1ItemStack);
//        }
//
//        return StatCollector.translateToLocal("item.bloodPotion.partialfull").trim();
//
//    }

//    @SideOnly(Side.CLIENT)
//
//    /**
//     * allows items to add custom lines of information to the mouseover description
//     */
//    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
//    {
//        if (par1ItemStack.getItemDamage() != 0)
//        {
//            List var5 = Item.potion.getEffects(par1ItemStack);
//
//            if (var5 != null && !var5.isEmpty())
//            {
//                Iterator var9 = var5.iterator();
//
//                while (var9.hasNext())
//                {
//                    PotionEffect var7 = (PotionEffect)var9.next();
//                    String var8 = StatCollector.translateToLocal(var7.getEffectName()).trim();
//
//                    if (var7.getAmplifier() > 0)
//                    {
//                        var8 = var8 + " " + StatCollector.translateToLocal("potion.potency." + var7.getAmplifier()).trim();
//                    }
//
//                    if (var7.getDuration() > 20)
//                    {
//                        var8 = var8 + " (" + Potion.getDurationString(var7) + ")";
//                    }
//
//                    if (Potion.potionTypes[var7.getPotionID()].isBadEffect())
//                    {
//                        par3List.add("\u00a7c" + var8);
//                    }
//                    else
//                    {
//                        par3List.add("\u00a77" + var8);
//                    }
//                }
//            }
//            else
//            {
//                String var6 = StatCollector.translateToLocal("potion.empty").trim();
//                par3List.add("\u00a77" + var6);
//            }
//        }
//    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack par1ItemStack)
    {
        return false;
    }

//   @SideOnly(Side.CLIENT)

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
//    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
//    {
//        super.getSubItems(par1, par2CreativeTabs, par3List);
//
//        if (field_77835_b.isEmpty())
//        {
//            for (int var4 = 0; var4 <= 32767; ++var4)
//            {
//                List var5 = PotionHelper.getPotionEffects(var4, false);
//
//                if (var5 != null && !var5.isEmpty())
//                {
//                    field_77835_b.put(var5, Integer.valueOf(var4));
//                }
//            }
//        }
//
//        Iterator var6 = field_77835_b.values().iterator();
//
//        while (var6.hasNext())
//        {
//            int var7 = ((Integer)var6.next()).intValue();
//            par3List.add(new ItemStack(par1, 1, var7));
//        }
//    }
}
