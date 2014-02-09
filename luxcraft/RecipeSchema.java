package luxcraft;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class RecipeSchema implements IRecipe {

	@Override
	public boolean matches(InventoryCrafting var1, World var2) {
		ItemStack newSchema = null;
		boolean hasSchema = false;
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++){
				ItemStack temp =var1.getStackInRowAndColumn(i, j);
				if(temp != null){
					if(temp.itemID == Luxcraft.schema.itemID){
						if(hasSchema)
							return false;
						else
							hasSchema=true;
					}
					else{
						if(newSchema == null){
							if(LuxLevels.instance.GetLuxPacket(temp)==null)
								return false;
							newSchema=temp;
						}else
							return false;
					}

				}
			}
		
		if(hasSchema & newSchema != null)
			return true;
		return false;
	}

	public ItemStack getCraftingResult(InventoryCrafting var1) {
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++){
				ItemStack temp =var1.getStackInRowAndColumn(i, j);
				if(temp != null){
					if(temp.itemID != Luxcraft.schema.itemID){
						return ((ItemLuxSchema)Luxcraft.schema).newSchema(temp);
					}
				}
			}

		return new ItemStack(Luxcraft.schema);
	}

	public int getRecipeSize() {
		return 2;
	}

	public ItemStack getRecipeOutput() {
		return null;
	}

}
