package com.rwtema.luxcraft.luxapi;

public enum LuxColor {
	White(0, "w", 1, 1, 1), //
	Red(1, "r", 1, 0, 0), //
	Green(2, "g", 0, 1, 0), //
	Blue(3, "b", 0, 0, 1), //
	Cyan(4, "c", 0, 1, 1), //
	Yellow(5, "y", 1, 1, 0), //
	Violet(6, "w", 1, 0, 1), //
	Black(7, "k", 0.1F, 0.1F, 0.1F);

	public static final int n = LuxColor.values().length;

	public final byte index;
	public final String shortname;

	public final float r, g, b;

	LuxColor(int c, String col, float r, float g, float b) {
		this.index = (byte) c;
		this.shortname = col;
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public static LuxColor col(byte col) {
		return LuxColor.values()[col];
	}
}
