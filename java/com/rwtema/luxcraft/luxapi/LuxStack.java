package com.rwtema.luxcraft.luxapi;

import net.minecraft.nbt.NBTTagCompound;

public class LuxStack {
    public static final float eps = 0.001F;
    public float[] lux = new float[8];

    public LuxStack() {
        this(0);
    }

    public LuxStack(float W, float R, float G, float B, float C, float Y, float V, float K) {
        lux[0] = W;
        lux[1] = R;
        lux[2] = G;
        lux[3] = B;
        lux[4] = C;
        lux[5] = Y;
        lux[6] = V;
        lux[7] = K;
    }

    public LuxStack(LuxColor color, float amount, Object... colors) {
        lux[color.index] = amount;
        LuxColor col = null;

        for (int i = 0; i < colors.length; i++) {
            if (colors[i] instanceof LuxColor)
                col = (LuxColor) colors[i];
            else if (col != null && colors[i] instanceof Float)
                lux[col.index] = (Float) colors[i];
        }
    }

    public LuxStack(float maxLux) {
        this(maxLux, maxLux, maxLux, maxLux, maxLux, maxLux, maxLux, maxLux);
    }

    public static LuxStack loadFromNBT(NBTTagCompound tags) {
        LuxStack stack = new LuxStack();
        stack.readFromNBT(tags);
        return stack;
    }

    public LuxStack add(LuxColor color, float amount) {
        lux[color.index] += amount;
        return this;
    }

    public LuxStack add(LuxStack other) {
        for (byte color = 0; color < lux.length; color++)
            lux[color] = lux[color] + other.lux[color];
        return this;
    }

    public LuxStack sub(LuxStack other) {
        for (byte color = 0; color < lux.length; color++) {
            lux[color] = lux[color] - other.lux[color];
            if (lux[color] < 0)
                lux[color] = 0;
        }
        return this;
    }

    public LuxStack extract(LuxStack other) {
        return this.extract(other.lux);
    }

    public LuxStack extract(float[] extract) {
        LuxStack temp = new LuxStack();
        for (byte color = 0; color < lux.length; color++) {
            temp.lux[color] = Math.min(lux[color], extract[color]);
            lux[color] -= temp.lux[color];
        }
        return temp;
    }

    public LuxStack mult(float mult) {
        LuxStack temp = new LuxStack();
        for (byte color = 0; color < lux.length; color++)
            temp.lux[color] = (float) Math.floor(lux[color] * mult);
        return temp;
    }

    public LuxStack limit(float threshold) {
        LuxStack temp = new LuxStack();
        for (byte color = 0; color < lux.length; color++)
            temp.lux[color] = Math.min(threshold, lux[color]);
        return temp;
    }

    public boolean isEmpty() {
        for (byte c = 0; c < lux.length; c++)
            if (lux[c] != 0)
                return false;
        return true;
    }

    public boolean isSignificant() {
        for (byte c = 0; c < lux.length; c++)
            if (lux[c] > eps)
                return true;
        return false;
    }

    public LuxStack correct() {
        for (byte c = 0; c < lux.length; c++)
            if (lux[c] < eps)
                lux[c] = 0;
        return this;
    }

    public String toString() {
        String str = "";
        for (byte c = 0; c < lux.length; c++)
            str = str + LuxColor.col(c).shortname + lux[c] + " ";
        return str;
    }

    public float totalLux() {
        float total = 0;
        for (byte c = 0; c < lux.length; c++)
            total = total + lux[c];
        return total;
    }

    public LuxStack copy() {
        return (new LuxStack()).add(this);
    }

    public LuxStack copyFrom(LuxStack other) {
        System.arraycopy(other.lux, 0, this.lux, 0, other.lux.length);
        return this;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound tags) {
        for (byte i = 0; i < lux.length; i++)
            if (lux[i] > 0)
                tags.setFloat("Lux_" + i, lux[i]);

        return tags;
    }

    /**
     * Read the stack fields from a NBT object.
     */
    public void readFromNBT(NBTTagCompound tags) {
        for (byte i = 0; i < lux.length; i++)
            lux[i] = tags.getFloat("Lux_" + i);
    }

    public float luxLevel(LuxColor col) {
        return lux[col.index];
    }

    public LuxStack div(int n) {
        for (byte color = 0; color < lux.length; color++)
            lux[color] /= n;
        return this;
    }

}
