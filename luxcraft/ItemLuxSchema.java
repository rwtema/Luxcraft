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

public class ItemLuxSchema extends Item {

	public ItemLuxSchema(int par1) {
		super(par1);
		this.setMaxDamage(0);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setItemName("Schema");
		this.setMaxStackSize(64);
		this.setIconIndex(12);
		this.setTextureFile("/luxcraft/terrain.png");
		this.setHasSubtypes(true);
	}

	//	@SideOnly(Side.CLIENT)
	//
	//	/**
	//	 * Gets an icon index based on an item's damage value
	//	 */
	//	public int getIconFromDamage(int par1)
	//	{
	//		if(par1==0)
	//			return 12;
	//		else 
	//			return 13;        
	//	}
	//	

	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses()
	{
		return true;
	}

	@SideOnly(Side.CLIENT)

	/**
	 * Gets an icon index based on an item's damage value and the given render pass
	 */
	public int getIconFromDamageForRenderPass(int par1, int par2)
	{
		if(par2 == 0){
			if(par1==0)
				return 12;
			else
				return 13;
		}else{
			return 46;
		}
	}

	/**
	 * Returns the number of render passes/layers this item has.
	 * Usually equates to ItemRenderer.renderItem being called for this many passes.
	 * Does not get called unless requiresMultipleRenderPasses() is true;
	 *
	 * @param metadata The item's metadata
	 * @return The number of passes to run.
	 */
	public int getRenderPasses(int metadata)
	{
		return metadata!=0 ? 2 : 1;
	}


	@SideOnly(Side.CLIENT)
	public int getColorFromDamage(int damage){
		return LuxLevels.instance.GetLuxColFromEntry(damage);
	}	

	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
	{
		if(par2!=0)
			return LuxLevels.instance.GetLuxColFromEntry(par1ItemStack.getItemDamage());
		else
			return 0xffffff;
	}



	//	@SideOnly(Side.CLIENT)
	//	public boolean hasEffect(ItemStack par1ItemStack)
	//	{
	//		return par1ItemStack.getItemDamage()>0;
	//	}

	@SideOnly(Side.CLIENT)

	/**
	 * allows items to add custom lines of information to the mouseover description
	 */
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		if(LuxLevels.instance.getItemStack(par1ItemStack.getItemDamage())==null)
			return;
		int id = LuxLevels.instance.getItemStack(par1ItemStack.getItemDamage()).itemID;
		int damage = LuxLevels.instance.getItemStack(par1ItemStack.getItemDamage()).getItemDamage();

		if(id < 0) return;

		Item temp = Item.itemsList[id];

		if(temp != null){
			String name1 = StatCollector.translateToLocal(temp.getItemName());

			if(temp.getHasSubtypes()){
				name1 = StatCollector.translateToLocal(temp.getItemNameIS(new ItemStack(temp,1,damage)));
			}

			if(name1 == null) name1 = "";

			String name = ("" + StringTranslate.getInstance().translateNamedKey(name1)).trim();

			par3List.set(0, name +" " + (String)par3List.get(0));


			LuxPacket tempPacket = LuxLevels.instance.GetLuxPacket(id, damage);

			if(tempPacket!=null){
				for(byte c = 0;c<7;c++)
					if(tempPacket.luxLevel[c]>0)
						par3List.add(LuxHelper.color_str[c]+LuxHelper.color_abb[c]+" "+LuxHelper.display(tempPacket.luxLevel[c]));

			}

		}
	}


	public static ItemStack newSchema(ItemStack par1ItemStack){
		if(par1ItemStack == null)
			return null;

		int damage = 0;
		if(par1ItemStack.itemID!=Luxcraft.schema.itemID){
			if(par1ItemStack.getItem().getHasSubtypes())
				damage = par1ItemStack.getItemDamage();
		}

		ItemStack newItem = newSchema(par1ItemStack.itemID, damage);
		return newItem;
	}


	public static ItemStack newSchema(int id){
		return newSchema(id, 0);
	}


	public static ItemStack newSchema(int id, int damage){
		if(Item.itemsList[id] == null)
			return null;
		
		if(id == Luxcraft.schemaId || id == Luxcraft.deconsRecipeId)
			return null;
		
		//		System.out.println(Item.itemsList[id].getItemName()+" "+id+" "+LuxLevels.instance.getLuxItemId(new ItemStack(id,1,damage)));
		
		int luxdamage = LuxLevels.instance.getLuxItemId(new ItemStack(id,1,damage));
		
		if(luxdamage == -1)
			return null;

		ItemStack newItem = new ItemStack(Luxcraft.schema, 1, luxdamage);
		return newItem;
	}



	public static ItemStack createdItem(ItemStack par1ItemStack){
		if(par1ItemStack == null)
			return null;

		return LuxLevels.instance.getItemStack(par1ItemStack.getItemDamage());
	}


	/**
	 * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
	 */
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		par3List.add(new ItemStack(par1, 1, 0));
		par3List.add(newSchema(new ItemStack(Block.cobblestone)));
		par3List.add(newSchema(Item.enderPearl.itemID));
		par3List.add(newSchema(Item.ingotIron.itemID));
		par3List.add(newSchema(Item.ingotGold.itemID));
		par3List.add(newSchema(Item.diamond.itemID));
	}


}
