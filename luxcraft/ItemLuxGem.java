package luxcraft;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemLuxGem extends Item
{
    public ItemLuxGem(int par1)
    {
        super(par1);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        this.setCreativeTab(CreativeTabs.tabBlock);
		this.setIconIndex(50);
		this.setTextureFile("/luxcraft/terrain.png");
    }

    /**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
    public int getMetadata(int par1)
    {
        return par1;
    }
    
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
    	if(par1 >= 7 | par2 == 0)
    		return this.iconIndex;
    	
        return this.iconIndex+1;
    }
    
    @SideOnly(Side.CLIENT)
    public int getColorFromDamage(int damage){
    	if(damage < 0 | damage >= 7)
    		return 16777215;
    	return LuxHelper.color_int[damage];
    }
    
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
    {
        return par2 == 0 ? 16777215 : this.getColorFromDamage(par1ItemStack.getItemDamage());
    }

    public String getItemNameIS(ItemStack par1ItemStack)
    {
        return super.getItemName() + "." + par1ItemStack.getItemDamage();
    }
    
    /**
	 * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
	 */
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for(int i = 0;i < 8;i++)
			par3List.add(new ItemStack(par1, 1, i));
	}
}
