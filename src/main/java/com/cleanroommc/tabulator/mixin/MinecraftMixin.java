package com.cleanroommc.tabulator.mixin;

import com.cleanroommc.tabulator.TabulatorAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.SearchTree;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

/**
 * Hides specified items in the search creative tab when something is typed in
 */
@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Inject(method = "populateSearchTreeManager", locals = LocalCapture.CAPTURE_FAILSOFT, at = @At(value = "INVOKE", target = "Lnet/minecraft/util/NonNullList;forEach(Ljava/util/function/Consumer;)V"))
    public void populateSearchTreeManager(CallbackInfo ci, SearchTree<ItemStack> searchtree, NonNullList<ItemStack> nonnulllist) {
        nonnulllist.removeIf(item -> TabulatorAPI.shouldRemoveItem(CreativeTabs.SEARCH, item));
    }
}
