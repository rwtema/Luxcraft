package com.rwtema.luxcraft.block_base;

import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;

public class BoxModel extends ArrayList<Box> {
    public int invModelRotate = 90;
    public String label = "";

    public BoxModel() {
    }

    public BoxModel(Box newBox) {
        super();
        this.add(newBox);
    }

    public BoxModel(float par1, float par3, float par5, float par7, float par9, float par11) {
        super();
        this.add(new Box(par1, par3, par5, par7, par9, par11));
    }

    public static BoxModel newStandardBlock() {
        Box t = new Box(0, 0, 0, 1, 1, 1);
        t.renderAsNormalBlock = true;
        return new BoxModel(t);
    }

    public static BoxModel hollowBoxI(int minX, int minY, int minZ, int holeMinX, int holeMinZ, int holeMaxX, int holeMaxZ, int maxX, int maxY, int maxZ) {
        return BoxModel.hollowBox(minX / 16F, minY / 16F, minZ / 16F, holeMinX / 16F, holeMinZ / 16F, holeMaxX / 16F, holeMaxZ / 16F, maxX / 16F, maxY / 16F, maxZ / 16F);
    }

    public static BoxModel hollowBox(float minX, float minY, float minZ, float holeMinX, float holeMinZ, float holeMaxX, float holeMaxZ, float maxX, float maxY, float maxZ) {
        BoxModel t = new BoxModel();
        t.add(new Box(minX, minY, minZ, holeMinX, maxY, maxZ));
        t.add(new Box(holeMinX, minY, minZ, holeMaxX, maxY, holeMinZ));
        t.add(new Box(holeMinX, minY, holeMaxZ, holeMaxX, maxY, maxZ));
        t.add(new Box(holeMaxX, minY, minZ, maxX, maxY, maxZ));
        return t;
    }

    public static Box boundingBox(List models) {
        if (models == null) {
            return null;
        }

        if (models.size() == 0) {
            return null;
        }

        Box bounds = ((Box) models.get(0)).copy();

        for (int i = 1; i < models.size(); i++) {
            bounds.setBounds(Math.min(bounds.minX, ((Box) models.get(i)).minX), Math.min(bounds.minY, ((Box) models.get(i)).minY), Math.min(bounds.minZ, ((Box) models.get(i)).minZ),
                    Math.max(bounds.maxX, ((Box) models.get(i)).maxX), Math.max(bounds.maxY, ((Box) models.get(i)).maxY), Math.max(bounds.maxZ, ((Box) models.get(i)).maxZ));
        }

        return bounds;
    }

    public Box addBoxI(int par1, int par3, int par5, int par7, int par9, int par11) {
        return this.addBox("", par1 / 16F, par3 / 16F, par5 / 16F, par7 / 16F, par9 / 16F, par11 / 16F);
    }

    public Box addBox(float par1, float par3, float par5, float par7, float par9, float par11) {
        return this.addBox("", par1, par3, par5, par7, par9, par11);
    }

    public Box addBox(String l, float par1, float par3, float par5, float par7, float par9, float par11) {
        Box b = new Box(l, par1, par3, par5, par7, par9, par11);
        this.add(b);
        return b;
    }

    public BoxModel rotateToSide(ForgeDirection dir) {
        for (int i = 0; i < this.size(); i++) {
            this.get(i).rotateToSide(dir);
        }

        return this;
    }

    public BoxModel rotateY(int numRotations) {
        for (int i = 0; i < this.size(); i++) {
            this.get(i).rotateY(numRotations);
        }

        return this;
    }

    public BoxModel offset(float x, float y, float z) {
        for (int i = 0; i < this.size(); i++) {
            this.get(i).offset(x, y, z);
        }

        return this;
    }

    public BoxModel setColor(int col) {
        for (int i = 0; i < this.size(); i++) {
            this.get(i).setColor(col);
        }

        return this;
    }

    public BoxModel addYRotations() {
        this.addAll(this.copy().rotateY(1));
        this.addAll(this.copy().rotateY(2));
        return this;
    }

    public Box boundingBox() {
        return BoxModel.boundingBox(this);
    }

    public BoxModel copy() {
        BoxModel newModel = new BoxModel();

        for (int i = 0; i < this.size(); i++) {
            newModel.add(this.get(i).copy());
        }

        return newModel;
    }
}
