package com.rwtema.luxcraft;

import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
            logger.info(String.format(info, params));
    }


    public static void info(String info, Object... params) {
        logger.info(String.format(info, params));
    }
}
