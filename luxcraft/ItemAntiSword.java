package luxcraft;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemAntiSword extends Item
{
	private int weaponDamage;

	public ItemAntiSword(int par1)
	{
		super(par1);
		this.maxStackSize = 1;
		this.setMaxDamage(1024);
		this.setCreativeTab(CreativeTabs.tabCombat);
		this.setIconIndex(45);
		this.setTextureFile("/luxcraft/terrain.png");
		this.weaponDamage = 4 + EnumToolMaterial.EMERALD.getDamageVsEntity();
	}

	/**
	 * Returns the strength of the stack against a given block. 1.0F base, (Quality+1)*2 if correct blocktype, 1.5F if
	 * sword
	 */
	public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block)
	{
		if (par2Block.blockID == Block.web.blockID)
		{
			return 15.0F;
		}
		else
		{
			Material var3 = par2Block.blockMaterial;
			return var3 != Material.plants && var3 != Material.vine && var3 != Material.coral && var3 != Material.leaves && var3 != Material.pumpkin ? 1.0F : 1.5F;
		}
	}

	/**
	 * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
	 * the damage on the stack.
	 */
	public boolean hitEntity(ItemStack par1ItemStack, EntityLiving par2EntityLiving, EntityLiving par3EntityLiving)
	{
		par1ItemStack.damageItem(1, par3EntityLiving);
		return true;
	}

	public boolean onBlockDestroyed(ItemStack par1ItemStack, World par2World, int par3, int par4, int par5, int par6, EntityLiving par7EntityLiving)
	{
		if ((double)Block.blocksList[par3].getBlockHardness(par2World, par4, par5, par6) != 0.0D)
		{
			par1ItemStack.damageItem(2, par7EntityLiving);
		}

		return true;
	}

	/**
	 * Returns the damage against a given entity.
	 */
	public int getDamageVsEntity(Entity par1Entity)
	{
		if (par1Entity instanceof EntityLiving)
		{
			EntityLiving t = (EntityLiving)par1Entity;

			if ((t instanceof EntityPlayer && !(((EntityPlayer) t).capabilities.isCreativeMode)) || !(t instanceof EntityPlayer))
			{
				double h1 = t.getHealth();

				if (h1 != 1)
				{
					t.attackEntityFrom(DamageSource.cactus, (int)h1 - 1);
					double h2 = t.getHealth();
					t.heal((int)(h1 - h2));
					t.attackEntityFrom(DamageSource.outOfWorld, (int)((h2 - 1) / (h1 - 1) * t.getHealth()));
					return 0;
				}
				else
				{
					return this.weaponDamage*1024;
				}
			}
		}

		return this.weaponDamage;
	}

	/**
	 * Called when the player Left Clicks (attacks) an entity.
	 * Processed before damage is done, if return value is true further processing is canceled
	 * and the entity is not attacked.
	 *
	 * @param stack The Item being used
	 * @param player The player that is attacking
	 * @param entity The entity being attacked
	 * @return True to cancel the rest of the interaction.
	 */
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
	{
		if (!player.capabilities.isCreativeMode)
		{
			double h1 = player.getHealth();

			if (h1 > 1)
			{
				player.attackEntityFrom(DamageSource.cactus, (int)h1 - 1);
				double h2 = player.getHealth();
				player.heal((int)(h1 - h2));
				player.attackEntityFrom(DamageSource.outOfWorld, (int)((h2 - 1) / (h1 - 1) * (player.getHealth()-1)));
			}
		}

		return false;
	}

	@SideOnly(Side.CLIENT)

	/**
	 * Returns True is the item is renderer in full 3D when hold.
	 */
	public boolean isFull3D()
	{
		return true;
	}

	/**
	 * returns the action that specifies what animation to play when the items is being used
	 */
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return EnumAction.block;
	}

	/**
	 * How long it takes to use or consume an item
	 */
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
	{
		return 72000;
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		return par1ItemStack;
	}

	/**
	 * Returns if the item (tool) can harvest results from the block type.
	 */
	public boolean canHarvestBlock(Block par1Block)
	{
		return par1Block.blockID == Block.web.blockID;
	}

	/**
	 * Return whether this item is repairable in an anvil.
	 */
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
	{
		return EnumToolMaterial.EMERALD.getToolCraftingMaterial() == par2ItemStack.itemID ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
	}
}
