package luxcraft;

public interface ILuxContainer {
	public int GetLuxLevel(byte color, boolean simulate);
	public int MaxLuxLevel(byte color);
	public void SetLux(int amount, byte color);
	public LuxPacket insertLux(LuxPacket packet, boolean simulate);
	public LuxPacket extractLux(LuxPacket packet, boolean simulate);
	public boolean canInsert();
	public boolean canExtract();
}
