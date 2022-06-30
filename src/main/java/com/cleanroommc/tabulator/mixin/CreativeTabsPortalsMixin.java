package com.cleanroommc.tabulator.mixin;

import com.cleanroommc.tabulator.common.TabulatorAPI;
import com.therandomlabs.randomportals.client.creativetab.CreativeTabPortals;
import com.therandomlabs.randomportals.config.RPOConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;

@Mixin(CreativeTabPortals.class)
public class CreativeTabsPortalsMixin {

    @Unique
    private static boolean init = true;

    /**
     * @author brachy84
     */
    @Overwrite
    private static void register() {
        if (init && !RPOConfig.Client.portalsCreativeTab) {
            TabulatorAPI.removeTab(CreativeTabPortals.INSTANCE);
            init = false;
        }
    }
}
