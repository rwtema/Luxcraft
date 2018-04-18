package com.rwtema.luxcraft.debug;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public class CommandClientDebug extends CommandBase {
    public static double debug = 0;

    @Override
    public String getCommandName() {
        return "luxdebugclient";
    }

    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public String getCommandUsage(ICommandSender var1) {
        return "luxdebugclient";
    }

    @Override
    public void processCommand(ICommandSender var1, String[] var2) {
        debug = Double.parseDouble(var2[0]);
    }

}
