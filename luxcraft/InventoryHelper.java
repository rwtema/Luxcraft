package luxcraft;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InventoryHelper {
	public static ItemStack insert(ItemStack items, IInventory inv, int slot){
		return insert(items, inv, slot, slot+1);
	}	
	
	public static ItemStack insert(ItemStack items, IInventory inv, int start, int end){
		if(items == null) return null;
		if(inv == null) return items;
		if(start>=end | start < 0) return items;
		if(start >= inv.getSizeInventory()) return items; 
		if(end > inv.getSizeInventory()) end = inv.getSizeInventory();
		
		for(int i = start; i < end; i++){
			
		}
		
		return items;
	}
	
	
	
}
