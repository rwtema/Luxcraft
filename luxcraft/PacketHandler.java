package luxcraft;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler
{
    @Override
    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
    {
        if (packet.channel.equals("TileEntityNBT"))
        {
            DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
            int id, x,y,z;
            NBTTagCompound tags;

            try
            {
                x = inputStream.readInt();
                y = inputStream.readInt();
                z = inputStream.readInt();
                tags = packet.readNBTTagCompound(inputStream);
            }
            catch (IOException e)
            {
                e.printStackTrace();
                return;
            }
            
            TileEntity t = ((EntityPlayer)player).worldObj.getBlockTileEntity(x, y, z);
            if(t != null){
            	if(t instanceof TileEntityLuxGenerator)
            		((TileEntityLuxGenerator)t).readFromNBT(tags);
            	else
            		t.readFromNBT(tags);
            }

//            Container t = ((EntityPlayer)player).openContainer;
//
//            if (t != null & t.windowId == id & t instanceof ContainerLuxGenerator)
//            {
//                ((ContainerLuxGenerator)t).updateData(luxLevel, color);
//            }
        }

    }

    private void handleRitualFurnace(Packet250CustomPayload packet)
    {
    }
}