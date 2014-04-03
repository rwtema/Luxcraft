package com.rwtema.luxcraft.luxapi;

import net.minecraft.util.StatCollector;

public enum LuxColor {
	White(0, "w", 1, 1, 1), //
	Red(1, "r", 1, 0, 0), //
	Green(2, "g", 0, 1, 0), //
	Blue(3, "b", 0, 0, 1), //
	Cyan(4, "c", 0, 1, 1), //
	Yellow(5, "y", 1, 1, 0), //
	Violet(6, "v", 1, 0, 1), //
	Black(7, "k", 0, 0, 0); //

	public static final int n = LuxColor.values().length;

	public final byte index;
	public final String shortname;

	public final float r, g, b;

	public final int col;

	LuxColor(int c, String name, float r, float g, float b) {
		this(c, name, r, g, b, true);
	}

	LuxColor(int c, String name, float r, float g, float b, boolean valid) {
		this.index = (byte) c;
		this.shortname = name;
		this.r = r;
		this.g = g;
		this.b = b;

		this.col = (((int) (r * 255)) << 16) | (((int) (g * 255)) << 8) | ((int) (b * 255));
	}

	public static LuxColor col(int col) {
		if (col >= n)
			col = 0;
		return LuxColor.values()[col];
	}

	public String getLocalizedName() {
		return StatCollector.translateToLocal("lux.color." + index);
	}

	public String getAbbrLocalizedName() {
		return StatCollector.translateToLocal("lux.abbr." + index);
	}

	public int displayColor() {
		return col;
	}

}
