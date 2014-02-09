package luxcraft;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiLuxDeconstructor extends GuiContainer
{
	private TileEntityLuxDeconstructor DeconstructorInventory;

	public GuiLuxDeconstructor(InventoryPlayer par1InventoryPlayer, TileEntityLuxDeconstructor par2TileEntityLuxDeconstructor)
	{
		super(new ContainerLuxDeconstructor(par1InventoryPlayer, par2TileEntityLuxDeconstructor));
		this.DeconstructorInventory = par2TileEntityLuxDeconstructor;
		this.ySize = 210;
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 */
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{

		//		for(byte c = 0;c < 7;c++){
		//			String temp = LuxHelper.display(DeconstructorInventory.GetLuxLevel(c))+"/"+LuxHelper.display(DeconstructorInventory.curItemCost(c));
		//			this.fontRenderer.drawString(temp, 105-this.fontRenderer.getStringWidth(temp), 6 + 11 * c, LuxHelper.color_int[c]);			
		//		}

		//		String temp = StatCollector.translateToLocal("tile.luxDeconstructor.name");
		//		this.fontRenderer.drawString(temp, (this.xSize-this.fontRenderer.getStringWidth(temp))/2, 6, 4210752);

		//		int var5 = (this.width - this.xSize) / 2;
		//		int var6 = (this.height - this.ySize) / 2;

		//		int mouse_x = par1-var5;
		//		int mouse_y = par2-var6;

		//		if(mouse_y>27 & mouse_y < 27+131){
		//			int dx = (mouse_x-9)/23;
		//			if(dx>=0 & dx<7){
		//				temp = LuxHelper.color_name[dx] + " " + LuxHelper.display(DeconstructorInventory.GetLuxLevel((byte)dx));
		//				this.fontRenderer.drawString(temp, (this.xSize-this.fontRenderer.getStringWidth(temp))/2, 16, LuxHelper.color_int[dx]);
		//
		//			}
		//		}


	}

	/**
	 * Draw the background layer for the GuiContainer (everything behind the items)
	 */
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		int var4 = this.mc.renderEngine.getTexture("/luxcraft/gui/luxDeconstructor.png");
		GL11.glColor4f(1.0F, 1F, 1F, 1.0F);
		this.mc.renderEngine.bindTexture(var4);
		int var5 = (this.width - this.xSize) / 2;
		int var6 = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);

		int var7;
		for(byte i = 0;i < 7;i++){
			if(DeconstructorInventory.MaxLuxLevel(i) > 0){
				GL11.glColor4d(LuxHelper.r[i], LuxHelper.g[i], LuxHelper.b[i], 1);
				var7 = (int)(Math.min((double)DeconstructorInventory.GetLuxLevel(i)/(double)DeconstructorInventory.MaxLuxLevel(i),1)*60);
				this.drawTexturedModalRect(var5 + 108, var6 + 7 + 11 * i, 176, 31, var7, 6);
			}
		}

		GL11.glColor4d(1,1,1, 1);
		for(int i = 0; i < 5; i++){
			for(int j = 0; j < 3; j++){
				if(((Slot) this.inventorySlots.inventorySlots.get(i+j*5)).getStack()!=null){
					int t = getMaxTotalLux(((Slot) this.inventorySlots.inventorySlots.get(i+j*5)).getStack());
					
					int progress = this.DeconstructorInventory.progress[i+j*5];

					if(t==0) t= -1;
					else if(t<progress) t=16;
					else t=1+(15*progress)/t;
					
					if(t==-1)
						this.drawTexturedModalRect(var5 + 8 + i * 18, var6 + 27 + j * 18, 176, 53, 16, 16);
					else{
						this.drawTexturedModalRect(var5 + 8 + i * 18, var6 + 27 + j * 18, 176, 37, t, 16);
					}
				}
			}
		}
	}
	
	
	private LuxPacket getMaxLuxPacket(ItemStack item){
		if(item==null)
			return null;

		for(int i = 15; i < 33; i++){
			ItemStack inv = ((Slot) this.inventorySlots.inventorySlots.get(i)).getStack();
			if(inv != null){
				if(inv.itemID == Luxcraft.schema.itemID)
				//	if(item.itemID == inv.getItemDamage())
						if(ItemLuxSchema.createdItem(inv).isItemEqual(item)){
							return LuxLevels.instance.GetLuxPacket(item);
						}
				
				if(inv.itemID == Luxcraft.deconsRecipe.itemID)
					if(item.itemID == inv.getItemDamage())
						if(ItemLuxDeconsRecipe.createdItem(inv).isItemEqual(item)){
							return ItemLuxDeconsRecipe.getLuxData(inv);
						}
			}

		}

		return null;
	}

	private int getMaxTotalLux(ItemStack item){
		return totalLux(getMaxLuxPacket(item));
	}
	
	private static int totalLux(LuxPacket t){if(t==null) return 0; else return t.totalLux();}

}
