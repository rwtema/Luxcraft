package luxcraft;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public class RecipeDeconsRecipe implements IRecipe {
	private static ContainerFake fakeCont = new ContainerFake(); 
	private static InventoryCrafting tempInv = new InventoryCrafting(fakeCont, 3, 3);
	
	public ItemStack getOutput(InventoryCrafting var1){
		ItemStack newSchema = null;
		boolean hasSchema = false;

		for(int i = 0; i < 3 & !hasSchema; i++){
			for(int j = 0; j < 3 & !hasSchema; j++){
				ItemStack temp =var1.getStackInRowAndColumn(i, j);
				if(temp != null){
					if(temp.itemID == Luxcraft.schema.itemID | temp.itemID == Luxcraft.deconsRecipe.itemID){
						hasSchema=true;
					}
				}
			}
		}

		if(!hasSchema) return null;



		boolean synth = true;

		LuxPacket tempLux = new LuxPacket();

		for(int i = 0; i < var1.getSizeInventory(); i++){
			ItemStack temp =var1.getStackInSlot(i);
			if(temp != null){
				if(temp.itemID == Luxcraft.schema.itemID){
					tempInv.setInventorySlotContents(i, ItemLuxSchema.createdItem(temp));
					tempLux = tempLux.add(LuxLevels.instance.GetLuxPacket(ItemLuxSchema.createdItem(temp)));
				}
				else if(temp.itemID == Luxcraft.deconsRecipe.itemID){
					tempInv.setInventorySlotContents(i, ItemLuxDeconsRecipe.createdItem(temp));
					tempLux = tempLux.add(ItemLuxDeconsRecipe.getLuxData(temp));
					if(synth) synth = ItemLuxDeconsRecipe.isSynthesizable(temp);
				}else{
					tempInv.setInventorySlotContents(i, temp);
					synth=false;
				}
			}else
				tempInv.setInventorySlotContents(i,null);
		}

		if(tempLux.isEmpty())
			return null;



		ItemStack output = CraftingManager.getInstance().findMatchingRecipe(tempInv, FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(0));
		if(output == null) return null;
		if(output.hasTagCompound()) return null;
		if(output.stackSize>0)
			tempLux=tempLux.div(output.stackSize);
		
		if(LuxLevels.instance.GetLuxPacket(output) != null){
			return ItemLuxSchema.newSchema(output);
		}
		
		return ItemLuxDeconsRecipe.newDeconRecipe(output, tempLux, synth);
	}


	@Override
	public boolean matches(InventoryCrafting var1, World var2) {
		return getOutput(var1) != null;
	}

	public ItemStack getCraftingResult(InventoryCrafting var1) {
		return getOutput(var1);
	}

	public int getRecipeSize() {
		return 2;
	}

	public ItemStack getRecipeOutput() {
		return null;
	}

}


