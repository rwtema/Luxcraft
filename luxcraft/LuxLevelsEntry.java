package luxcraft;

import java.util.Random;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class LuxLevelsEntry {
	public int item;
	public int metadata;
	public LuxPacket lux;
	public boolean constructable;
	public int color;
	private static Random cols = new Random(); 
	
	
	

	public LuxLevelsEntry(ItemStack par1, LuxPacket par2, boolean constructable){
		this(par1.itemID, par1.getItemDamage(), par2, constructable);
		
	}

	public LuxLevelsEntry(Item par1, LuxPacket par2, boolean constructable){		
		this(par1.itemID, -1, par2, constructable);
	}	

	public LuxLevelsEntry(int itemId, LuxPacket par2, boolean constructable){
		this(itemId, -1, par2, constructable);
	}

	public LuxLevelsEntry(int itemId, int meta_data, LuxPacket par2, boolean constructable){
		item = itemId;
		metadata = meta_data;
		lux = par2;
		this.constructable = constructable;
		color=cols.nextInt(256*256*256);
	}		

	public boolean itemMatches(ItemStack otherItem){
		if(item == otherItem.itemID){
			if(Item.itemsList[item].getHasSubtypes() & otherItem.getItemDamage() != -1)
				return otherItem.getItemDamage() == metadata;
			else
				return true;
		}
		return false;
	}


	public LuxPacket getLuxPacket(){
		return lux;
	}

}
