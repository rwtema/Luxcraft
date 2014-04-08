package com.rwtema.luxcraft;

import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;

public class LogHelper {
    public static Logger logger = LogManager.getLogger("luxcraft");

    public static boolean isDeObf = false;

    static {
        try {
            World.class.getMethod("getBlock", int.class, int.class, int.class);
            isDeObf = true;
        } catch (Throwable ex) {
            isDeObf = false;
        }
    }

    public static void debug(String info, Object... params) {
        if (isDeObf)
            logger.debug(String.format(info, params));

    }


    public static void info(String info, Object... params) {
        logger.info(String.format(info, params));
    }

    public static class myMarker implements Marker {
        public static myMarker instance = new myMarker();

        @Override
        public String getName() {
            return "LxC_Log";
        }

        @Override
        public Marker getParent() {

            return null;
        }

        @Override
        public boolean isInstanceOf(Marker m) {
            return m instanceof myMarker;
        }

        @Override
        public boolean isInstanceOf(String name) {
            return getName().equals(name);
        }
    }
}
