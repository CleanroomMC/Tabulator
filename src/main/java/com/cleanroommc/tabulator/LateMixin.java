package com.cleanroommc.tabulator;

import com.google.common.collect.ImmutableList;
import net.minecraftforge.fml.common.Loader;
import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.List;

public class LateMixin implements ILateMixinLoader {
    @Override
    public List<String> getMixinConfigs() {
        return ImmutableList.of("mixin.tabulator.rp.json");
    }

    @Override
    public boolean shouldMixinConfigQueue(String mixinConfig) {
        return !mixinConfig.split("\\.")[2].equals("rp") || Loader.isModLoaded("randomportals");
    }
}
