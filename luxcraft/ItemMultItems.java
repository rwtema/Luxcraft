package luxcraft;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemMultItems extends ItemBlock
{
    public ItemMultItems(int par1)
    {
        super(par1);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
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
        return super.getItemName() + "." + par1ItemStack.getItemDamage();
    }
}
