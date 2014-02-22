package com.rwtema.luxcraft;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.rwtema.luxcraft.luxapi.LuxStack;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityList.EntityEggInfo;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;

public class LuxLevels {
	private List<LuxLevelsEntry> luxEntries = new ArrayList<LuxLevelsEntry>();
	public static LuxLevels instance = new LuxLevels();
	private Random cols = new Random(0);

	public void initLevels() {
		//
		// addLuxEntry(new ItemStack(Block.cobblestone), new LuxPacket(0,12),
		// true);
		// addLuxEntry(new ItemStack(Block.stone), new LuxPacket(0,12), true);
		// addLuxEntry(new ItemStack(Block.dirt), new LuxPacket(0,12), true);
		// addLuxEntry(new ItemStack(Block.grass), new LuxPacket(0,12), true);
		// addLuxEntry(new ItemStack(Block.gravel), new LuxPacket(0,12), true);
		// //addLuxEntry(new ItemStack(Block.sand), new LuxPacket(0,12), true);
		// addLuxEntry(new ItemStack(Block.sapling), (new
		// LuxPacket(2,12)).add((byte)0, 12), true);
		// addLuxEntry(new ItemStack(Block.wood,1,0), (new
		// LuxPacket(2,12*12)).add((byte)0, 12*12), true);
		// addLuxEntry(new ItemStack(Block.wood,1,1), (new
		// LuxPacket(2,12*12)).add((byte)0, 12*12), true);
		// addLuxEntry(new ItemStack(Block.wood,1,2), (new
		// LuxPacket(2,12*12)).add((byte)0, 12*12), true);
		// addLuxEntry(new ItemStack(Block.wood,1,3), (new
		// LuxPacket(2,12*12)).add((byte)0, 12*12), true);
		// addLuxEntry(new ItemStack(Item.stick), (new
		// LuxPacket(2,12*12/8)).add((byte)0, 12*12/8),true);
		//
		// addLuxEntry(new ItemStack(Item.bucketEmpty), new LuxPacket() ,
		// false);
		// addLuxEntry(new ItemStack(Item.bucketWater), new
		// LuxPacket(3,12),true);
		// addLuxEntry(new ItemStack(Item.bucketLava), (new
		// LuxPacket(1,32*12)).add((byte)4,32*12),true);
		//
		// addLuxEntry(new ItemStack(Item.ingotIron), new
		// LuxPacket(80*12,16*12,16*12,16*12,0,0,0), true);
		// addLuxEntry(new ItemStack(Item.redstone), new LuxPacket(0,64*12),
		// true);
		// addLuxEntry(new ItemStack(Item.ingotGold), new
		// LuxPacket(5,1024*12).add(0, 16*12), true);
		// addLuxEntry(new ItemStack(Item.dyePowder, 1, 4), new
		// LuxPacket(5,512*12), true);
		// addLuxEntry(new ItemStack(Item.egg), new LuxPacket(4,16*12), true);
		// addLuxEntry(new ItemStack(Item.rottenFlesh), new
		// LuxPacket(0,5*12,5*12,5*12,20*12,0,0), true);
		// addLuxEntry(new ItemStack(Item.gunpowder), new
		// LuxPacket(0,30*12,0,0,5*12,5*12,0), true);
		// addLuxEntry(new ItemStack(Item.seeds), new LuxPacket(2,16*12), true);
		// addLuxEntry(new ItemStack(Item.silk), (new
		// LuxPacket(2,12)).add((byte)0, 12), true);
		// addLuxEntry(new ItemStack(Item.enderPearl), (new
		// LuxPacket(6,12*128)), true);
		// addLuxEntry(new ItemStack(Item.diamond), new
		// LuxPacket(1024*12,128*12,128*12,128*12,128*12,128*12,128*12), true);
		//
		// addLuxEntry(new ItemStack(Block.sand), new LuxPacket(0, 2*12), true);
		// addLuxEntry(new ItemStack(Block.glass), new LuxPacket(0, 12).add(3,
		// 12), true);
		//
		//
		// addLuxEntry(Item.clay.getItem(), new LuxPacket(3, 16*12).add(2,
		// 16*12).add(0, 16*12), true);
		//
		// addLuxEntry(Block.cactus.blockID, new LuxPacket(2, 15*12).add(3,
		// 15*12), true);
		//
		// addLuxEntry(Item.coal.getItem(),-1,new LuxPacket(1, 64*12), true);
		//
		// addLuxEntry(new ItemStack(Item.emerald), new LuxPacket(1024*12,
		// 170*12, 170*12, 170*12, 512*12, 512*12, 512*12),true);
		//
		// for(int i = 0; i < BlockCobblestoneCompressed.maxIterations; i++)
		// addLuxEntry(new ItemStack(Luxcraft.cobblestoneCompr,1,i), new
		// LuxPacket(0,12*(int)Math.pow(9, 1+i)),true);
		//

		// addLuxEntry(new ItemStack(Item.emerald), new LuxPacket(1024*12,
		// 170*12, 170*12, 170*12, 512*12, 512*12, 512*12),true);

		// while(addDerivedEntries()){System.out.println("iterate");}

		// for(int i = 0;i<luxEntries.size();i+=1){
		// System.out.print(Item.itemsList[luxEntries.get(i).item].getItemName()+" ");
		// if(Item.itemsList[luxEntries.get(i).item].getHasSubtypes())
		// System.out.print(luxEntries.get(i).metadata+" ");
		// System.out.println(((double)luxEntries.get(i).getLuxPacket().totalLux())/12);
		// }

		// for(int i = 0;i<Item.itemsList.length;i+=1){
		// if(Item.itemsList[i]!=null)
		// if(this.GetLuxPacket(i) == null){
		// System.out.println(Item.itemsList[i].getItemName()+"   Unregistered");
		// }
		// }

	}

	public void addLuxEntry(ItemStack par1, LuxStack lux, boolean deconstructable) {
		luxEntries.add(new LuxLevelsEntry(par1, lux, deconstructable));
	}

	public void addLuxEntry(Item itemId, LuxStack lux, boolean deconstructable) {
		luxEntries.add(new LuxLevelsEntry(itemId, lux, deconstructable));
	}

	public void addLuxEntry(Item itemId, int metadata, LuxStack lux, boolean deconstructable) {
		luxEntries.add(new LuxLevelsEntry(itemId, metadata, lux, deconstructable));
	}

	public void addLuxEntry(LuxLevelsEntry par1) {
		luxEntries.add(par1);
	}

	// public boolean addDerivedEntries() {
	// List recipes = CraftingManager.getInstance().getRecipeList();
	// Map smeltingRecipes = FurnaceRecipes.smelting().getSmeltingList();
	//
	// boolean change = false;
	//
	// for (int i = 0; i < recipes.size(); i += 1) {
	//
	// if (recipes.get(i) instanceof ShapedRecipes) {
	// ShapedRecipes temp = (ShapedRecipes) recipes.get(i);
	//
	// if (GetLuxPacket(temp.getRecipeOutput()) == null) {
	// LuxPacket tempEnergy = new LuxPacket();
	// boolean add = true;
	// for (int j = 0; add & j < temp.recipeItems.length; j += 1) {
	// if (temp.recipeItems[j] != null) {
	// LuxPacket t = GetLuxPacket(temp.recipeItems[j]);
	// if (t != null)
	// tempEnergy = tempEnergy.add(t);
	// else
	// add = false;
	// }
	// }
	//
	// if (add & !tempEnergy.isEmpty()
	// & temp.getRecipeOutput().stackSize > 0) {
	// tempEnergy = tempEnergy.mult(1 / (double) temp
	// .getRecipeOutput().stackSize);
	//
	// addLuxEntry(temp.getRecipeOutput(), tempEnergy, true);
	// if (!change)
	// System.out.println(temp.getRecipeOutput()
	// .getItemName());
	// change = true;
	// }
	// }
	//
	// }
	//
	// if (recipes.get(i) instanceof ShapelessRecipes) {
	// ShapelessRecipes temp = (ShapelessRecipes) recipes.get(i);
	// LuxPacket tempEnergy = new LuxPacket();
	//
	// if (GetLuxPacket(temp.getRecipeOutput()) == null) {
	// boolean add = true;
	// for (int j = 0; j < temp.recipeItems.size(); j += 1) {
	// if (temp.recipeItems.get(j) != null) {
	// LuxPacket t = GetLuxPacket((ItemStack) temp.recipeItems
	// .get(j));
	// if (t == null)
	// add = false;
	// else
	// tempEnergy = tempEnergy.add(t);
	// }
	// }
	//
	// if (add & !tempEnergy.isEmpty()
	// & temp.getRecipeOutput().stackSize > 0) {
	// tempEnergy = tempEnergy.mult(1 / (double) temp
	// .getRecipeOutput().stackSize);
	// change = true;
	// if (!change)
	// System.out.println(temp.getRecipeOutput()
	// .getItemName());
	// addLuxEntry(temp.getRecipeOutput(), tempEnergy, false);
	// }
	// }
	//
	// }
	// }
	//
	// int k = 0;
	// for (Iterator f = smeltingRecipes.keySet().iterator(); f.hasNext(); k =
	// (Integer) (f
	// .next())) {
	// LuxPacket temp = GetLuxPacket(k);
	// if (temp != null) {
	// ItemStack t2 = (ItemStack) smeltingRecipes.get(k);
	// if (GetLuxPacket(t2) == null) {
	// addLuxEntry(t2, temp.mult(1 / (double) t2.stackSize), false);
	// if (!change)
	// System.out.println(t2.getItemName());
	// change = true;
	// }
	// }
	//
	// }
	//
	// return change;
	// }

	//
	// public ItemStack getItemStack(int id) {
	// if (id < 0) {
	// return new ItemStack(Item.itemsList[-id], 1);
	// } else if (id > 0) {
	// id--;
	// if (id > luxEntries.size())
	// return null;
	// if (luxEntries.get(id).metadata == -1)
	// return new ItemStack(Item.itemsList[luxEntries.get(id).item], 1);
	// else
	// return new ItemStack(Item.itemsList[luxEntries.get(id).item],
	// 1, luxEntries.get(id).metadata);
	// }
	//
	// return null;
	// }

	public LuxStack GetLuxPacket(ItemStack par1) {
		// if (par1 == null)
		return null;
		// return GetLuxPacket(par1.getItem(), par1.getItemDamage());
	}

	// public LuxPacket GetLuxPacket(int itemId) {
	// return GetLuxPacket(itemId, -1);
	// }
	//
	// public LuxPacket GetLuxPacket(int itemId, int damage) {
	// for (int i = 0; i < luxEntries.size(); i++) {
	// if (luxEntries.get(i).item == itemId
	// & (luxEntries.get(i).metadata == damage | luxEntries.get(i).metadata ==
	// -1)) {
	// return luxEntries.get(i).getLuxPacket().copy();
	// }
	// }
	// return null;
	// }
	//
	// public int GetLuxColFromEntry(int entryId) {
	// if (entryId < luxEntries.size()) {
	// return luxEntries.get(entryId).color;
	// }
	// return 0xff00ff;
	// }
	//
	// public int GetLuxCol(int itemId, int damage) {
	// for (int i = 0; i < luxEntries.size(); i++) {
	// if (luxEntries.get(i).item == itemId
	// & (luxEntries.get(i).metadata == damage | luxEntries.get(i).metadata ==
	// -1)) {
	// return luxEntries.get(i).color;
	// }
	// }
	// return 0xffffff;
	// }
	//
	// public ItemStack getEntityEgg(String monster) {
	// Iterator var4 = EntityList.entityEggs.values().iterator();
	//
	// while (var4.hasNext()) {
	// EntityEggInfo var5 = (EntityEggInfo) var4.next();
	// if (EntityList.getStringFromID(var5.spawnedID) == monster)
	// return (new ItemStack(Item.monsterPlacer.getItem(), 1,
	// var5.spawnedID));
	// }
	// return null;
	// }
	//
	// public boolean addLuxSchemaRecipe(ItemStack par1ItemStack,
	// Object... par2ArrayOfObj) {
	// if (par1ItemStack == null)
	// return false;
	//
	// LuxPacket resultLux = new LuxPacket();
	//
	// for (int i = 0; i < par2ArrayOfObj.length; i++) {
	//
	// if (par2ArrayOfObj[i] instanceof Item) {
	// par2ArrayOfObj[i] = (ItemStack) (par2ArrayOfObj[i]);
	// } else if (par2ArrayOfObj[i] instanceof Block) {
	// par2ArrayOfObj[i] = new ItemStack((Block) par2ArrayOfObj[i], 1,
	// -1);
	// }
	//
	// if (par2ArrayOfObj[i] instanceof ItemStack) {
	// ItemStack temp = ItemLuxSchema
	// .newSchema(((ItemStack) par2ArrayOfObj[i]));
	// if (temp != null) {
	// resultLux = resultLux.add(GetLuxPacket(
	// (ItemStack) par2ArrayOfObj[i]).mult(
	// ((ItemStack) par2ArrayOfObj[i]).stackSize));
	// par2ArrayOfObj[i] = temp;
	// } else
	// return false;
	// }
	// }
	//
	// addLuxEntry(par1ItemStack, resultLux, true);
	// ItemStack result = ItemLuxSchema.newSchema(par1ItemStack);
	// CraftingManager.getInstance().addRecipe(result, par2ArrayOfObj);
	//
	// return true;
	// }

}
