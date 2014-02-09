package luxcraft;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;

public class TileEntityLuxSynthesizer extends TileEntityLuxContainer implements ILuxContainer, ISidedInventory {
	private static final int invSize = 2;
	private ItemStack[] invContents = new ItemStack[invSize];
	private ItemStack itemAssembling = null;
	private boolean accurateMax = false;
	public int type;


	public void updateEntity(){
		super.updateEntity();
		if(!CheckAndCreate())
			decay();
		if(canRecycleLux())
			recycleLux();
	}


	public int getType(){
		if(this.worldObj==null){
			return(-1);
		}else{
			if(this.worldObj.getBlockId(xCoord, yCoord, zCoord)!=0)
				return this.worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
			else 
				return -1;
		}
	}

	public void recycleLux(){
		boolean change = false;
		change = true;
		while(change){
			change=false;
			for(byte i = 0; i < 7; i++){
				if(this.luxStorage.luxLevel[i]>this.curItemCost(i)){
					for(byte j = 0; j < 7; j++){
						byte k = (byte) (LuxHelper.convOrder[i][j]-1);
						while(this.luxStorage.luxLevel[k]<this.curItemCost(k) & this.luxStorage.luxLevel[i]>=this.curItemCost(i)+LuxHelper.convRate[i][k]){
							this.luxStorage.luxLevel[k]++;
							this.luxStorage.luxLevel[i]-=LuxHelper.convRate[i][k];
							change = true;
						}
					}
				}
			}
		}

		if(change)
			this.updateContainerUsers();
	}

	public LuxPacket insertLux(LuxPacket packet, boolean simulate) {
		if(packet!=null){

			LuxPacket newPacket; 

			if (canExplode()){
				newPacket = super.insertLux(packet, simulate);
				for(byte c = 0;c<7;c++){
					if(this.curItemCost(c) != 0 & this.GetLuxLevel(c, simulate) >= (this.MaxLuxLevel(c)-1)){
						if(!simulate){
							this.worldObj.setBlockWithNotify(xCoord, yCoord, zCoord, 0);
							this.worldObj.createExplosion((Entity)null, this.xCoord+0.5, this.yCoord+0.5, this.zCoord+0.5, 4.0F,  this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
						}
						return new LuxPacket();
					}
				}
			}
			else{
				newPacket = super.insertLux(packet, simulate);
			}

		}
		return new LuxPacket();
	}

	public boolean canExplode(){
		return this.getType()==1;
	}

	public boolean canExtract(){
		return false;
	}


	public LuxPacket extractLux(LuxPacket packet, boolean simulate){
		return new LuxPacket();
	}

	public boolean canRecycleLux(){
		return getType() == 2;
	}

	public double costMultiplier(){
		int type = getType();
		if(type == 0)
			return 1.1;
		else if (type == 1)
			return 1;
		else 
			return 2;
	}

	public boolean CheckAndCreate(){
		if(this.invContents[0]!=null){
			ItemStack target = null;

			if(this.invContents[0].itemID == Luxcraft.schema.itemID){
				target = ItemLuxSchema.createdItem(this.invContents[0]);
			}

			if(this.invContents[0].itemID == Luxcraft.deconsRecipe.itemID & getType() == 0){
				if(ItemLuxDeconsRecipe.isSynthesizable(this.invContents[0]))
					target = ItemLuxDeconsRecipe.createdItem(this.invContents[0]);
			}

			if (target != null){
				if(this.invContents[1] != null){
					if(!target.isItemEqual(this.invContents[1]))
						return false;

					if(this.invContents[1].stackSize >= target.getMaxStackSize())
						return false;
				}

				LuxPacket targetLux = LuxLevels.instance.GetLuxPacket(target);
				boolean exit=false;
				for(byte c = 0; c < 7; c++){
					if(curItemCost(c) > 0 & this.luxStorage.luxLevel[c] < curItemCost(c)){
						return false;
					}
				}

				for(byte c = 0; c < 7; c++){
					this.luxStorage.luxLevel[c]-=curItemCost(c);
				}

				if(this.invContents[1] == null)
					this.setInventorySlotContents(1, target);
				else if(target.isItemEqual(this.invContents[1])){
					this.invContents[1].stackSize++;
					return true;
				}
			}


		}
		return false;
	}

	public void decay(){
		if(getType()==1)
			for(byte c = 0; c < 7; c++){
				if(this.luxStorage.luxLevel[c] > curItemCost(c)){
					if(this.worldObj.getWorldTime() % 40 == 10){
						this.luxStorage.luxLevel[c]--;
						this.updateContainerUsers();
					}
				}
			}
	}


	public boolean canUseDeconsRecipe(){
		return getType() == 0;
	}

	public int curItemCost(byte color){		
		if(this.invContents[0]!=null){
			if(this.invContents[0].itemID == Luxcraft.schema.itemID){
				if(ItemLuxSchema.createdItem(this.invContents[0])==null)
					return 0;
				if(LuxLevels.instance.GetLuxPacket(ItemLuxSchema.createdItem(this.invContents[0]))==null)
					return 0;
				return (int)(LuxLevels.instance.GetLuxPacket(ItemLuxSchema.createdItem(this.invContents[0])).luxLevel[color]*costMultiplier());
			}

			if(this.invContents[0].itemID == Luxcraft.deconsRecipe.itemID & canUseDeconsRecipe()){
				if(ItemLuxDeconsRecipe.isSynthesizable(this.invContents[0]))
					return (int)(ItemLuxDeconsRecipe.getLuxData(this.invContents[0]).luxLevel[color]*costMultiplier());
			}			
		}
		return 0;
	}


	public int getSizeInventory() {
		return invSize;
	}


	public ItemStack getStackInSlot(int i) {
		return invContents[i];
	}

	public ItemStack decrStackSize(int par1, int par2) {
		if (this.invContents[par1] != null)
		{
			ItemStack var3;

			if (this.invContents[par1].stackSize <= par2)
			{
				var3 = this.invContents[par1];
				this.invContents[par1] = null;
				this.onInventoryChanged();
				return var3;
			}
			else
			{
				var3 = this.invContents[par1].splitStack(par2);

				if (this.invContents[par1].stackSize == 0)
				{
					this.invContents[par1] = null;
				}

				this.onInventoryChanged();
				return var3;
			}
		}
		else
		{
			return null;
		}
	}

	public ItemStack getStackInSlotOnClosing(int par1) {
		if (this.invContents[par1] != null)
		{
			ItemStack var2 = this.invContents[par1];
			this.invContents[par1] = null;
			return var2;
		}
		else
		{
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
		this.invContents[par1] = par2ItemStack;

		if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
		{
			par2ItemStack.stackSize = this.getInventoryStackLimit();
		}

		this.onInventoryChanged();		
	}


	public String getInvName() {
		return "synthesizer.name";
	}

	public int getInventoryStackLimit() {
		return 64;
	}

	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
		return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
	}

	public void openChest() {
	}

	public void closeChest() {}


	public int MaxLuxLevel(byte color) {
		int t = 0;
		if(canRecycleLux()){
			t=t+1024*12;
		}

		if(canExplode()){
			if(curItemCost(color)!=0)
				t+=128*12;
		}

		return curItemCost(color)+t;
	}	

	/**
	 * Reads a tile entity from NBT.
	 */
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);
		NBTTagList var2 = par1NBTTagCompound.getTagList("Items");
		this.invContents = new ItemStack[this.getSizeInventory()];

		for (int var3 = 0; var3 < var2.tagCount(); ++var3)
		{
			NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
			int var5 = var4.getByte("Slot") & 255;

			if (var5 >= 0 && var5 < this.invContents.length)
			{
				this.invContents[var5] = ItemStack.loadItemStackFromNBT(var4);
			}
		}
		//insertLimit=getLuxInsertLimit();
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
		NBTTagList var2 = new NBTTagList();

		for (int var3 = 0; var3 < this.invContents.length; ++var3)
		{
			if (this.invContents[var3] != null)
			{
				NBTTagCompound var4 = new NBTTagCompound();
				var4.setByte("Slot", (byte)var3);
				this.invContents[var3].writeToNBT(var4);
				var2.appendTag(var4);
			}
		}

		par1NBTTagCompound.setTag("Items", var2);
	}


	@Override
	public int getStartInventorySide(ForgeDirection side) {
		return 1;
	}


	@Override
	public int getSizeInventorySide(ForgeDirection side) {
		return 1;
	}



}
