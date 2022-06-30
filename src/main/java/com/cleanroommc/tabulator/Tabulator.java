package com.cleanroommc.tabulator;

import com.cleanroommc.tabulator.common.TabulatorCommand;
import com.cleanroommc.tabulator.crafttweaker.CTTabulatorAPI;
import com.cleanroommc.tabulator.crafttweaker.TabulatorCreativeTab;
import com.therandomlabs.randomportals.client.creativetab.CreativeTabPortals;
import crafttweaker.CraftTweakerAPI;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Tabulator.ID, name = Tabulator.NAME, version = Tabulator.VERSION)
@Mod.EventBusSubscriber(modid = Tabulator.ID)
public class Tabulator {

    public static final String ID = "tabulator";
    public static final String NAME = "Tabulator";
    public static final String VERSION = "1.0.2";

    public static final Logger LOGGER = LogManager.getLogger(ID);

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        if (Loader.isModLoaded("randomportals")) {
            String s = CreativeTabPortals.INSTANCE.getTabLabel();
        }
        CraftTweakerAPI.registerClass(CTTabulatorAPI.class);
        CraftTweakerAPI.registerClass(TabulatorCreativeTab.class);
    }

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
    }

    @Mod.EventHandler
    public void onServerLoad(FMLServerStartingEvent event) {
        event.registerServerCommand(new TabulatorCommand());
    }
}
