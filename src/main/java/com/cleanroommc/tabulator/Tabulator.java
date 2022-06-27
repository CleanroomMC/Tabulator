package com.cleanroommc.tabulator;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Tabulator.ID, name = Tabulator.NAME, version = Tabulator.VERSION)
@Mod.EventBusSubscriber(modid = Tabulator.ID)
public class Tabulator {

    public static final String ID = "tabulator";
    public static final String NAME = "Tabulator";
    public static final String VERSION = "1.0.0";

    public static final Logger LOGGER = LogManager.getLogger(ID);

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
    }

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
    }
}
