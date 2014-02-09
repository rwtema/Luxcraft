package luxcraft;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class RecipeRitualTable implements IRecipe {
	public boolean matches(InventoryCrafting var1, World var2) {
		if(!isId(var1.getStackInRowAndColumn(1, 1), Block.workbench.blockID))
			return false;
		

		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++){
				if(var1.getStackInRowAndColumn(i, j) == null)
					return false;
				if(i!=1 | j!=1)
					if(!isId(var1.getStackInRowAndColumn(i, j), Item.silk.itemID) & !isId(var1.getStackInRowAndColumn(i, j), Item.redstone.itemID))
						return false;
			}
		
	
		if(var1.getStackInRowAndColumn(0, 1).itemID != var1.getStackInRowAndColumn(2, 1).itemID)
			return false;

		if(var1.getStackInRowAndColumn(1, 0).itemID != var1.getStackInRowAndColumn(1, 2).itemID)
			return false;		

		return true;
	}

	public boolean isId(ItemStack t, int id){
		if(t != null)
			if(t.itemID == id)
				return true;
		return false;
	}

	public ItemStack getCraftingResult(InventoryCrafting var1) {
		return new ItemStack(Luxcraft.ritualTable);
	}

	public int getRecipeSize() {
		return 9;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return null;
	}

}
