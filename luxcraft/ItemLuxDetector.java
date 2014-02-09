package luxcraft;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemLuxDetector extends ItemBlock
{
    public ItemLuxDetector(int par1)
    {
        super(par1);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        this.setItemName("luxDetector");
    }

    /**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
    public int getMetadata(int par1)
    {
        return par1;
    }

    public String getItemNameIS(ItemStack par1ItemStack)
    {
    	if(par1ItemStack.getItemDamage()==1)
    		return super.getItemName()+".inverted";
        return super.getItemName();
    }
}
