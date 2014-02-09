package luxcraft;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingSand;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntitySubsidanceNuke extends Entity
{
	/** How long the fuse is */
	public int fuse;
	
	private final static int rowSpeed = 10;

	public EntitySubsidanceNuke(World par1World)
	{
		super(par1World);
		this.fuse = 80;
		this.preventEntitySpawning = true;
		this.setSize(0.98F, 0.98F);
		this.yOffset = this.height / 2.0F;
	}

	public EntitySubsidanceNuke(World par1World, double par2, double par4, double par6)
	{
		this(par1World);
		this.setPosition(par2, par4, par6);
		float var8 = (float)(Math.random() * Math.PI * 2.0D);
		this.motionX = (double)(-((float)Math.sin((double)var8)) * 0.02F);
		this.motionY = 0.20000000298023224D;
		this.motionZ = (double)(-((float)Math.cos((double)var8)) * 0.02F);
		this.fuse = 80;
		this.prevPosX = par2;
		this.prevPosY = par4;
		this.prevPosZ = par6;
	}

	protected void entityInit() {}

	/**
	 * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
	 * prevent them from trampling crops
	 */
	protected boolean canTriggerWalking()
	{
		return false;
	}

	/**
	 * Returns true if other Entities should be prevented from moving through this Entity.
	 */
	public boolean canBeCollidedWith()
	{
		return !this.isDead;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	public void onUpdate()
	{
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= 0.03999999910593033D;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9800000190734863D;
        this.motionY *= 0.9800000190734863D;
        this.motionZ *= 0.9800000190734863D;

        if (this.onGround)
        {
            this.motionX *= 0.699999988079071D;
            this.motionZ *= 0.699999988079071D;
            this.motionY *= -0.5D;
        }
        
        System.out.println(this.worldObj.isRemote+" "+this.fuse);

        if (this.fuse-- <= 0)
        {
            this.setDead();

            if (!this.worldObj.isRemote)
            {
                this.explode();
            }
        }
        else
        {
            this.worldObj.spawnParticle("smoke", this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D);
        }
	}

	//	private void explode()
	//	{
	//		float v = explosion_width;
	//
	//		int y = (int)(-this.fuse/rowSpeed);
	//		boolean destroy=true;
	//		int t = 0;
	//		
	//		System.out.println(this.fuse);
	//
	//		for(int x = (int)Math.floor(this.posX-v); x <= (int)Math.ceil(this.posX+v);x++){
	//			for(int z = (int)Math.floor(this.posZ-v); z <= (int)Math.ceil(this.posZ+v);z++){
	//				int dx = (int)Math.abs(x - this.posX);
	//				int dz = (int)Math.abs(z - this.posZ);
	//				if(dx+dz<=v){
	//					if(this.canFall(x, y-dx-dz, z))
	//						if(this.makeFall(x, y-dx-dz, z))
	//							t++;
	//					if(y-dx-dz <= this.worldObj.getChunkFromBlockCoords(x,z).getTopFilledSegment()+15)
	//						destroy=false;
	//				}
	//			}
	//		}
	//		
	////		if(t==0)
	////			this.fuse-=this.rowSpeed-1;
	//		System.out.println(t);
	////		if(t>400)
	////			this.fuse+=this.rowSpeed*5-1;
	//		this.fuse+=t/100*this.rowSpeed;
	//
	//		if(destroy)
	//			this.setDead();
	//
	//	}
	//
	//	private boolean makeFall(int x,int y, int z){
	//		int id = worldObj.getBlockId(x, y, z);
	//		if(id == Luxcraft.subNuke.blockID){
	//			Luxcraft.subNuke.onBlockDestroyedByPlayer(worldObj, x, y, z, 0);
	//			return false;
	//		}else{
	//
	//			byte var8 = 32;
	//			if (!worldObj.isRemote)
	//			{
	//				if(Block.blocksList[id] != null)
	//					if(Block.blocksList[id].getMobilityFlag()==1){
	//						Block.blocksList[id].dropBlockAsItem(worldObj, x, y, z, worldObj.getBlockMetadata(x, y, z), 0);
	//						worldObj.setBlockWithNotify(x, y, z, 0);
	//						return false;
	//					}
	//				
	//				if(canFallBelow(x,y-1,z)){
	//					if (worldObj.checkChunksExist(x - var8, y - var8, z - var8, x + var8, y + var8, z + var8))
	//					{
	//						if(id == Block.grass.blockID) worldObj.setBlock(x, y, z, Block.dirt.blockID);
	//						EntityFallingSand var9 = new EntityFallingSand(worldObj, (double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), worldObj.getBlockId(x, y, z), worldObj.getBlockMetadata(x, y, z));
	//						worldObj.spawnEntityInWorld(var9);
	//						return true;
	//					}
	//				}
	//			}
	//		}
	//		return false;
	//	}
	//
	//	private boolean canFall(int x, int y, int z){
	//		int id = this.worldObj.getBlockId(x, y, z);
	//
	//		if(id==0) return false;
	//		if(Block.blocksList[id]==null) return false;
	//		if(Block.blocksList[id].getExplosionResistance(this, this.worldObj, x, y , z, this.posX, this.posY, this.posZ)>100) return false;
	//		if(this.worldObj.getBlockTileEntity(x, y, z)!=null) return false;
	//		if(Block.blocksList[id].getMobilityFlag()==1) return true;
	//		if(Block.blocksList[id].getMobilityFlag()==2) return false;
	//		return true;
	//	}


	private void explode()
	{
		float v = Luxcraft.explosion_width;

		int y = (int)(-this.fuse/rowSpeed);
		boolean destroy=true;
		int t = 0;

		System.out.println("start");
		for(int x = (int)Math.floor(this.posX-v); x <= (int)Math.ceil(this.posX+v);x++){
			for(int z = (int)Math.floor(this.posZ-v); z <= (int)Math.ceil(this.posZ+v);z++){
				explodeStack(x,z);
			}
		}
		System.out.println("end");

		this.setDead();

	}

	private void explodeStack(int x,int z){
		int freey=0;
		boolean solid=true;

		int maxy = worldObj.getChunkFromBlockCoords(x, z).getTopFilledSegment()+16;
		while(maxy > 1 & !(this.getDistance(x, maxy, z) <= Luxcraft.explosion_width)) maxy--;
		while(maxy > 1 & canFallBelow(x,maxy,z)) maxy--;
		
		if(maxy<=1) return;

		int y = 1;


		for(;y <= maxy;y++){
			int id = worldObj.getBlockId(x, y, z);
			//if(canFallBelow(x,y,z)){
			if(id==0){
				if(solid) freey = y;
				solid=false;
			}else{
				if(!canFall(x,y,z)){
					solid=true;
					freey=y+1;
					if(y > 3 |id != Block.bedrock.blockID){
						solid=true;
						freey=-1;
						y+=10;
					}
				}else if(this.getDistance(x, y, z) < Luxcraft.explosion_width){
					if(Block.blocksList[id].getMobilityFlag()==0){
						if(!solid){
							int data  = worldObj.getBlockMetadata(x, y, z);
							if(id==Block.grass.blockID)
								id=Block.dirt.blockID;

							worldObj.setBlockAndMetadataWithNotify(x, freey, z, id, data);

							freey++;
							worldObj.setBlockWithNotify(x,y,z,0);
						}else{
							if(id==Block.grass.blockID)
								worldObj.setBlockAndMetadataWithNotify(x, y, z, id, Block.dirt.blockID);
						}
					}else if(Block.blocksList[id].getMobilityFlag()==1){
						Block.blocksList[id].dropBlockAsItem(worldObj, x, y, z, worldObj.getBlockMetadata(x, y, z), 0);
						worldObj.setBlockWithNotify(x,y,z,0);
						solid=false;
					}
				}else if (y>posY)
					return;

			}


		}
	}

	private boolean canFall(int x, int y, int z){
		int id = this.worldObj.getBlockId(x, y, z);

		if(id==0) return false;
		if(Block.blocksList[id]==null) return false;
		if(Block.blocksList[id].getMobilityFlag()==2) return false;
		if(Block.blocksList[id].getExplosionResistance(this, this.worldObj, x, y , z, this.posX, this.posY, this.posZ)>100) return false;
		if(this.worldObj.getBlockTileEntity(x, y, z)!=null) return false;

		return true;
	}


	/**
	 * Checks to see if the sand can fall into the block below it
	 */
	public boolean canFallBelow(int x, int y, int z)
	{
		int var4 = this.worldObj.getBlockId(x, y, z);

		if (var4 == 0)
		{
			return true;
		}
		else if (var4 == Block.fire.blockID)
		{
			return true;
		}
		else
		{
			Material var5 = Block.blocksList[var4].blockMaterial;
			return var5 == Material.water ? true : var5 == Material.lava;
		}
	}




	/**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        par1NBTTagCompound.setByte("Fuse", (byte)this.fuse);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        this.fuse = par1NBTTagCompound.getByte("Fuse");
    }

	@SideOnly(Side.CLIENT)
	public float getShadowSize()
	{
		return 0.0F;
	}
}
