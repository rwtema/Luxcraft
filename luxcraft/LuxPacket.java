package luxcraft;

import net.minecraft.tileentity.TileEntity;

public class LuxPacket {
	public int[] luxLevel= {0,0,0,0,0,0,0};
	public LuxPacket(){}
	public LuxPacket(int W, int R, int G, int B, int C, int Y, int V){
		luxLevel[0]=W;
		luxLevel[1]=R;
		luxLevel[2]=G;
		luxLevel[3]=B;
		luxLevel[4]=C;
		luxLevel[5]=Y;
		luxLevel[6]=V;
	}

	public LuxPacket(int color, int amount){
		luxLevel[color] = amount;
	}
	
	public LuxPacket(int amount){
		for(byte c = 0;c<7;c++)
			luxLevel[c] = amount;
	}
	
	

	public LuxPacket(ILuxContainer container){
		for(byte color=0;color<7;color++)
			luxLevel[color] = container.GetLuxLevel(color, false);
	}

	public LuxPacket(TileEntity container){
		if(container != null){
			if(container instanceof ILuxContainer)
				for(byte color=0;color<7;color++)
					luxLevel[color] = ((ILuxContainer)container).GetLuxLevel(color, false);
		}
	}


	public LuxPacket add(int color, int amount){
		return this.add(new LuxPacket((byte)color,amount));
	}

	public LuxPacket add(LuxPacket lux){
		LuxPacket temp = new LuxPacket();
		if(lux!=null)
			for(byte color=0;color<luxLevel.length;color++)
				temp.luxLevel[color]=luxLevel[color]+lux.luxLevel[color];
		return temp;
	}
	
	
	public LuxPacket subt(LuxPacket lux){
		LuxPacket temp = new LuxPacket();
		if(lux!=null)
			for(byte color=0;color<luxLevel.length;color++)
				temp.luxLevel[color]=luxLevel[color]-lux.luxLevel[color];
		return temp;
	}
	
	public LuxPacket subt_to_zero(LuxPacket lux){
		LuxPacket temp = new LuxPacket();
		if(lux!=null)
			for(byte color=0;color<luxLevel.length;color++)
				temp.luxLevel[color]=Math.max(luxLevel[color]-lux.luxLevel[color],0);
		return temp;
	}
	
	

	public LuxPacket mult(double mult){
		LuxPacket temp = new LuxPacket();
		for(byte color=0;color<luxLevel.length;color++)
			temp.luxLevel[color]=(int)Math.floor(luxLevel[color]*mult);
		return temp;
	}
	
	public LuxPacket div(double div){
		LuxPacket temp = new LuxPacket();
		for(byte color=0;color<luxLevel.length;color++)
			temp.luxLevel[color]=(int) Math.ceil((double)luxLevel[color]/div);
		return temp;
	}

	public LuxPacket pmin(LuxPacket other){
		LuxPacket temp = new LuxPacket();
		for(byte color=0;color<luxLevel.length;color++)
			temp.luxLevel[color]=Math.min(luxLevel[color],other.luxLevel[color]);
		return temp;
	}
	
	public LuxPacket col(byte c){
		return new LuxPacket(c, this.luxLevel[c]);
	}


	public LuxPacket(int[] luxLevels){
		for(int i = 0; i < luxLevels.length & i < luxLevel.length;i++){
			luxLevel[i]=luxLevels[i];
		}
	}

	public boolean isEmpty(){
		for(byte c = 0;c<luxLevel.length;c+=1)
			if(luxLevel[c]!=0)
				return false;
		return true;
	}

	public String toString(){
		String str="";
		for(byte c = 0;c<luxLevel.length;c+=1)
			str = str + LuxHelper.color_abb[c] + luxLevel[c];
		return str;
	}

	public int totalLux(){
		int total=0;
		for(byte c = 0;c<luxLevel.length;c+=1)
			total = total + luxLevel[c];
		return total;
	}
	
	public int maxLux(){
		int max=-1;
		for(byte c = 0;c<luxLevel.length;c+=1)
			if(max == -1 | max < luxLevel[c])
				max =luxLevel[c];
		return max;
	}
	
	

	public LuxPacket copy(){
		return (new LuxPacket()).add(this);
	}


}
