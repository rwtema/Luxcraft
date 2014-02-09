package luxcraft;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.util.StringTranslate;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemLuxDeconsRecipe extends ItemLuxSchema {

	public ItemLuxDeconsRecipe(int par1) {
		super(par1);
		this.setMaxDamage(0);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setItemName("DeconsRecipe");
		this.setMaxStackSize(1);
		this.setIconIndex(31);
		this.setTextureFile("/luxcraft/terrain.png");
	}

	@SideOnly(Side.CLIENT)

	/**
	 * Gets an icon index based on an item's damage value
	 */
	public int getIconFromDamage(int par1)
	{
		return this.iconIndex;
	}

	@SideOnly(Side.CLIENT)

	/**
	 * allows items to add custom lines of information to the mouseover description
	 */
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		int id = par1ItemStack.getItemDamage();
		int damage = 0;
		if(par1ItemStack.getTagCompound() != null){
			if(par1ItemStack.getTagCompound().hasKey("damage"))
				damage = par1ItemStack.getTagCompound().getInteger("damage");
		}

		Item temp = Item.itemsList[id];

		if(temp != null){
			String name1 = StatCollector.translateToLocal(temp.getItemName());

			if(temp.getHasSubtypes()){
				name1 = StatCollector.translateToLocal(temp.getItemNameIS(new ItemStack(temp,1,damage)));
			}

			if(name1 == null) name1 = "";

			String name = ("" + StringTranslate.getInstance().translateNamedKey(name1)).trim();

			par3List.set(0, name +" " + (String)par3List.get(0));

			if(!this.isSynthesizable(par1ItemStack) )
				par3List.add("Not Synthesizable");


			LuxPacket tempPacket = this.getLuxData(par1ItemStack);
			for(byte c = 0;c<7;c++)
				if(tempPacket.luxLevel[c]>0)
					par3List.add(LuxHelper.color_str[c]+LuxHelper.color_abb[c]+" "+LuxHelper.display(tempPacket.luxLevel[c]));

		}
	}


	public static ItemStack newDeconRecipe(ItemStack par1ItemStack, LuxPacket luxData, boolean synthasizeable){
		int damage = 0;
		if(par1ItemStack.getItem().getHasSubtypes())
			damage = par1ItemStack.getItemDamage();

		ItemStack newItem = new ItemStack(Luxcraft.deconsRecipe, 1, par1ItemStack.itemID);


		if(damage != 0 | !luxData.isEmpty() | synthasizeable){
			NBTTagCompound var3 = newItem.getTagCompound();

			if (var3 == null)
			{
				var3 = new NBTTagCompound();
				newItem.setTagCompound(var3);
			}

			if(damage != 0 ){
				var3.setInteger("damage", damage);
			}

			for(byte c = 0; c<7; c++){
				if(luxData.luxLevel[c]>0){
					var3.setInteger("Lux"+LuxHelper.color_abb[c],luxData.luxLevel[c]);
				}
			}

			if(synthasizeable)
				var3.setBoolean("Synth", true);
		}

		return newItem;
	}

	public static LuxPacket getLuxData(ItemStack item){
		LuxPacket packet = new LuxPacket();

		if(item.getTagCompound() != null)
			for(byte c = 0; c<7; c++)
				if(item.getTagCompound().hasKey("Lux"+LuxHelper.color_abb[c]))
					packet.luxLevel[c] = item.getTagCompound().getInteger("Lux"+LuxHelper.color_abb[c]);

		return packet;
	}

	public static boolean isSynthesizable(ItemStack item){
		if(item.getTagCompound() != null)
			for(byte c = 0; c<7; c++)
				if(item.getTagCompound().hasKey("Synth"))
					return true;

		return false;
	}





	public static ItemStack createdItem(ItemStack par1ItemStack){
		if(par1ItemStack == null)
			return null;

		if(par1ItemStack.itemID==Luxcraft.deconsRecipe.itemID){
			int id = par1ItemStack.getItemDamage();
			int damage = 0;
			if(par1ItemStack.getTagCompound() != null){
				if(par1ItemStack.getTagCompound().hasKey("damage"))
					damage = par1ItemStack.getTagCompound().getInteger("damage");
			}

			return new ItemStack(id, 1, damage);
		}else
			return null;
	}


	/**
	 * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
	 */
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		return;
	}


}
