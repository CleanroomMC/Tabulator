package com.cleanroommc.tabulator;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class TabulatorCommand extends CommandBase {

    @Override
    public String getName() {
        return "creativeTabs";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/creativeTabs";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        StringBuilder tabs = new StringBuilder();
        for (CreativeTabs tab : CreativeTabs.CREATIVE_TAB_ARRAY) {
            tabs.append(tab.getTabLabel()).append(", ");
        }
        tabs.delete(tabs.length() - 2, tabs.length());
        sender.sendMessage(new TextComponentString("Creative tabs:"));
        sender.sendMessage(new TextComponentString(" - " + tabs));
    }
}
