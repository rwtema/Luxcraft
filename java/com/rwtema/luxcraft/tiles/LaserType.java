package com.rwtema.luxcraft.tiles;

public enum LaserType {
    Standard(32, 1, 0, 0.05F, 0.0F, 0.9F, 0.8F), //
    Advanced(16, 64, 4, 0.1F, 0.0F, 0.9F, 0.8F);

    public final int range;
    public final float maxLux;
    public final int damage;
    public final float size;
    public final float noise;
    public final float alpha;
    public final float noise_momentum;

    LaserType(int range, int maxLux, int damage, float size, float noise, float alpha, float noise_momentum) {
        this.range = range;
        this.maxLux = maxLux;
        this.damage = damage;
        this.size = size;
        this.noise = noise;
        this.alpha = alpha;
        this.noise_momentum = noise_momentum;
    }
}