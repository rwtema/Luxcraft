package com.rwtema.luxcraft.luxapi;

public enum Transfer {
	Perform(true), Simulate(false);

	public final boolean perform;

	Transfer(boolean perform) {
		this.perform = perform;
	}
}
