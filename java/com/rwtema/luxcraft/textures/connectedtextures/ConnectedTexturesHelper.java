package com.rwtema.luxcraft.textures.connectedtextures;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;

public class ConnectedTexturesHelper {
    public static int[] textureFromArrangement = new int[256];
    public static boolean[] isAdvancedArrangement = new boolean[16];
    public static int[] textureIds = new int[47];


     static {
        textureIds = new int[47];
        int j = 0;

        int[] sideA = new int[]{1, 4, 4, 1};
        int[] sideB = new int[]{2, 2, 8, 8};
        int[] corner = new int[]{16, 32, 64, 128};

        boolean[] validTexture = new boolean[625];
        int[] revTextureIds = new int[625];
        int[] k = new int[]{1, 5, 25, 125};

        for (int ar = 0; ar < 256; ar++) {
            int texId = 0;

            for (int i = 0; i < 4; i++) {
                boolean sA = (ar & sideA[i]) != 0;
                boolean sB = (ar & sideB[i]) != 0;
                boolean c = (ar & corner[i]) != 0;
                texId = texId + (getTex(sA, sB, c) * k[i]);
                if (!sA && !sB)
                    isAdvancedArrangement[ar & 15] = true;
            }

            if (!validTexture[texId]) {
                textureIds[j] = texId;
                revTextureIds[texId] = j;
                validTexture[texId] = true;
                j++;
            }

            textureFromArrangement[ar] = revTextureIds[texId];
        }
    }


    private static int getTex(boolean sideA, boolean sideB, boolean corner) {
        return sideA ? (sideB ? 0 : 1) : (sideB ? 2 : corner ? 3 : 4);
    }

    public static IIcon registerIcon(String texture, IIconRegister register) {
        if (!(register instanceof TextureMap))
            return null;

        TextureMap map = (TextureMap) register;

        IconConnected icon = new IconConnected(texture);
        for (int i = 0; i < 47; i++) {
            String s = TextureAtlasConnected.iconName(texture, i);
            TextureAtlasSprite t = map.getTextureExtry(s);
            if (t == null) {
                t = new TextureAtlasConnected(texture, i);
                map.setTextureEntry(s, t);
            }
            icon.setTexture(i, t);
        }

        return icon;
    }
}
