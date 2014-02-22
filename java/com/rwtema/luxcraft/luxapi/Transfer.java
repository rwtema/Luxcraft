package com.rwtema.luxcraft.luxapi;

public enum Transfer {
	Perform(false), Simulate(true);

	public final boolean simulate;

	Transfer(boolean simulate) {
		this.simulate = simulate;
	}
}
