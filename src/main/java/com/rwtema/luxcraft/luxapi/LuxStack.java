package com.rwtema.luxcraft.luxapi;

import net.minecraft.nbt.NBTTagCompound;

public class LuxStack {
    public static final double eps = 0.001F;
    public double[] lux = new double[8];

    public LuxStack() {
        this(0);
    }

    public LuxStack(double W, double R, double G, double B, double C, double Y, double V, double K) {
        lux[0] = W;
        lux[1] = R;
        lux[2] = G;
        lux[3] = B;
        lux[4] = C;
        lux[5] = Y;
        lux[6] = V;
        lux[7] = K;
    }

    public LuxStack(LuxColor color, double amount, Object... colors) {
        lux[color.index] = amount;
        LuxColor col = null;

        for (int i = 0; i < colors.length; i++) {
            if (colors[i] instanceof LuxColor)
                col = (LuxColor) colors[i];
            else if (col != null && colors[i] instanceof Double)
                lux[col.index] = (Double) colors[i];
        }
    }

    public LuxStack(double maxLux) {
        this(maxLux, maxLux, maxLux, maxLux, maxLux, maxLux, maxLux, maxLux);
    }

    public static LuxStack loadFromNBT(NBTTagCompound tags) {
        LuxStack stack = new LuxStack();
        stack.readFromNBT(tags);
        return stack;
    }

    public LuxStack add(LuxColor color, double amount) {
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

    public LuxStack extract(double[] extract) {
        LuxStack temp = new LuxStack();
        for (byte color = 0; color < lux.length; color++) {
            temp.lux[color] = Math.min(lux[color], extract[color]);
            lux[color] -= temp.lux[color];
        }
        return temp;
    }

    public LuxStack mult(double mult) {
        LuxStack temp = new LuxStack();
        for (byte color = 0; color < lux.length; color++)
            temp.lux[color] = Math.floor(lux[color] * mult);
        return temp;
    }

    public LuxStack limit(double threshold) {
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

    public double totalLux() {
        double total = 0;
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
                tags.setDouble("Lux_" + i, lux[i]);

        return tags;
    }

    /**
     * Read the stack fields from a NBT object.
     */
    public void readFromNBT(NBTTagCompound tags) {
        for (byte i = 0; i < lux.length; i++)
            lux[i] = tags.getDouble("Lux_" + i);
    }

    public double luxLevel(LuxColor col) {
        return lux[col.index];
    }

    public LuxStack div(int n) {
        for (byte color = 0; color < lux.length; color++)
            lux[color] /= n;
        return this;
    }

}
